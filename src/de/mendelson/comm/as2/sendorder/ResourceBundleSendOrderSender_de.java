//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderSender_de.java,v 1.1 2015/01/06 11:07:49 heller Exp $
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
public class ResourceBundleSendOrderSender_de extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"message.packed", "{0}: Ausgehende AS2 Nachricht aus \"{1}\" f�r den Empf�nger \"{2}\" erstellt in {4}, Rohdatengr�sse: {3}." },
    };
    
}