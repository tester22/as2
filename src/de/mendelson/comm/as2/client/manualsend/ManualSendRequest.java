//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/manualsend/ManualSendRequest.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.client.manualsend;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.util.clientserver.clients.datatransfer.UploadRequestFile;
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
public class ManualSendRequest extends UploadRequestFile implements Serializable {

    private Partner sender;
    private Partner receiver;
    private String filename;
    private String resendMessageId = null;
    private String userdefinedId = null;

    @Override
    public String toString() {
        return ("Manual send request");
    }

    /**
     * @return the sender
     */
    public Partner getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(Partner sender) {
        this.sender = sender;
    }

    /**
     * @return the receiver
     */
    public Partner getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(Partner receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the resendMessageId
     */
    public String getResendMessageId() {
        return resendMessageId;
    }

    /**
     * Set this message id if this is a resend of an existing message
     * @param resendMessageId the resendMessageId to set
     */
    public void setResendMessageId(String resendMessageId) {
        this.resendMessageId = resendMessageId;
    }

    /**
     * @return the userdefinedId
     */
    public String getUserdefinedId() {
        return userdefinedId;
    }

    /** Sets a user defined id to this transaction. If this is set the userdefined id could be
     *  used later to track the progress of this send transmission.
     * @param userdefinedId the userdefinedId to set
     */
    public void setUserdefinedId(String userdefinedId) {
        this.userdefinedId = userdefinedId;
    }

}
