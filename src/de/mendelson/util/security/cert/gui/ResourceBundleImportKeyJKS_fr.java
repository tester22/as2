//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyJKS_fr.java,v 1.1 2015/01/06 11:07:59 heller Exp $
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
public class ResourceBundleImportKeyJKS_fr extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Valider" },
    {"button.cancel", "Annuler" },
    {"button.browse", "Parcourir..." },
            
    {"keystore.contains.nokeys", "Ce porte-clef ne contient aucune clef priv�e." },
    
    {"label.importkey", "Importer le fichier de clef (JKS):" },
    {"label.keypass", "Mot de passe d'import de la clef:" },
            
    {"title", "Importer les clefs depuis le fichier porte-clef (JKS format)" },
    {"filechooser.key.import", "Merci de s�lectionner le fichier porte-clef JKS pour l'import" },
            
    {"multiple.keys.message", "Merci de s�lectionner la clef � importer" },
    {"multiple.keys.title", "Le porte-clef contient plusieurs clefs" },

    {"key.import.success.message", "La clef a �t� import�e avec succ�s." },
    {"key.import.success.title", "Succ�s" },
    {"key.import.error.message", "Une erreur a eu lieu lors du processus d'import.\n{0}" },
    {"key.import.error.title", "Erreur" },

    {"enter.keypassword", "Entrer le mot de passe de la clef pour \"{0}\"" },
    
  };		
  
}
