//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/CertificateInUseChecker.java,v 1.1 2015/01/06 11:07:57 heller Exp $
package de.mendelson.util.security.cert;

import java.util.List;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Checks if a certificate is in use
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public interface CertificateInUseChecker {

    /**Returns null if this certificate is not in use*/
    public List<CertificateInUseInfo> checkUsed(KeystoreCertificate cert); 
    
}
