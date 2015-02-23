//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleExportKeyPKCS12_fr.java,v 1.1 2015/01/06 11:07:59 heller Exp $
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
 * @author E.Pailleau
 * @version $Revision: 1.1 $
 */
public class ResourceBundleExportKeyPKCS12_fr extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Valider" },
    {"button.cancel", "Annuler" },
    {"button.browse", "Parcourir..." },
            
    {"keystore.contains.nokeys", "Ce porte-clef ne contient aucune clef priv�e." },
    
    {"label.exportkey", "Exporter le porte-clef (PKCS#12):" },
    {"label.keypass", "Mot de passe d'export du porte-clef:" },
            
    {"title", "Export de la clef vers le porte-clef (PKCS#12 format)" },
    {"filechooser.key.export", "Merci de s�lectionner le fichier porte-clef PKCS#12 pour l'export" },
                
    {"key.export.success.message", "La clef a �t� export�e avec succ�s." },
    {"key.export.success.title", "Succ�s" },
    {"key.export.error.message", "Une erreur a eu lieu lors du processus d'export.\n{0}" },
    {"key.export.error.title", "Erreur" },
    
    {"label.alias", "Clef � exporter:" },
    {"key.exported.to.file", "La clef \"{0}\" a �t� ins�r�e dans le porte-clef \"{1}\"." },
  };		
  
}
