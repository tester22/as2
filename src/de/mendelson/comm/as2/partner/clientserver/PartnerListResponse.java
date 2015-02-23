//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/partner/clientserver/PartnerListResponse.java,v 1.1 2015/01/06 11:07:43 heller Exp $
package de.mendelson.comm.as2.partner.clientserver;

import de.mendelson.comm.as2.partner.Partner;
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
public class PartnerListResponse extends ClientServerResponse implements Serializable {

    private List<Partner> list = null;

    public PartnerListResponse(PartnerListRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("List partner");
    }

    /**
     * @return the list
     */
    public List<Partner> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Partner> list) {
        this.list = list;
    }
}
