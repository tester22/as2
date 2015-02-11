//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/client/manualsend/ManualSendResponse.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.client.manualsend;

import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.util.clientserver.clients.datatransfer.UploadResponseFile;

/**
 * Msg for the client server protocol
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ManualSendResponse extends UploadResponseFile implements Serializable {

    private AS2MessageInfo as2Info = null;
    
    public ManualSendResponse(ManualSendRequest request) {
        super(request);
    }

    
    
    @Override
    public String toString() {
        return ("Manual send response");
    }

    /**
     * @return the as2Info
     */
    public AS2MessageInfo getAS2Info() {
        return as2Info;
    }

    /**
     * @param as2Info the as2Info to set
     */
    public void setAS2Info(AS2MessageInfo as2Info) {
        this.as2Info = as2Info;
    }

    
}
