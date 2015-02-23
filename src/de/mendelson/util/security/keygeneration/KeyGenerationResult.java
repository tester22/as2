//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/keygeneration/KeyGenerationResult.java,v 1.1 2015/01/06 11:08:02 heller Exp $
package de.mendelson.util.security.keygeneration;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores key generation results
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class KeyGenerationResult {

    private KeyPair keyPair = null;
    private X509Certificate certificate = null;

    public KeyGenerationResult(KeyPair keyPair, X509Certificate certificate) {
        this.keyPair = keyPair;
        this.certificate = certificate;
    }

    /**
     * @return the keyPair
     */
    public KeyPair getKeyPair() {
        return keyPair;
    }

    /**
     * @return the certificate
     */
    public X509Certificate getCertificate() {
        return certificate;
    }
}
