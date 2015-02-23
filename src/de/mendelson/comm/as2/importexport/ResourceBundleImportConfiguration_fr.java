//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/importexport/ResourceBundleImportConfiguration_fr.java,v 1.1 2015/01/06 11:07:40 heller Exp $
package de.mendelson.comm.as2.importexport;

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
public class ResourceBundleImportConfiguration_fr extends MecResourceBundle {

    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"button.import", "Importation!"},
        {"button.cancel", "Annuler"},
        {"title", "Importation de configuration"},
        {"import.info", "Veuillez choisir la configuration et partner vous voudrait importer."},
        {"label.propertiesimport", "Propri�t�s de serveur d''importation (procuration, pathes de keystore, langue)"},
        {"label.notificationimport", "Donn�es d'avis d'importation (compte de courrier, options g�n�rales d'avis)"},
        {"invalid.importfile", "Ce n'est pas un dossier valide d'importation."},
        {"header.name", "Nom"},
        {"header.as2id", "AS2 id"},
        {"import.success.msg", "Les donn�es de configuration ont �t� r�ussies import�."},
        {"import.success.title", "Succ�s"},
        {"import.failed.msg", "Une erreur s'est produite pendant l'importation: \"{0}\""},
        {"import.failed.title", "Importation d�faillante"},
        {"title.partner", "Associ�"},
        {"title.config", "Configuration"},
        {"partner.all", "Tout l''associ�"},
        {"partner.none", "Aucun associ�"},
    };
}
