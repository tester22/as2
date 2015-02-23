//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/cem/gui/ResourceBundleDialogSendCEM.java,v 1.1 2015/01/06 11:07:35 heller Exp $
package de.mendelson.comm.as2.cem.gui;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleDialogSendCEM extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"title", "Exchange certificate with partners via CEM" },
        {"button.ok", "Ok" },
        {"button.cancel", "Cancel" },
        {"label.initiator", "Local station:" },
        {"label.receiver", "Receiver:" },
        {"label.certificate", "Certificate:"},
        {"label.activationdate", "Activation date:"},
        {"cem.request.failed", "The CEM request failed:\n{0}" },
        {"cem.request.success", "The CEM request has been sent successful." },
        {"cem.request.title", "Certificate exchange via CEM" },
        {"cem.informed", "The system tried to inform the following partners via CEM, please have a look at the CEM management to see if this was successful: {0}" },
        {"cem.not.informed", "The following partners have not been informed via CEM, please send the certificate using an other channel, e.g. email: {0}" },
    };
    
}