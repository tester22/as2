//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/message/clientserver/MessageOverviewResponse.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.message.clientserver;

import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
import java.util.List;
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
public class MessageOverviewResponse extends ClientServerResponse implements Serializable {

    private List<AS2MessageInfo> list = null;

    public MessageOverviewResponse(MessageOverviewRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("Message overview response");
    }

    /**
     * @return the list
     */
    public List<AS2MessageInfo> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<AS2MessageInfo> list) {
        this.list = list;
    }

    
}
