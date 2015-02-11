//$Header: /cvsroot/mec-as2/b47/de/mendelson/util/clientserver/ClientServerSessionHandler.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.LoginRequest;
import de.mendelson.util.clientserver.messages.LoginRequired;
import de.mendelson.util.clientserver.messages.LoginState;
import de.mendelson.util.clientserver.messages.QuitRequest;
import de.mendelson.util.clientserver.messages.ServerInfo;
import de.mendelson.util.clientserver.messages.ServerLogMessage;
import de.mendelson.util.clientserver.user.PermissionDescription;
import de.mendelson.util.clientserver.user.User;
import de.mendelson.util.clientserver.user.UserAccess;
import de.mendelson.util.log.LogFormatter;
import de.mendelson.util.security.keygeneration.KeyGenerationResult;
import de.mendelson.util.security.keygeneration.KeyGenerationValues;
import de.mendelson.util.security.keygeneration.KeyGenerator;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Session handler for the server implemetation
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ClientServerSessionHandler extends IoHandlerAdapter {

    public static final String SESSION_ATTRIB_USER = "user";
    /**
     * User readable description of user permissions
     */
    private PermissionDescription permissionDescription = null;
    private Logger logger = Logger.getAnonymousLogger();
    /**
     * Synchronized structure to perform user defined processing on the server
     * depending on the incoming message object type
     */
    private final List<ClientServerProcessing> processingList = Collections.synchronizedList(new ArrayList<ClientServerProcessing>());
    /**
     * Stores the product name, this is displayed on login requests
     */
    private String productName = "";
    /**
     * Stores all sessions
     */
    private final List<IoSession> sessions = Collections.synchronizedList(new ArrayList<IoSession>());
    private PasswordValidationHandler loginHandler;
    private Logger serverSessionLogger = Logger.getAnonymousLogger();
    private AnonymousProcessing anonymousProcessing = null;

    public ClientServerSessionHandler(Logger logger, String[] validClientIds) {
        if (logger != null) {
            this.logger = logger;
        }
        this.loginHandler = new PasswordValidationHandler(validClientIds);
        this.serverSessionLogger.setUseParentHandlers(false);        
        try {
            Handler logHandler = this.generateLogHandler();
            this.serverSessionLogger.addHandler(logHandler);
        } catch (Exception e) {
            logger.warning("Unable to initialize the server session handler: " + e.getMessage());
        }
    }

    /**Overwrite this to create another log mechanism*/
    public Handler generateLogHandler() throws Exception {
        // Create a file handler that uses 3 logfiles, each with a limit of 1Mbyte
        String serverSessionLogPattern = "client_server_session%g.log";
        int limit = 1000000; // 1 Mb
        int numLogFiles = 3;
        FileHandler fileHandler = new FileHandler(serverSessionLogPattern, limit, numLogFiles, true);        
        fileHandler.setFormatter(new LogFormatter());
        return( fileHandler );
    }

    /**
     * Allows to process messages without login, e.g. server state
     */
    public void setAnonymousProcessing(AnonymousProcessing anonymousProcessing) {
        this.anonymousProcessing = anonymousProcessing;
    }

    /**
     * Logs something to the clients log - but only if the level is higher than
     * the defined loglevelThreshold
     */
    public void log(Level logLevel, String message) {
        this.logger.log(logLevel, message);
    }

    private void logSession(IoSession session, Level logLevel, String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("Session ");
        builder.append(session.getId()).append(" ");
        if (session.getRemoteAddress() != null) {
            builder.append("[").append(session.getRemoteAddress()).append("] ");
        }
        builder.append(message);
        this.serverSessionLogger.log(logLevel, builder.toString());
    }

    @Override
    /**
     * The session has been opened: send a server info object
     */
    public void sessionOpened(IoSession session) {
        this.logSession(session, Level.INFO, "Incoming connection");
        //send information about what this server is
        ServerInfo info = new ServerInfo();
        info.setProductname(this.productName);
        session.write(info);
        //request a login
        LoginRequired loginRequired = new LoginRequired();
        session.write(loginRequired);
    }

    public void setPermissionDescription(PermissionDescription permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    /**
     * Incoming message on the server site
     */
    @Override
    public void messageReceived(IoSession session, Object messageObj) {
        if (!(messageObj instanceof ClientServerMessage)) {
            return;
        }
        ClientServerMessage message = (ClientServerMessage) messageObj;
        if (message instanceof QuitRequest) {
            session.close(false);
            return;
        }
        if (this.anonymousProcessing != null && this.anonymousProcessing.processMessageWithoutLogin(session, message)) {
            this.performUserDefinedProcessing(session, message);
        } else {
            //it is a login request
            if (message instanceof LoginRequest) {
                LoginRequest loginRequest = (LoginRequest) message;
                UserAccess access = new UserAccess(this.logger);
                //validate passwd first, close session if it fails
                User definedUser = access.readUser(loginRequest.getUserName());
                if (definedUser != null && this.permissionDescription != null) {
                    definedUser.setPermissionDescription(this.permissionDescription);
                }
                User transmittedUser = new User();
                transmittedUser.setName(loginRequest.getUserName());
                transmittedUser.setPasswdCrypted(loginRequest.getCryptedPasswd());
                int validationState = this.loginHandler.validate(definedUser, transmittedUser,
                        loginRequest.getClientId());
                if (validationState == PasswordValidationHandler.STATE_FAILURE) {
                    this.logSession(session, Level.INFO, "Authentication failed for user " + loginRequest.getUserName());
                    LoginState state = new LoginState(loginRequest);
                    state.setUser(transmittedUser);
                    state.setState(LoginState.STATE_AUTHENTICATION_FAILURE);
                    session.write(state);
                    return;
                } else if (validationState == PasswordValidationHandler.STATE_INCOMPATIBLE_CLIENT) {
                    this.logSession(session, Level.INFO, "Authentication failed: incompatible client");
                    LoginState state = new LoginState(loginRequest);
                    state.setUser(transmittedUser);
                    state.setState(LoginState.STATE_INCOMPATIBLE_CLIENT);
                    session.write(state);
                    boolean closeImmediately = false;
                    session.close(closeImmediately);
                    return;
                } else if (validationState == PasswordValidationHandler.STATE_PASSWORD_REQUIRED) {
                    this.logSession(session, Level.INFO, "Authentication failed, no password send for user " + loginRequest.getUserName());
                    LoginState state = new LoginState(loginRequest);
                    state.setUser(transmittedUser);
                    state.setState(LoginState.STATE_AUTHENTICATION_FAILURE_PASSWORD_REQUIRED);
                    session.write(state);
                    return;
                }
                //user is logged in: add the user name to the session
                session.setAttribute(SESSION_ATTRIB_USER, loginRequest.getUserName());
                //add the session to the list of available sessions
                synchronized (this.sessions) {
                    this.sessions.add(session);
                }
                this.logSession(session, Level.INFO, "Authentication successful, user " + definedUser.getName() + " logged in");
                //success!
                LoginState loginSuccessState = new LoginState(loginRequest);
                loginSuccessState.setState(LoginState.STATE_AUTHENTICATION_SUCCESS);
                loginSuccessState.setUser(definedUser);
                session.write(loginSuccessState);
                return;
            }
            boolean loggedIn = session.containsAttribute(SESSION_ATTRIB_USER);
            //user not logged in so far
            if (!loggedIn) {
                LoginRequired loginRequired = new LoginRequired();
                User userObj = new User();
                if (this.permissionDescription != null) {
                    userObj.setPermissionDescription(this.permissionDescription);
                }
                loginRequired.setUser(userObj);
                session.write(loginRequired);
                session.close(false);
                return;
            }
            //here starts the user defined processing to extend the server functionality
            this.performUserDefinedProcessing(session, message);
        }
    }

    private KeyGenerationResult generateSessionKey(String sessionId) throws Exception {
        KeyGenerator generator = new KeyGenerator();
        KeyGenerationValues parameter = new KeyGenerationValues();
        //generating a longer key takes some time. In the current test (09/2012): 1024bit 140ms, 2048bit 1250ms
        parameter.setKeySize(1024);
        parameter.setKeyType(KeyGenerationValues.KEYTYPE_RSA);
        parameter.setKeyValidInDays(5);
        parameter.setSignatureAlgorithm(KeyGenerationValues.SIGNATUREALGORITHM_SHA1_WITH_RSA);
        parameter.setOrganisationName(sessionId);
        return (generator.generateKeyPair(parameter));
    }

    /**
     * User defined extensions for the server processing
     */
    private synchronized void performUserDefinedProcessing(IoSession session, ClientServerMessage message) {
        boolean processed = false;
        for (int i = 0; i < this.processingList.size(); i++) {
            processed |= this.processingList.get(i).process(session, message);
        }
        if (!processed) {
            this.log(Level.WARNING, "performUserDefinedProcessing: inbound message of class "
                    + message.getClass().getName() + " has not been processed.");
        }
    }

    /**
     * User defined actions for messages sent by any client. The user may extend
     * the framework by implementing a ServerProcessing interface
     */
    public void addServerProcessing(ClientServerProcessing serverProcessing) {
        synchronized (this.processingList) {
            this.processingList.add(serverProcessing);
        }
    }

    /**
     * Sends a message object to all connected clients
     */
    public void broadcast(Object data) {
        synchronized (this.sessions) {
            for (IoSession session : this.sessions) {
                if (session.isConnected()) {
                    session.write(data);
                }
            }
        }
    }

    /**
     * Sends a log message to all connected clients
     */
    public void broadcastLogMessage(Level level, String message, Object[] parameter) {
        ServerLogMessage serverMessage = new ServerLogMessage();
        serverMessage.setLevel(level);
        serverMessage.setMessage(message);
        serverMessage.setParameter(parameter);
        this.broadcast(serverMessage);
    }

    /**
     * Sends a log message to all connected clients
     */
    public void broadcastLogMessage(Level level, String message) {
        this.broadcastLogMessage(level, message, null);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        String user = (String) session.getAttribute(SESSION_ATTRIB_USER);
        if (user != null) {
            this.logSession(session, Level.INFO, "Closed");
            synchronized (this.sessions) {
                this.sessions.remove(session);
            }
            //this.log(Level.INFO, "Session closed for user " + user);
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        // disconnect an idle client
        session.close(false);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        this.logSession(session, Level.WARNING, "Exception caught: " + cause.getMessage());
        cause.printStackTrace();
        // Close connection when unexpected exception is caught.
        session.close(true);
    }

    public int getConnectedClients() {
        return (this.sessions.size());
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
