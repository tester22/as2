//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/ResourceBundleMDNParser_de.java,v 1.1 2015/01/06 11:07:40 heller Exp $
package de.mendelson.comm.as2.message;
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
public class ResourceBundleMDNParser_de extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"invalid.mdn.nocontenttype", "MDN ung�ltig: Kein content-type definiert." },
        {"structure.failure.mdn", "Eine eingehende MDN wurde erkannt. Leider ist die Struktur der MDN fehlerhaft, sodass sie nicht verarbeitet werden konnte. Die zugeh�rige Transaktion hat Ihren Status nicht ver�ndert: \"{0}\"" },
    };
    
}