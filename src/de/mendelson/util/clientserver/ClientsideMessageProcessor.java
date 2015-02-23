//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/ClientsideMessageProcessor.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ClientServerResponse;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Interface for all client class that process a server message
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public interface ClientsideMessageProcessor {

    /**Returns if the message has been processed by the instance*/
    public boolean processMessageFromServer( ClientServerMessage message );

    
    public void processSyncResponseFromServer( ClientServerResponse response );
    
}
