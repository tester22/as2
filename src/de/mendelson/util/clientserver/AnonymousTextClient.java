//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/AnonymousTextClient.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ClientServerResponse;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Text Client implementation that sends anonymous messages (no login required)
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class AnonymousTextClient extends BaseTextClient {

    public AnonymousTextClient() throws Exception {
        super();
        super.addMessageProcessor(new ClientsideMessageProcessor() {

            @Override
            public boolean processMessageFromServer(ClientServerMessage message) {
                return (true);
            }

            @Override
            public void processSyncResponseFromServer(ClientServerResponse response) {                
            }
        });
    }

    /**
     * no login required, anonymous request
     */
    @Override
    public void performLogin() {
    }
   
    
    @Override
    public void disconnected() {
        super.disconnect();
    }
}
