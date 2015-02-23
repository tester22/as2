//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/store/ResourceBundleMessageStoreHandler_fr.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.message.store;
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
public class ResourceBundleMessageStoreHandler_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"message.error.stored", "{0}: Contenu du message stock� vers \"{1}\"." },
        {"message.error.raw.stored", "{0}: Message sortant brut stock� vers \"{1}\"." },
        {"dir.createerror", "Cr�ation impossible du r�pertoire \"{0}\"." },
        {"comm.success", "{0}: Succ�s de la communication AS2, le contenu {1} a �t� d�plac� vers \"{2}\"." },
        {"outboundstatus.written", "{0}: Fichier d''�tat sortant �crit \"{1}\"."},
    };
    
}
