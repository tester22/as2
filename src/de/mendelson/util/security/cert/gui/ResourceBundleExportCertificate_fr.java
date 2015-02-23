//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleExportCertificate_fr.java,v 1.1 2015/01/06 11:07:59 heller Exp $
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
public class ResourceBundleExportCertificate_fr extends MecResourceBundle{

    @Override
  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Valider" },
    {"button.cancel", "Annuler" },
    {"button.browse", "Parcourir..." },
            
    {"title", "Exporter un certificat X.509" },
    {"label.exportfile", "Fichier d'export:" },
    {"label.alias", "Alias:" },        
    {"label.encoding", "Encodage:" },
    {"filechooser.certificate.export", "Merci de s�lectionner le fichier d'export du certificat." },
    {"certificate.export.error.title", "L'export du certificat a �chou�" },
    {"certificate.export.error.message", "L'export du certificat suivant a �chou�:\n{0}" },
    {"certificate.export.success.title", "Succ�s" },
    {"certificate.export.success.message", "Le certificat a �t� export� avec succ�s a\n\"{0}\"" }, 
    {JDialogExportCertificate.PEM, "Format texte (PEM. *.cer)" },
    {JDialogExportCertificate.DER, "Format binaire (DER, *.cer)" },
    {JDialogExportCertificate.PKCS7, "Avec cha�ne de confiance (PKCS#7, *.p7b)" },    
  };		
  
}
