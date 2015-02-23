//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/cem/clientserver/CEMListResponse.java,v 1.1 2015/01/06 11:07:35 heller Exp $
package de.mendelson.comm.as2.cem.clientserver;

import de.mendelson.comm.as2.cem.CEMEntry;
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
public class CEMListResponse extends ClientServerResponse implements Serializable {

    private List<CEMEntry> list = null;

    public CEMListResponse(CEMListRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("List cem entries");
    }

    /**
     * @return the list
     */
    public List<CEMEntry> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<CEMEntry> list) {
        this.list = list;
    }
}
