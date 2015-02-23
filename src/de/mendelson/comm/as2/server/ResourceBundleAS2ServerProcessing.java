//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/server/ResourceBundleAS2ServerProcessing.java,v 1.1 2015/01/06 11:07:49 heller Exp $
package de.mendelson.comm.as2.server;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleAS2ServerProcessing extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        { "send.failed", "Send failed" },
        {"unable.to.process", "Unable to process on server: {0}" },
        { "server.shutdown", "The user {0} requests a server shutdown." },
        {"sync.mdn.sent", "{0}: Synchronous MDN sent as answer to message {1}."},
        {"invalid.request.from", "An invalid request has been detected. It has not been processed because it does not contain a as2-from header."},
        {"invalid.request.to", "An invalid request has been detected. It has not been processed because it does not contain a as2-to header."},
        {"invalid.request.messageid", "An invalid request has been detected. It has not been processed because it does not contain a message id header."},
    };
    
}