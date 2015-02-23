//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleExportCertificate_de.java,v 1.1 2015/01/06 11:07:59 heller Exp $ 
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
public class ResourceBundleExportCertificate_de extends MecResourceBundle{

    @Override
  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Abbrechen" },
    {"button.browse", "Durchsuchen" },                    
            
    {"title", "X.509 Zertifikat exportieren" },
    {"label.exportfile", "Exportdatei:" },
    {"label.alias", "Alias:" },        
    {"label.encoding", "Format:" },   
    {"filechooser.certificate.export", "Bitte w�hlen Sie den Dateinamen f�r den Export." },        
    {"certificate.export.error.title", "Fehler beim Export" },
    {"certificate.export.error.message", "Der Export des Zertifikates schlug fehl:\n{0}" },        
    {"certificate.export.success.title", "Erfolg" },
    {"certificate.export.success.message", "Das Zertifikat konnte erfolgreich exportiert werden nach\n\"{0}\"" },   
    {JDialogExportCertificate.PEM, "Textformat (PEM, *.cer)" },
    {JDialogExportCertificate.DER, "Bin�rformat (DER, *.cer)" },
    {JDialogExportCertificate.PKCS7, "Mit Zertifizierungskette (PKCS#7, *.p7b)" },      
  };		
  
}