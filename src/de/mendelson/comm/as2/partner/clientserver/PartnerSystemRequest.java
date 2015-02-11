//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/partner/clientserver/PartnerSystemRequest.java,v 1.1 2015/01/06 11:07:43 heller Exp $
package de.mendelson.comm.as2.partner.clientserver;

import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.util.clientserver.messages.ClientServerMessage;

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class PartnerSystemRequest extends ClientServerMessage implements Serializable {

    private Partner partner;

    public PartnerSystemRequest(Partner partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return ("Request partner system");
    }

    /**
     * @return the partner
     */
    public Partner getPartner() {
        return partner;
    }
}
