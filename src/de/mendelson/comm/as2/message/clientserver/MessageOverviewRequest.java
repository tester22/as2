//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/clientserver/MessageOverviewRequest.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.message.clientserver;

import de.mendelson.comm.as2.message.MessageOverviewFilter;
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
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class MessageOverviewRequest extends ClientServerMessage implements Serializable{

    private MessageOverviewFilter filter = null;
    private String messageId = null;

    public MessageOverviewRequest(String messageId){
        this.messageId = messageId;
    }
    
    public MessageOverviewRequest(MessageOverviewFilter filter){
        this.filter = filter;
    }

    @Override
    public String toString(){
        return( "Message overview request" );
    }

    /**
     * @return the filter
     */
    public MessageOverviewFilter getFilter() {
        return filter;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

   

    
}
