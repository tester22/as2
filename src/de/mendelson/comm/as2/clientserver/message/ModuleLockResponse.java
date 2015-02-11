//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/clientserver/message/ModuleLockResponse.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.clientserver.message;

import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

import de.mendelson.comm.as2.modulelock.ClientInformation;
import de.mendelson.util.clientserver.messages.ClientServerResponse;

/**
 * Msg for the client server protocol
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ModuleLockResponse extends ClientServerResponse implements Serializable {

    private ClientInformation lockKeeper = null;
    private boolean success = false;
    
    public ModuleLockResponse(ModuleLockRequest request) {
        super(request);
    }

    
    @Override
    public String toString() {
        return ("Module lock response");
    }

    /**
     * @return the clientInformation
     */
    public ClientInformation getLockKeeper() {
        return lockKeeper;
    }

    /**
     * @param lockKeeper the clientInformation to set
     */
    public void setLockKeeper(ClientInformation lockKeeper) {
        this.lockKeeper = lockKeeper;
    }

    /**
     * @return the success
     */
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
