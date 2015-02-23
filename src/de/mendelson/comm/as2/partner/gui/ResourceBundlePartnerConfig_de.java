//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerConfig_de.java,v 1.1 2015/01/06 11:07:44 heller Exp $
package de.mendelson.comm.as2.partner.gui;
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
public class ResourceBundlePartnerConfig_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"title", "Partnerkonfiguration" },
        {"button.ok", "Ok" },
        {"button.cancel", "Abbrechen" },
        {"button.new", "Neu" },
        {"button.clone", "Kopie" },
        {"button.delete", "L�schen" },
        {"nolocalstation.message", "Mindestens ein Partner muss als lokale Station definiert sein." },
        {"nolocalstation.title", "Keine lokale Station" },
        {"localstation.noprivatekey.message", "Die lokale Station muss einen private Schl�ssel zugewiesen bekommen." },
        {"localstation.noprivatekey.title", "Kein privater Schl�ssel" },
        {"dialog.partner.delete.message", "Sie sind dabei, den Partner \"{0}\" aus der Partnerkonfiguration zu l�schen.\nAlle Partnerdaten von \"{0}\" gehen verloren.\n\nWollen Sie den Partner \"{0}\" wirklich l�schen?" },
        {"dialog.partner.delete.title", "L�schen eines Partners" },
        {"dialog.partner.deletedir.message", "Der Partner \"{0}\" wurde aus der Konfiguration gel�scht. Soll das dazugeh�rige Verzeichnis\n\"{1}\"\nauf der Festplatte gel�scht werden?" },
        {"dialog.partner.deletedir.title", "L�schen eines Nachrichtenverzeichnisses" },
        {"dialog.partner.renamedir.message", "Der Partner \"{0}\" wurde umbenannt nach \"{1}\". Soll das dazugeh�rige Verzeichnis\n\"{2}\"\nauf der Festplatte umbenannt werden?" },
        {"dialog.partner.renamedir.title", "Nachrichtenverzeichnis umbenennen" },
        {"directory.rename.failure", "Das Verzeichnis \"{0}\" kann nicht nach \"{1}\" umbenannt werden." },
        {"directory.rename.success", "Das Verzeichnis \"{0}\" wurde umbenannt nach \"{1}\"." },
        {"directory.delete.failure", "Das Verzeichnis \"{0}\" konnte nicht gel�scht werden." },
        {"directory.delete.success", "Das Verzeichnis \"{0}\" wurde gel�scht." },
        {"saving", "Speichern..." },
        {"module.locked", "Die Partnerverwaltung ist exklusiv von einem anderen Client ge�ffnet, Sie k�nnen Ihre �nderungen nicht sichern!" },
    };
    
}