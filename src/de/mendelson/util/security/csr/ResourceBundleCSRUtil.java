//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/csr/ResourceBundleCSRUtil.java,v 1.1 2015/01/06 11:08:02 heller Exp $
package de.mendelson.util.security.csr;
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
public class ResourceBundleCSRUtil extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {                
        {"verification.failed", "Operation failed - Verification of the created CSR failed" },
        {"no.certificates.in.reply", "Operation failed - No certificates found in CSR reply, unable to patch the key" },
        {"missing.cert.in.trustchain", "Operation failed - The system failed to establish the trust chain from the reply.\nPlease import the certificate with the issuer\n{0}\ninto the keystore first." },
        {"response.chain.incomplete", "Operation failed - The certificate chain of the response is incomplete" },
        {"response.verification.failed", "Operation failed - Problem verifying the certificate chain of the response: {0}" },
        {"response.public.key.does.not.match", "Operation failed - This is not the CAs answer for this key." },
    };


    

}