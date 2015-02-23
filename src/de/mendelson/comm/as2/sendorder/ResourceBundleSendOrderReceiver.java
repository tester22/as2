//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderReceiver.java,v 1.1 2015/01/06 11:07:49 heller Exp $
package de.mendelson.comm.as2.sendorder;
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
public class ResourceBundleSendOrderReceiver extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"async.mdn.wait", "{0}: Will wait for async MDN until {1}." },  
        {"max.retry.reached", "{0}: The max retry has been reached, transmission canceled." },
        {"retry", "{0}: Will retry to send transmission after {1}s, retry {2}/{3}." },
    };
    
}