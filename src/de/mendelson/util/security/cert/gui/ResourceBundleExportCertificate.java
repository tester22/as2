//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleExportCertificate.java,v 1.1 2015/01/06 11:07:59 heller Exp $ 
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
public class ResourceBundleExportCertificate extends MecResourceBundle{

    @Override
  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Cancel" },
    {"button.browse", "Browse" },        
            
    {"title", "Export X.509 Certificate" },
    {"label.exportfile", "Export file:" },
    {"label.alias", "Alias:" },        
    {"label.encoding", "Encoding:" },        
    {"filechooser.certificate.export", "Please select the filename to export the certificate to." },        
    {"certificate.export.error.title", "Certificate export failed" },
    {"certificate.export.error.message", "The export of this certificate failed:\n{0}" },        
    {"certificate.export.success.title", "Success" },
    {"certificate.export.success.message", "The certificate has been exported successfully to\n\"{0}\"." }, 
    {JDialogExportCertificate.PEM, "Text format (PEM, *.cer)" },
    {JDialogExportCertificate.DER, "Binary format (DER, *.cer)" },
    {JDialogExportCertificate.PKCS7, "With full trust chain (PKCS#7, *.p7b)" },            
  };		
  
}