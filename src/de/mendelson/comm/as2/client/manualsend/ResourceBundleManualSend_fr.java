//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/manualsend/ResourceBundleManualSend_fr.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.client.manualsend;

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
 * @author E.Pailleau
 * @version $Revision: 1.1 $
 */
public class ResourceBundleManualSend_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"button.browse", "Parcourir..."},
        {"label.filename", "Nom de fichier:"},
        {"label.partner", "Destinataire:"},
        {"label.selectfile", "Merci de s�lectionner le fichier � envoyer"},
        {"title", "Envoyer un fichier � un partenaire"},
        {"send.success", "Le fichier a �t� mis en queue d''envoi avec succ�s."},
        {"send.failed", "Le fichier n''a pas �t� plac� dans le processus d''envoi en raison d''une erreur."},
    };
}
