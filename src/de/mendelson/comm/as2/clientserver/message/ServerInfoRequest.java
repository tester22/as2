//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/clientserver/message/ServerInfoRequest.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.clientserver.message;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ServerInfoRequest extends ClientServerMessage implements Serializable {

    public static final String SERVER_PRODUCT_NAME = "serverprodname";
    public static final String SERVER_VERSION = "serverversion";
    public static final String SERVER_BUILD = "serverbuild";
    public static final String SERVER_START_TIME = "serverstarttime";

    public ServerInfoRequest() {
    }

    @Override
    public String toString() {
        return ("Request server info");
    }

}
