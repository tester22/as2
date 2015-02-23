//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/ClientSessionHandlerCallback.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Client side protocol handler classback interface
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public interface ClientSessionHandlerCallback {

    public void loginRequestedFromServer();

    public void connected( SocketAddress address);
   
    public void loggedOut();

    public void disconnected();

    public void messageReceivedFromServer(ClientServerMessage message);

    public void error(String message);

    public void log(Level logLevel, String message);

    public Logger getLogger();

    public void syncRequestFailed( Throwable throwable );
}

