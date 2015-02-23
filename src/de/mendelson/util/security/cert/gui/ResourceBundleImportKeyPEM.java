//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM.java,v 1.1 2015/01/06 11:07:59 heller Exp $ 
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
public class ResourceBundleImportKeyPEM extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Cancel" },
    {"button.browse", "Browse" },        
            
    {"label.importkey", "Import key file (PEM):" },
    {"label.importcert", "Import certificate file:" },        
    {"label.alias", "New alias to use:" },
    {"label.keypass", "Key password for importing key:" },            
            
    {"title", "Import keys (PEM format)" },     
    {"filechooser.cert.import", "Please select the certificate file for the import" },
    {"filechooser.key.import", "Please select the key file for the import (PEM format)" }, 
            
    {"key.import.success.message", "The key has been imported successfully." },
    {"key.import.success.title", "Success" },
    {"key.import.error.message", "There occured an error during the import process.\n{0}" },
    {"key.import.error.title", "Error" },        
            
  };		
  
}