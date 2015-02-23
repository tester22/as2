//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleCertificates.java,v 1.1 2015/01/06 11:07:59 heller Exp $
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
public class ResourceBundleCertificates extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        
        {"display.root.certs", "Show root certificates" },        
        {"button.delete", "Delete key/certificate" },
        {"button.edit", "Rename alias" },
        {"button.newkey", "Import key" },
        {"button.newcertificate", "Import certificate" },
        {"button.export", "Export certificate" },                
        {"menu.file", "File" },        
        {"menu.file.close", "Close" },
        {"menu.import", "Import" },        
        {"menu.export", "Export" },
        {"menu.tools", "Tools" },
        {"menu.tools.generatekey", "Generate new key (self signed)" },
        {"menu.tools.generatecsr", "Trust certificate: Generate CSR to CA" },
        {"menu.tools.generatecsr.renew", "Renew certificate: Generate CSR to CA" },
        {"menu.tools.importcsr", "Trust certificate: Import CAs answer to CSR" },
        {"menu.tools.importcsr.renew", "Renew certificate: Import CAs answer to CSR" },
        {"label.selectcsrfile", "Please select the file where to store the request" },
        {"label.cert.import", "Import certificate (from your trading partner)" },
        {"label.cert.export", "Export certificate (for your trading partner)" },
        {"label.key.import.pem", "Import your own private key (from PEM)" },
        {"label.key.import.pkcs12", "Import your own private key (from PKCS#12)" },     
        {"label.key.import.jks", "Import your own private key (from JKS, JAVA keystore format)" },     
        {"label.key.export.pkcs12", "Export your own private key (PKCS#12) (for backup purpose only!)" },        
        {"label.keystore", "Keystore file:" },        
        {"title.signencrypt", "Avaliable certificates and keys (encryption, signature)" },
        {"title.ssl", "Avaliable certificates and keys (SSL)" },                
        {"button.ok", "Ok" },
        {"button.cancel", "Cancel" },
        {"filechooser.certificate.import", "Please select the certificate file for the import" },
        {"certificate.import.success.message", "The certificate has been imported successfully using the alias \"{0}\"" },
        {"certificate.root.import.success.message", "The root certificate has been imported successfully using the alias \"{0}\"." },
        {"certificate.import.success.title", "Success" },
        {"certificate.import.error.message", "There occured an error during the import process.\n{0}" },
        {"certificate.import.error.title", "Error" },
        {"certificate.import.alias", "Certificate alias to use:" },
        {"keystore.readonly.message", "The underlaying keystore is read-only.\nThis operation is not permitted." },
        {"keystore.readonly.title", "Keystore r/o" },
        {"modifications.notalllowed.message", "Modifications are not possible"},
        {"generatekey.error.message", "{0}" },
        {"generatekey.error.title", "Error while key generation" },
        {"tab.info.basic", "Details" },
        {"tab.info.extension", "Extension" },
        {"csr.title", "Trust certificate: Certificate Sign Request" },
        {"csr.title.renew", "Renew certificate: Certificate Sign Request" },
        {"csr.message.storequestion", "Would you like to trust the key at the mendelson CA\nor store the CSR to a file?" },        
        {"csr.message.storequestion.renew", "Would you like to renew the key at the mendelson CA\nor store the CSR to a file?" },        
        {"csr.generation.success.message", "The CSR has been stored to the file\n\"{0}\".\nPlease send it to your CA to perform the trust request.\nWe would suggest the mendelson CA (http://ca.mendelson-e-c.com)."},
        {"csr.option.1", "Trust at mendelson CA" },
        {"csr.option.1.renew", "Renew at mendelson CA" },
        {"csr.option.2", "Store to a file" },
        {"csr.generation.success.title", "CSR generated successfully"},
        {"csr.generation.failure.title", "CSR generation failed"},
        {"csr.generation.failure.message", "{0}"},
        {"label.selectcsrrepsonsefile", "Please select the CA answer file" },
        {"csrresponse.import.success.message", "The key has been successfully patched with the CA answer." },
        {"csrresponse.import.success.title", "Key trust path established" },
        {"csrresponse.import.failure.message", "{0}" },
        {"csrresponse.import.failure.title", "Problem patching the key" },
        {"dialog.cert.delete.message", "Do you really want to delete the certificate with the alias \"{0}\"?"},
        {"dialog.cert.delete.title", "Delete certificate"},        
        {"title.cert.in.use", "Certificate is in use" },
        {"cert.delete.impossible", "It is impossible to delete the entry:" },
        {"module.locked", "This certificate management is locked by another client, you are not allowed to commit your changes!" },
    };
    
}