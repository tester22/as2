//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/messages/LoginRequest.java,v 1.1 2015/01/06 11:07:55 heller Exp $
package de.mendelson.util.clientserver.messages;

import de.mendelson.util.clientserver.user.User;
import java.io.Serializable;
import java.security.cert.X509Certificate;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol. This is the initial message that should be send to the server
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class LoginRequest extends ClientServerMessage implements Serializable {

    private String username = null;
    private String cryptedPasswd = null;
    /**The servers require a special client version/id because client and server
     * must be compatible. This is set here*/
    private String clientId = null;

    public String getUserName() {
        return username;
    }

    public void setUserName(String user) {
        this.username = user;
    }

    public String getCryptedPasswd() {
        return this.cryptedPasswd;
    }

    public void setPasswd(char[] passwd) {
        this.cryptedPasswd = User.cryptPassword(passwd);
    }

    @Override
    public String toString() {
        return ("Login request for user " + this.username);
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
   
}
