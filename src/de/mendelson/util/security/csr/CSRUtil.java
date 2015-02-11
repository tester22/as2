//$Header: /cvsroot/mec-as2/b47/de/mendelson/util/security/csr/CSRUtil.java,v 1.1 2015/01/06 11:08:02 heller Exp $
package de.mendelson.util.security.csr;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.CertificateManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.openssl.PEMWriter;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Handles csr related activities on a certificate
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class CSRUtil {

    private MecResourceBundle rb;

    public CSRUtil() {
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCSRUtil.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Generates a PKCS10 CertificationRequest. The passed private key must not be trusted
     */
    public PKCS10CertificationRequest generateCSR(PrivateKey key, X509Certificate cert) throws Exception {
        X509Name subject = new X509Name(cert.getSubjectDN().toString());
        PKCS10CertificationRequest csr =
                new PKCS10CertificationRequest(cert.getSigAlgName(),
                subject, cert.getPublicKey(), null,
                key);
        boolean verified = csr.verify();
        if (!verified) {
            throw new Exception(this.rb.getResourceString("verification.failed"));
        }
        return (csr);
    }

    /**
     * Generates a PKCS10 CertificationRequest. The passed private key must not be trusted
     */
    public PKCS10CertificationRequest generateCSR(CertificateManager manager, String alias) throws Exception {
        PrivateKey key = manager.getPrivateKey(alias);
        KeyStoreUtil keystoreUtil = new KeyStoreUtil();
        Certificate[] certchain = manager.getCertificateChain(alias);
        X509Certificate[] x509Certchain = new X509Certificate[certchain.length];
        for (int i = 0; i < certchain.length; i++) {
            x509Certchain[i] = (X509Certificate) certchain[i];
        }
        x509Certchain = keystoreUtil.orderX509CertChain(x509Certchain);
        PKCS10CertificationRequest csr = this.generateCSR(key, x509Certchain[0]);
        return (csr);
    }

    /**Writes the CSR to a string*/
    public String storeCSRPEM( PKCS10CertificationRequest csr )throws Exception{
        PEMWriter pemWriter = null;  
        StringWriter stringWriter = new StringWriter();;
        try {            
            pemWriter = new PEMWriter(stringWriter);
            pemWriter.writeObject(csr);
            pemWriter.flush();
        } finally {
            if (pemWriter != null) {
                pemWriter.close();
            }
        }
        return( stringWriter.toString());
    }
    
    /**Writes a csr to a file, PEM encoded*/
    public void storeCSRPEM(PKCS10CertificationRequest csr, File outFile) throws Exception {
        PEMWriter pemWriter = null;
        try {
            pemWriter = new PEMWriter(new FileWriter(outFile));
            pemWriter.writeObject(csr);
            pemWriter.flush();
        } finally {
            if (pemWriter != null) {
                pemWriter.close();
            }
        }
    }

    /**
     * Imports the answer of the CA which looks like a certificate. The patched certificate will be 
     * updated with the cert chain that is included in the returned signed certificate.
     *
     */
    public boolean importCSRReply(CertificateManager manager, String alias, File csrResponseFile) throws Throwable {
        PrivateKey key = manager.getPrivateKey(alias);
        PublicKey publicKey = manager.getPublicKey(alias);
        // Load certificates found in the PEM(!) encoded answer
        List<X509Certificate> responseCertList = new ArrayList<X509Certificate>();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(csrResponseFile);
            for (Certificate responseCert : CertificateFactory.getInstance("X509").generateCertificates(inputStream)) {
                responseCertList.add((X509Certificate) responseCert);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        if (responseCertList.isEmpty()) {
            throw new Exception(this.rb.getResourceString("no.certificates.in.reply"));
        }
        PublicKey responsePublicKey = responseCertList.get(responseCertList.size()-1).getPublicKey();
        if( !publicKey.equals(responsePublicKey)){
            throw new Exception(this.rb.getResourceString("response.public.key.does.not.match"));
        }
        List<X509Certificate> newCerts;
        if (responseCertList.size() == 1) {
            // Reply has only one certificate
            newCerts = this.buildNewTrustChain(manager, responseCertList.get(0));
        } else {
            // Reply has a chain of certificates
            newCerts = this.validateReply(responseCertList);
        }
        if (newCerts != null) {
            manager.setKeyEntry(alias, key, newCerts.toArray(new X509Certificate[newCerts.size()]));
            return true;
        } else {
            return false;
        }
    }

    private List<X509Certificate> buildNewTrustChain(CertificateManager manager, X509Certificate certReply)
            throws Exception {
        Map<X500Principal, List<X509Certificate>> knownCerts = manager.getIssuerCertificateMap();
        LinkedList<X509Certificate> newTrustChain = new LinkedList<X509Certificate>();
        this.buildNewTrustChainRecursive(manager, certReply, newTrustChain, knownCerts);
        return (newTrustChain);
    }

    /**
     * Builds a new certificate chain from the answer
     */
    private void buildNewTrustChainRecursive(CertificateManager manager, X509Certificate certificate, LinkedList<X509Certificate> newTrustChain,
            Map<X500Principal, List<X509Certificate>> availableCertificates) throws Exception {
        X500Principal subject = certificate.getSubjectX500Principal();
        X500Principal issuer = certificate.getIssuerX500Principal();
        // Check if the certificate is a root certificate (i.e. was issued by the same Principal that
        // is present in the subject)
        if (subject.equals(issuer)) {
            newTrustChain.addFirst(certificate);
            return;
        }
        // Get the list of known certificates of the certificate's issuer
        List<X509Certificate> issuerCerts = availableCertificates.get(issuer);
        if (issuerCerts == null || issuerCerts.isEmpty()) {
            // A certificate is in the chain that is missing in the available certificates -> has to be imported first
            throw new Exception(this.rb.getResourceString("missing.cert.in.trustchain", issuer));
        }
        for (X509Certificate issuerCert : issuerCerts) {
            PublicKey publickey = issuerCert.getPublicKey();
            // Verify the certificate with the specified public key
            certificate.verify(publickey);
            this.buildNewTrustChainRecursive(manager, issuerCert, newTrustChain, availableCertificates);
        }
        newTrustChain.addFirst(certificate);
    }

    /**
     * Validates chain in certification reply, and returns the ordered
     * elements of the chain (with user certificate first, and root
     * certificate last in the array).
     *
     * @param alias the alias name
     * @param userCert the user certificate of the alias
     * @param replyCerts the chain provided in the reply
     */
    private List<X509Certificate> validateReply(List<X509Certificate> replyCerts) throws Exception {
        // order the certs in the reply (bottom-up).
        X509Certificate tmpCert = null;
        Principal issuer = replyCerts.get(0).getIssuerDN();
        for (int i = 1; i < replyCerts.size(); i++) {
            // find a cert in the reply whose "subject" is the same as the
            // given "issuer"
            int j;
            for (j = i; j < replyCerts.size(); j++) {
                Principal subject = replyCerts.get(j).getSubjectDN();
                if (subject.equals(issuer)) {
                    tmpCert = replyCerts.get(i);
                    replyCerts.set(i, replyCerts.get(j));
                    replyCerts.set(j, tmpCert);
                    issuer = replyCerts.get(i).getIssuerDN();
                    break;
                }
            }
            if (j == replyCerts.size()) {
                throw new Exception(this.rb.getResourceString("response.chain.incomplete"));
            }
        }
        // now verify each cert in the ordered chain
        for (int i = 0; i < replyCerts.size(); i++) {
            PublicKey pubKey = replyCerts.get(i + 1).getPublicKey();
            try {
                replyCerts.get(i).verify(pubKey);
            } catch (Exception e) {
                throw new Exception(this.rb.getResourceString("response.verification.failed", e.getMessage()));
            }
        }
        return replyCerts;
    }
}
