//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/partner/clientserver/PartnerSystemResponse.java,v 1.1 2015/01/06 11:07:43 heller Exp $
package de.mendelson.comm.as2.partner.clientserver;

import java.io.Serializable;

import de.mendelson.comm.as2.partner.PartnerSystem;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
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
public class PartnerSystemResponse extends ClientServerResponse implements Serializable {

    private PartnerSystem partnerSystem = null;

    public PartnerSystemResponse(PartnerSystemRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("Request partner system");
    }

    /**
     * @return the partnerSystem
     */
    public PartnerSystem getPartnerSystem() {
        return partnerSystem;
    }

    /**
     * @param partnerSystem the partnerSystem to set
     */
    public void setPartnerSystem(PartnerSystem partnerSystem) {
        this.partnerSystem = partnerSystem;
    }
   
}
