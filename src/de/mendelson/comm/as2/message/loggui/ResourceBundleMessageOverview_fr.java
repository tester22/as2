//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/message/loggui/ResourceBundleMessageOverview_fr.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.message.loggui;
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
 * @author E.Pailleau
 * @version $Revision: 1.1 $
 */
public class ResourceBundleMessageOverview_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"header.timestamp", "Date et heure" },
        {"header.localstation", "Station locale" },
        {"header.partner", "Partenaire" },
        {"header.messageid", "Message id" },
        {"header.encryption", "Cryptage" },
        {"header.signature", "Signature" },
        {"header.mdn", "MDN" },      
        {"header.userdefinedid", "Id" },  
        {"header.payload", "Contenu" },
        {"number.of.attachments", "* {0} Pièces attachées *" },
    };
    
}
