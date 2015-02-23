//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_de.java,v 1.1 2015/01/06 11:07:59 heller Exp $ 
package de.mendelson.util.security.cert.gui;
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
public class ResourceBundleImportKeyPEM_de extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Abbrechen" },
    {"button.browse", "Durchsuchen" },        
            
    {"label.importkey", "Schl�sseldatei (PEM):" },
    {"label.importcert", "Zertifikatdatei:" },        
    {"label.alias", "Zu benutzener Alias:" },
    {"label.keypass", "Schl�sselpasswort:" },            
            
    {"title", "Schl�ssel importieren (PEM Format)" },     
    {"filechooser.cert.import", "Bitte w�hlen Sie das zu importierende Zertifikat" },
    {"filechooser.key.import", "Bitte w�hlen Sie die zu importierende Schl�sseldatei (PEM Format)" }, 
            
    {"key.import.success.message", "Der Schl�ssel wurde erfolgreich importiert." },
    {"key.import.success.title", "Erfolg" },
    {"key.import.error.message", "Es gab einen Fehler beim Import des Schl�ssels.\n{0}" },
    {"key.import.error.title", "Fehler" },        
            
  };		
  
}