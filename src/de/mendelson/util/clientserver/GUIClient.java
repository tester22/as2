//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/GUIClient.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.connectionprogress.JDialogConnectionProgress;
import de.mendelson.util.clientserver.gui.JDialogLogin;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.clientserver.messages.LoginRequired;
import de.mendelson.util.clientserver.messages.LoginState;
import de.mendelson.util.clientserver.messages.ServerInfo;
import de.mendelson.util.clientserver.user.User;
import java.awt.Color;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * GUI Client root implementation
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public abstract class GUIClient extends JFrame implements ClientSessionHandlerCallback {

    private BaseClient client = null;
    private MecResourceBundle rb = null;
    private final List<ClientsideMessageProcessor> messageProcessorList = Collections.synchronizedList(new ArrayList<ClientsideMessageProcessor>());
    private String serverProductName = null;

    public GUIClient() {
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleGUIClient.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        this.client = new BaseClient(this);
        this.client.setLogger(this.getLogger());
    }

    public void addMessageProcessor(ClientsideMessageProcessor processor) {
        synchronized (this.messageProcessorList) {
            this.messageProcessorList.add(processor);
        }
    }

    public void removeMessageProcessor(ClientsideMessageProcessor processor) {
        synchronized (this.messageProcessorList) {
            this.messageProcessorList.remove(processor);
        }
    }

    /**
     * Logs something to the clients log
     */
    @Override
    public void log(Level logLevel, String message) {
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.log: No logger set.");
        }
        this.getLogger().log(logLevel, message);
    }

    public void connect(final InetSocketAddress address, final long timeout) {
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.connect: No logger set.");
        }
        ProgressRun progress = new ProgressRun(address);
        new Thread(progress).start();
        boolean connected = false;
        try {
            connected = client.connect(address, timeout);
        } finally {
            progress.stopRunning();
        }
        if (!connected) {
            this.log(Level.WARNING, this.rb.getResourceString("connectionrefused.message", address));
            JOptionPane.showMessageDialog(GUIClient.this,
                    GUIClient.this.rb.getResourceString("connectionrefused.message", address),
                    GUIClient.this.rb.getResourceString("connectionrefused.title"), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public abstract Logger getLogger();

    public void performLogin(String user, char[] passwd, String clientId) {
        LoginState state = this.getBaseClient().login(user, passwd, clientId);
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.loggedIn: No logger set.");
        }
        while (state.getState() != LoginState.STATE_AUTHENTICATION_SUCCESS) {
            state = this.performLogin(user);
        }
        User returnedLoginUser = state.getUser();
        //login successful: pass a user to the base client
        this.getBaseClient().setUser(returnedLoginUser);
        this.log(Level.CONFIG, this.rb.getResourceString("login.success", returnedLoginUser.getName()));
    }

    /**
     * Overwrite this to change the login dialogs color
     */
    public Color getLoginDialogColorBackground() {
        return (Color.decode("#3F7017"));
    }

    /**
     * Overwrite this to change the login dialogs color
     */
    public Color getLoginDialogColorForeground() {
        return (Color.WHITE);
    }

    private LoginState performLogin(String user) {
        JDialogLogin dialog = new JDialogLogin(null, this.serverProductName);
        dialog.setColor(this.getLoginDialogColorBackground(), this.getLoginDialogColorForeground());
        dialog.setDefaultUser(user);
        dialog.setVisible(true);
        if (!dialog.isCanceled()) {
            char[] passwd = dialog.getPass();
            String loginUser = dialog.getUser();
            LoginState state = this.getBaseClient().login(loginUser, passwd, this.serverProductName);
            if (state.getState() == LoginState.STATE_AUTHENTICATION_FAILURE_PASSWORD_REQUIRED) {
                if (this.getLogger() == null) {
                    throw new RuntimeException("GUIClient.loginFailureServerRequestsPassword: No logger set.");
                }
                this.log(Level.INFO, this.rb.getResourceString("password.required", user));
                return (this.performLogin(loginUser));
            } else if (state.getState() == LoginState.STATE_AUTHENTICATION_SUCCESS) {
                //everything is fine: just return the state for further login processing
                return (state);
            } else if (state.getState() == LoginState.STATE_AUTHENTICATION_FAILURE) {
                if (this.getLogger() == null) {
                    throw new RuntimeException("GUIClient.loginFailure: No logger set.");
                }
                User returnedLoginUser = state.getUser();
                this.log(Level.WARNING, this.rb.getResourceString("login.failure", returnedLoginUser.getName()));
                return (this.performLogin(returnedLoginUser.getName()));
            } else if (state.getState() == LoginState.STATE_INCOMPATIBLE_CLIENT) {
                JOptionPane.showMessageDialog(GUIClient.this,
                        this.rb.getResourceString("login.failed.client.incompatible.message"),
                        this.rb.getResourceString("login.failed.client.incompatible.title"), JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } else {
            //kill VM
            System.exit(1);
        }
        //will NEVER happen
        return (null);
    }

    /**
     * Sends a message async to the server
     */
    public void sendAsync(ClientServerMessage message) {
        this.getBaseClient().sendAsync(message);
    }

    /**
     * Sends a message sync to the server and returns a response Will inform the
     * ClientSessionHandler callback (syncRequestFailed) if the sync request
     * fails
     */
    public ClientServerMessage sendSync(ClientServerMessage request, long timeout) {
        return (this.getBaseClient().sendSync(request, timeout));
    }

    /**
     * Sends a message sync to the server and returns a response Will inform the
     * ClientSessionHandler callback (syncRequestFailed) if the sync request
     * fails
     */
    public ClientServerMessage sendSync(ClientServerMessage request) {
        return (this.getBaseClient().sendSync(request));
    }

    @Override
    public void connected(SocketAddress socketAddress) {
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.connected: No logger set.");
        }
        this.log(Level.INFO, this.rb.getResourceString("connection.success",
                socketAddress.toString()));
    }

    @Override
    public void loggedOut() {
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.loggedOut: No logger set.");
        }
        this.log(Level.INFO, this.rb.getResourceString("logout.from.server"));
    }

    @Override
    public void disconnected() {
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.diconnected: No logger set.");
        }
        this.log(Level.WARNING, this.rb.getResourceString("connection.closed"));
        JOptionPane.showMessageDialog(null, this.rb.getResourceString("connection.closed.message"),
                this.rb.getResourceString("connection.closed.title"), JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    /**
     * Overwrite this in the client implementation for user defined processing
     */
    @Override
    public void messageReceivedFromServer(ClientServerMessage message) {
        //there is no user defined processing for sync responses
        if (message._isSyncRequest() && message instanceof ClientServerResponse) {
            synchronized (this.messageProcessorList) {
                //let the message process by all registered client side processors            
                for (ClientsideMessageProcessor processor : this.messageProcessorList) {
                    processor.processSyncResponseFromServer((ClientServerResponse)message);
                }
            }
            return;
        }
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.messageReceivedFromServer: No logger set.");
        }
        //catch the server information if its available
        if (message instanceof ServerInfo) {
            this.serverProductName = ((ServerInfo) message).getProductname();
        } else if (message instanceof LoginRequired) {
            this.loginRequestedFromServer();
        } else {
            boolean processed = false;
            synchronized (this.messageProcessorList) {
                //let the message process by all registered client side processors            
                for (ClientsideMessageProcessor processor : this.messageProcessorList) {
                    processed |= processor.processMessageFromServer(message);
                }
            }
            if (!processed) {
                this.log(Level.WARNING, this.rb.getResourceString("client.received.unprocessed.message",
                        message.getClass().getName()));
            }
        }
    }

    @Override
    public void error(String message) {
        if (this.getLogger() == null) {
            throw new RuntimeException("GUIClient.error: No logger set.");
        }
        this.log(Level.SEVERE, this.rb.getResourceString("error", message));
    }

    /**
     * Performs a logout, closes the actual session
     */
    public void logout() {
        this.getBaseClient().logout();
    }

    /**
     * @return the client
     */
    public BaseClient getBaseClient() {
        return client;
    }

    /**
     * Makes this a ClientSessionCallback
     */
    @Override
    public void syncRequestFailed(Throwable throwable) {
        this.getLogger().warning(throwable.getMessage());
    }

    private class ProgressRun implements Runnable {

        private boolean keepRunning = true;
        private InetSocketAddress address;

        public ProgressRun(InetSocketAddress address) {
            this.address = address;
        }

        @Override
        public void run() {
            JDialogConnectionProgress dialog = new JDialogConnectionProgress(GUIClient.this);
            dialog.setHost(address.toString());
            dialog.setVisible(true);
            while (this.keepRunning) {
                try {
                    TimeUnit.MILLISECONDS.sleep(150);
                } catch (InterruptedException e) {
                }
            }
            dialog.setVisible(false);
            dialog.dispose();
        }

        public void stopRunning() {
            this.keepRunning = false;
        }
    }
}
