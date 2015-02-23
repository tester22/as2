//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/ResourceBundleKeystoreStorage_de.java,v 1.1 2015/01/06 11:07:58 heller Exp $
package de.mendelson.util.security.cert;
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
public class ResourceBundleKeystoreStorage_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {                
        {"error.save.notloaded", "Keystore kann nicht gespeichert werden, er wurde noch nicht geladen." },
        {"error.delete.notloaded", "Der Eintrag konnte nicht gel�scht werden, der unterliegende Keystore wurde noch nicht geladen." },
        {"error.readaccess", "Der Keystore konnte nicht gelesen werden: Kein Lesezugriff m�glich auf \"{0}\"." },
        {"error.filexists", "Der Keystore konnte nicht gelesen werden: Die Keystore Datei \"{0}\" existiert nicht." },
        {"error.notafile", "Der Keystore konnte nicht gelesen werden: Die Keystore Datei \"{0}\" ist keine Datei." },
        {"error.nodata", "Der Keystore konnte nicht gelesen werden: Keine Daten verf�gbar" },
        {"error.empty", "Der Keystore konnte nicht gelesen werden: Die Keystore Daten m�ssen l�nger als 0 sein." },
        {"error.save", "Die Daten des Keystores konnte nicht gespeichert werden." },
        {"keystore.read.failure", "Das System konnte die unterliegenden Zertifikate nicht lesen. Fehlermeldung: \"{0}\". Bitte pr�fen Sie, ob Sie das richtige Passwort f�r den Keystore verwenden."},
    };
    
}