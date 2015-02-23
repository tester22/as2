//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/cem/ResourceBundleCEM_fr.java,v 1.1 2015/01/06 11:07:31 heller Exp $
package de.mendelson.comm.as2.cem;
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
public class ResourceBundleCEM_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {                
        {"cem.validated.schema", "{0}: Le CEM a �t� valid� avec succ�s." },
        {"cert.already.imported", "{0}: Le certificat CEM soumis existe d�j� dans le keystore (alias {1}), l''importation a �t� ignor�e."},
        {"cert.imported.success", "{0}: Le certificat CEM soumis a �t� correctement import� (alias {1})."},
        {"category." + CEMEntry.CATEGORY_CRYPT, "Cryptage" },
        {"category." + CEMEntry.CATEGORY_SIGN, "Signature" },
        {"category." + CEMEntry.CATEGORY_SSL, "SSL" },
        {"state." + CEMEntry.STATUS_ACCEPTED_INT, "Accept�e par {0}" },
        {"state." + CEMEntry.STATUS_PENDING_INT, "Pas de r�ponse si loin de {0}" },
        {"state." + CEMEntry.STATUS_REJECTED_INT, "Rejet�e par {0}" },
        {"state." + CEMEntry.STATUS_CANCELED_INT, "Annul�e" },
        {"state." + CEMEntry.STATUS_PROCESSING_ERROR_INT, "Erreur de traitement" },
        {"cemtype.response", "{0}: Le CEM message est du type \"certificate response\"" },
        {"cemtype.request", "{0}: Le CEM message est du type \"certificate request\"" },
        {"cem.response.relatedrequest.found", "{0}: La r�ponse de CEM se rapporte � la demande existante \"{1}\"" },
        {"cem.response.prepared", "{0}: Le message de r�ponse de CEM a �t� cr�� pour la demande {1}" },
    };
    
}