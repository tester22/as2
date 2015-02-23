//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/datasheet/gui/ResourceBundleCreateDataSheet.java,v 1.1 2015/01/06 11:07:39 heller Exp $ 
package de.mendelson.comm.as2.datasheet.gui;

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
public class ResourceBundleCreateDataSheet extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"title", "Datasheet for new communication link" },
        {"button.ok", ">> Create datasheet"},
        {"button.cancel", "Cancel"},   
        {"progress", "Create PDF" },
        {"label.newpartner", "New partner - not in the system so far" },
        {"label.comment", "Comment: "},
        {"label.receipturl", "Your AS2 receipt URL: " },
        {"label.localpartner", "Local partner: "},
        {"label.remotepartner", "Remote partner: "},
        { "label.encryption", "Encryption: "},
        { "label.signature", "Digital signature: "},
        { "label.compression", "Data compression"},
        { "label.syncmdn", "Request sync MDN"},
        { "label.signedmdn", "Request signed MDN"},
        {"label.info", "<HTML><strong>This wizard allows to create a comminication datasheet PDF which could be send to a new partner to establish a communication link.</strong></HTML>" },
        {"file.written", "The communication datasheet PDF has been written to \"{0}\". Please send it to your new partner to establish a new communication link." },
        {"label.usedataencryption", "Use data encryption" },
        {"label.usedatasignature", "Use data signature"},
        {"label.usessl", "Use SSL"},
        {"label.usesessionauth", "Use session auth"},
        {"label.requestsignedeerp", "Request signed EERP"},
    };
}
