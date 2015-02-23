//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleExportKeyPKCS12_de.java,v 1.1 2015/01/06 11:07:59 heller Exp $ 
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
public class ResourceBundleExportKeyPKCS12_de extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Abbrechen" },
    {"button.browse", "Durchsuchen" },        
            
    {"keystore.contains.nokeys", "Dieser Keystore beihaltet keine privaten Schl�ssel." },
    
    {"label.exportkey", "Export Keystore (PKCS#12):" },
    {"label.keypass", "Passwort:" },            
            
    {"title", "Schl�ssel in Keystore exportieren (PKCS#12 format)" },     
    {"filechooser.key.export", "Bitte w�hlen Sie den PKCS#12 Keystore f�r den Export" }, 
                
    {"key.export.success.message", "Der Schl�ssel wurde erfolgreich exportiert." },
    {"key.export.success.title", "Erfolg" },
    {"key.export.error.message", "Es gab einen Fehler beim Export.\n{0}" },
    {"key.export.error.title", "Fehler" },    
    
    {"label.alias", "Zu exportierender Schl�ssel:" },
    {"key.exported.to.file", "Der Schl�ssel \"{0}\" wurde in den Keystore \"{1}\" exportiert." },
  };		
  
}