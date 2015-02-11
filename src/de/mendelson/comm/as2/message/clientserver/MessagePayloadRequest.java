//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/message/clientserver/MessagePayloadRequest.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.message.clientserver;

import java.io.Serializable;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Msg for the client server protocol
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class MessagePayloadRequest extends ClientServerMessage implements Serializable{

    private String messageId = null;

    public MessagePayloadRequest(String messageId){
        this.messageId = messageId;
    }

    @Override
    public String toString(){
        return( "Message payload request" );
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }
  
}
