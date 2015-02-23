//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/BCCryptoHelper.java,v 1.1 2015/01/06 11:07:56 heller Exp $
package de.mendelson.util.security;

import de.mendelson.util.security.cert.KeystoreCertificate;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientId;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.cms.jcajce.ZlibCompressor;
import org.bouncycastle.cms.jcajce.ZlibExpanderProvider;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEEnveloped;
import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESigned;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.bouncycastle.mail.smime.SMIMEUtil;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Utility class to handle bouncycastle cryptography
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class BCCryptoHelper {

    public static final String ALGORITHM_3DES = "3des";
    public static final String ALGORITHM_DES = "des";
    public static final String ALGORITHM_RC2 = "rc2";
    public static final String ALGORITHM_RC4 = "rc4";
    public static final String ALGORITHM_AES_128 = "aes128";
    public static final String ALGORITHM_AES_192 = "aes192";
    public static final String ALGORITHM_AES_256 = "aes256";
    public static final String ALGORITHM_MD5 = "md5";
    public static final String ALGORITHM_SHA1 = "sha1";
    public static final String ALGORITHM_SHA224 = "sha-224";
    public static final String ALGORITHM_SHA256 = "sha-256";
    public static final String ALGORITHM_SHA384 = "sha-384";
    public static final String ALGORITHM_SHA512 = "sha-512";
    public static final String ALGORITHM_IDEA = "idea";
    public static final String ALGORITHM_CAST5 = "cast5";
    public static final String KEYSTORE_PKCS12 = "PKCS12";
    public static final String KEYSTORE_JKS = "JKS";

    public BCCryptoHelper() {
    }

    public boolean isEncrypted(MimeBodyPart part) throws MessagingException {
        if (part == null) {
            throw new MessagingException("Part is absent");
        }
        ContentType contentType = new ContentType(part.getContentType());
        String baseType = contentType.getBaseType().toLowerCase();
        if (baseType.equalsIgnoreCase("application/pkcs7-mime")) {
            String smimeType = contentType.getParameter("smime-type");
            return smimeType != null && smimeType.equalsIgnoreCase("enveloped-data");
        } else {
            return false;
        }
    }

    /**
     * Performs an encryption with a 192 bit key, this will fail if the
     * unlimited strength policy files have not been installed for the VM This
     * method will throw an exception if there is a general security problem,
     * e.g. the provider is not found
     *
     * @return true if the VM is patched, false if the VM is not pached
     */
    public boolean performUnlimitedStrengthJurisdictionPolicyTest() {
        byte[] data = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
        SecretKeySpec key192 = new SecretKeySpec(
                new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                    0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                    0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17
                },
                "Blowfish");
        try {
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key192);
            cipher.doFinal(data);
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }

    /**
     * Returns the signed part of this container if it exists, else null. If the
     * container itself is signed it is returned. You could use this method if
     * you are not sure if the main container of a message is the signed part or
     * if there are some unused MIME wrappers around it that embedd the signed
     * part
     */
    public Part getSignedEmbeddedPart(Part part) throws MessagingException, IOException {
        if (part == null) {
            throw new MessagingException("Part is absent");
        }
        if (part.isMimeType("multipart/signed")) {
            return (part);
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multiPart = (Multipart) part.getContent();
            int count = multiPart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multiPart.getBodyPart(i);
                Part signedEmbeddedPart = this.getSignedEmbeddedPart(bodyPart);
                if (signedEmbeddedPart != null) {
                    return (signedEmbeddedPart);
                }
            }
        }
        return (null);
    }

    /**
     * Checks if two mics in the format <base64>, <digest> are equal
     *
     * @param mic1
     * @param mic2
     * @return
     */
    public boolean micIsEqual(String mic1, String mic2) {
        try {
            mic1 = mic1.trim();
            mic2 = mic2.trim();
            if (mic1.equals(mic2)) {
                return (true);
            }
            //parse the mics
            int index1 = mic1.lastIndexOf(',');
            int index2 = mic2.lastIndexOf(',');
            String digest1 = mic1.substring(index1 + 1).trim();
            String digest2 = mic2.substring(index2 + 1).trim();
            String oid1 = this.convertAlgorithmNameToOID(digest1);
            String oid2 = this.convertAlgorithmNameToOID(digest2);
            String hashbase641 = mic1.substring(0, index1);
            String hashbase642 = mic2.substring(0, index2);
            byte[] bytes1 = Base64.decode(hashbase641);
            byte[] bytes2 = Base64.decode(hashbase642);
            DigestInputStream inStream1 = new DigestInputStream(new ByteArrayInputStream(bytes1), MessageDigest.getInstance(oid1, "BC"));
            DigestInputStream inStream2 = new DigestInputStream(new ByteArrayInputStream(bytes2), MessageDigest.getInstance(oid2, "BC"));
            ByteArrayOutputStream hashValueStream1 = new ByteArrayOutputStream();
            ByteArrayOutputStream hashValueStream2 = new ByteArrayOutputStream();
            this.copyStreams(inStream1, hashValueStream1);
            this.copyStreams(inStream2, hashValueStream2);
            inStream1.close();
            inStream2.close();
            hashValueStream1.close();
            hashValueStream2.close();
            byte[] bytesHashValue1 = hashValueStream1.toByteArray();
            byte[] bytesHashValue2 = hashValueStream2.toByteArray();
            if (bytesHashValue1.length != bytesHashValue2.length) {
                return (false);
            }
            for (int i = 0; i < bytesHashValue1.length; i++) {
                if (bytesHashValue1[i] != bytesHashValue2[i]) {
                    return (false);
                }
            }
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
    }

    /**
     * Copies all data from one stream to another
     */
    private void copyStreams(InputStream in, OutputStream out) throws IOException {
        BufferedInputStream inStream = new BufferedInputStream(in);
        BufferedOutputStream outStream = new BufferedOutputStream(out);
        //copy the contents to an output stream
        byte[] buffer = new byte[2048];
        int read = 2048;
        //a read of 0 must be allowed, sometimes it takes time to
        //extract data from the input
        while (read != -1) {
            read = inStream.read(buffer);
            if (read > 0) {
                outStream.write(buffer, 0, read);
            }
        }
        outStream.flush();
    }

    /**
     * Displays a bundle of byte arrays as hex string, for debug purpose only
     */
    private String toHexDisplay(byte[] data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            result.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
            result.append(" ");
        }
        return result.toString();
    }

    /**
     * Calculates the hash value for a passed byte array, base 64 encoded
     *
     * @param digestAlgOID digest OID algorithm, e.g. "1.3.14.3.2.26"
     */
    public String calculateMIC(InputStream dataStream, String digestAlgOID) throws GeneralSecurityException, MessagingException, IOException {
        if (dataStream == null) {
            throw new GeneralSecurityException("calculateMIC: Unable to calculate MIC - processed data is absent");
        }
        MessageDigest messageDigest = MessageDigest.getInstance(digestAlgOID, "BC");
        DigestInputStream digestInputStream = new DigestInputStream(dataStream, messageDigest);
        //perform filter operation
        for (byte buf[] = new byte[4096]; digestInputStream.read(buf) >= 0;) {
        }
        byte mic[] = digestInputStream.getMessageDigest().digest();
        digestInputStream.close();
        String micString = new String(Base64.encode(mic));
        return (micString);
    }

    /**
     * Calculates the hash value for a passed body part, base 64 encoded
     *
     * @param digestAlgOID digest OID algorithm, e.g. "1.3.14.3.2.26"
     */
    public String calculateMIC(Part part, String digestAlgOID) throws GeneralSecurityException, MessagingException, IOException {
        if (part == null) {
            throw new GeneralSecurityException("calculateMIC: Unable to calculate MIC - MIME part is absent");
        }
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        part.writeTo(bOut);
        bOut.flush();
        bOut.close();
        byte data[] = bOut.toByteArray();
        return (this.calculateMIC(new ByteArrayInputStream(data), digestAlgOID));
    }

    /**
     * Used for mendelson rosettaNet
     */
    public MimeBodyPart decrypt(MimeBodyPart part, Certificate cert, Key key) throws GeneralSecurityException, MessagingException, CMSException, IOException, SMIMEException {
        if (!this.isEncrypted(part)) {
            throw new GeneralSecurityException("decrypt: Unable to decrypt - Content-Type indicates data isn't encrypted");
        }
        X509Certificate x509Cert = castCertificate(cert);
        SMIMEEnveloped envelope = new SMIMEEnveloped(part);
        RecipientId recipientId = new JceKeyTransRecipientId(x509Cert);
        RecipientInformation recipient = envelope.getRecipientInfos().get(recipientId);
        if (recipient == null) {
            throw new GeneralSecurityException("decrypt: Unable to decrypt data - wrong key used to decrypt the data.");
        } else {
            MimeBodyPart bodyPart = SMIMEUtil.toMimeBodyPart(
                    recipient.getContentStream(new JceKeyTransEnvelopedRecipient(this.getPrivateKey(key)).setProvider("BC")));
            return (bodyPart);
        }
    }

    public void deinitialize() {
    }

    /**
     * @param algorith a algorith alias name, e.g. "3des", wil be translated
     * into the right IOD number internal
     */
    public MimeBodyPart encrypt(MimeMessage part, Certificate cert, String algorithm) throws Exception {
        X509Certificate x509Cert = castCertificate(cert);
        String encAlgOID = this.convertAlgorithmNameToOID(algorithm);
        SMIMEEnvelopedGenerator generator = new SMIMEEnvelopedGenerator();
        generator.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(x509Cert).setProvider("BC"));
        if (part == null) {
            throw new GeneralSecurityException("encrypt: Part is absent");
        }
        MimeBodyPart encData = generator.generate(part, new JceCMSContentEncryptorBuilder(new ASN1ObjectIdentifier(encAlgOID)).setProvider("BC").build());
        return encData;
    }

    /**
     * @param algorith a algorith alias name, e.g. "3des", will be translated
     * into the right IOD number internal
     */
    public MimeBodyPart encrypt(MimeBodyPart part, Certificate cert, String algorithm) throws Exception {
        X509Certificate x509Cert = castCertificate(cert);
        String encAlgOID = this.convertAlgorithmNameToOID(algorithm);
        SMIMEEnvelopedGenerator generator = new SMIMEEnvelopedGenerator();
        generator.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(x509Cert).setProvider("BC"));
        if (part == null) {
            throw new GeneralSecurityException("encrypt: Part is absent");
        }
        MimeBodyPart encData = generator.generate(part, new JceCMSContentEncryptorBuilder(new ASN1ObjectIdentifier(encAlgOID)).setProvider("BC").build());
        return encData;
    }

    public void initialize() {
        Security.addProvider(new BouncyCastleProvider());
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("application/pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_signature");
        mc.addMailcap("application/pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_mime");
        mc.addMailcap("application/x-pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
        mc.addMailcap("application/x-pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
        mc.addMailcap("multipart/signed;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.multipart_signed");
        CommandMap.setDefaultCommandMap(mc);
        //As of JavaMail 1.4.1 and later caching was introduced for Multipart objects,
        //this can cause some issues for signature verification as occasionally the cache does not produce exactly
        //the same message as was read in.
        System.setProperty("mail.mime.cachemultipart", "false");
    }

    /**
     * Create a pkcs7-signature of the passed content and returns it
     *
     * @param chain certificate chain, chain[0] is the signers certificate
     * itself
     * @param embeddOriginalData Indicates if the original data should be
     * embedded in the signature
     *
     */
    public byte[] sign(byte[] content, Certificate[] chain, Key key, String digest,
            boolean embeddOriginalData) throws Exception {
        X509Certificate x509Cert = this.castCertificate(chain[0]);
        PrivateKey privKey = this.getPrivateKey(key);
        CMSSignedDataGenerator signedDataGenerator = new CMSSignedDataGenerator();
        //add dont know
        ASN1EncodableVector signedAttrs = new ASN1EncodableVector();
        SMIMECapabilityVector caps = new SMIMECapabilityVector();
        caps.addCapability(SMIMECapability.dES_EDE3_CBC);
        caps.addCapability(SMIMECapability.rC2_CBC, 128);
        caps.addCapability(SMIMECapability.dES_CBC);
        signedAttrs.add(new SMIMECapabilitiesAttribute(caps));
        if (digest.equalsIgnoreCase(ALGORITHM_SHA1)) {
            signedDataGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA1withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_MD5)) {
            signedDataGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("MD5withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA224)) {
            signedDataGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA224withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA256)) {
            signedDataGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA256withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA384)) {
            signedDataGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA384withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA512)) {
            signedDataGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA512withRSA", privKey, x509Cert));
        } else {
            throw new Exception("sign: Signing digest " + digest + " not supported.");
        }
        //add cert store
        List<Certificate> certList = Arrays.asList(chain);
        Store certStore = new JcaCertStore(certList);
        signedDataGenerator.addCertificates(certStore);
        if (content == null) {
            throw new Exception("sign: content is absent");
        }
        CMSTypedData processable = new CMSProcessableByteArray(content);
        CMSSignedData signatureData = signedDataGenerator.generate(processable, embeddOriginalData);
        return (signatureData.getEncoded());
    }

    /**
     * Create a pkcs7-signature of the passed content and returns it, without
     * embedding the original data in the signature
     *
     * @param chain certificate chain, chain[0] is the signers certificate
     * itself
     *
     */
    public byte[] sign(byte[] content, Certificate[] chain, Key key, String digest) throws Exception {
        return (this.sign(content, chain, key, digest, false));
    }

    /**
     * @param chain certificate chain, chain[0] is the signers certificate
     * itself Signs the data using S/MIME 3.1 - dont use if for S/MIME 3.2 or
     * higher
     */
    public MimeMultipart sign(MimeBodyPart body, Certificate[] chain, Key key, String digest) throws Exception {
        X509Certificate x509Cert = this.castCertificate(chain[0]);
        PrivateKey privKey = this.getPrivateKey(key);
        //call this generator with a S/MIME 3.1 compatible constructor as it defaults to RFC 5751 (other micalg values)
        SMIMESignedGenerator signedGenerator = new SMIMESignedGenerator(SMIMESignedGenerator.RFC3851_MICALGS);
        //add dont know
        ASN1EncodableVector signedAttrs = new ASN1EncodableVector();
        SMIMECapabilityVector caps = new SMIMECapabilityVector();
        caps.addCapability(SMIMECapability.dES_EDE3_CBC);
        caps.addCapability(SMIMECapability.rC2_CBC, 128);
        caps.addCapability(SMIMECapability.dES_CBC);
        signedAttrs.add(new SMIMECapabilitiesAttribute(caps));
        if (digest.equalsIgnoreCase(ALGORITHM_SHA1)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA1withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA224)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA224withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA256)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA256withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA384)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA384withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA512)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA512withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_MD5)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("MD5withRSA", privKey, x509Cert));
        } else {
            throw new Exception("sign: Signing digest " + digest + " not supported.");
        }
        //add cert store
        List<Certificate> certList = Arrays.asList(chain);
        Store certStore = new JcaCertStore(certList);
        signedGenerator.addCertificates(certStore);
        MimeMultipart signedPart = signedGenerator.generate(body);
        return (signedPart);
    }

    /**
     * @param chain certificate chain, chain[0] is the signers certificate
     * itself Signs the data using S/MIME 3.1 - dont use if for S/MIME 3.2 or
     * higher
     */
    public MimeMultipart sign(MimeMessage message, Certificate[] chain, Key key, String digest) throws Exception {
        if (message == null) {
            throw new Exception("sign: Message is absent");
        }
        X509Certificate x509Cert = this.castCertificate(chain[0]);
        PrivateKey privKey = this.getPrivateKey(key);
        SMIMESignedGenerator signedGenerator = new SMIMESignedGenerator(SMIMESignedGenerator.RFC3851_MICALGS);
        //add dont know
        ASN1EncodableVector signedAttrs = new ASN1EncodableVector();
        SMIMECapabilityVector caps = new SMIMECapabilityVector();
        caps.addCapability(SMIMECapability.dES_EDE3_CBC);
        caps.addCapability(SMIMECapability.rC2_CBC, 128);
        caps.addCapability(SMIMECapability.dES_CBC);
        signedAttrs.add(new SMIMECapabilitiesAttribute(caps));
        if (digest.equalsIgnoreCase(ALGORITHM_SHA1)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA1withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA224)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA224withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA256)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA256withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA384)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA384withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_SHA512)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("SHA512withRSA", privKey, x509Cert));
        } else if (digest.equalsIgnoreCase(ALGORITHM_MD5)) {
            signedGenerator.addSignerInfoGenerator(
                    new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(
                            new AttributeTable(signedAttrs)).build("MD5withRSA", privKey, x509Cert));
        } else {
            throw new Exception("sign: Signing digest " + digest + " not supported.");
        }
        //add cert store
        List<Certificate> certList = Arrays.asList(chain);
        Store certStore = new JcaCertStore(certList);
        signedGenerator.addCertificates(certStore);
        MimeMultipart multipart = signedGenerator.generate(message);
        return (multipart);
    }

    /**
     * @param chain certificate chain, chain[0] is the signers certificate
     * itself
     */
    public MimeMessage signToMessage(MimeMessage message, Certificate[] chain, Key key, String digest) throws Exception {
        MimeMultipart multipart = this.sign(message, chain, key, digest);
        MimeMessage signedMessage = new MimeMessage(Session.getInstance(System.getProperties(), null));
        signedMessage.setContent(multipart, multipart.getContentType());
        signedMessage.saveChanges();
        return (signedMessage);
    }

    /**
     * Returns the digest OID algorithm from a signature that signes the passed
     * message part The return value for sha1 is e.g. "1.3.14.3.2.26".
     */
    public String getDigestAlgOIDFromSignature(Part part) throws Exception {
        if (part == null) {
            throw new GeneralSecurityException("getDigestAlgOIDFromSignature: Part is absent");
        }
        if (part.isMimeType("multipart/signed")) {
            MimeMultipart signedMultiPart = null;
            if (part.getContent() instanceof MimeMultipart) {
                signedMultiPart = (MimeMultipart) part.getContent();
            } else {
                //assuming it is an inputstream now
                signedMultiPart = new MimeMultipart(new ByteArrayDataSource((InputStream) part.getContent(), part.getContentType()));
            }
            SMIMESigned signed = new SMIMESigned(signedMultiPart);
            SignerInformationStore signerStore = signed.getSignerInfos();
            Iterator iterator = signerStore.getSigners().iterator();
            while (iterator.hasNext()) {
                SignerInformation signerInfo = (SignerInformation) iterator.next();
                return (signerInfo.getDigestAlgOID());
            }
            throw new GeneralSecurityException("getDigestAlgOIDFromSignature: Unable to identify signature algorithm.");
        }
        throw new GeneralSecurityException("Content-Type indicates data isn't signed");
    }

    /**
     * Returns the digest OID algorithm from a pkcs7 signature The return value
     * for sha1 is e.g. "1.3.14.3.2.26".
     */
    public String getDigestAlgOIDFromSignature(byte[] signature) throws Exception {
        if (signature == null) {
            throw new GeneralSecurityException("getDigestAlgOIDFromSignature: Signature is absent");
        }
        CMSSignedData signedData = new CMSSignedData(signature);
        SignerInformationStore signers = signedData.getSignerInfos();
        Collection signerCollection = signers.getSigners();
        Iterator iterator = signerCollection.iterator();
        while (iterator.hasNext()) {
            SignerInformation signerInfo = (SignerInformation) iterator.next();
            return (signerInfo.getDigestAlgOID());
        }
        throw new GeneralSecurityException("getDigestAlgOIDFromSignature: Unable to identify signature algorithm.");
    }

     /**
     * Returns the digest OID algorithm from a signature. The return value
     * for sha1 is e.g. "1.3.14.3.2.26".
     */
    public String getDigestAlgOIDFromSignature(InputStream signed, Certificate cert) throws Exception {
        CMSSignedDataParser parser = new CMSSignedDataParser(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build(), signed);
        parser.getSignedContent().drain();
        SignerInformationStore signers = parser.getSignerInfos();
        Collection signerCollection = signers.getSigners();
        Iterator it = signerCollection.iterator();
        boolean verified = false;
        X509CertificateHolder certHolder = new X509CertificateHolder(cert.getEncoded());
        SignerInformationVerifier verifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(certHolder);
        while (it.hasNext()) {
            SignerInformation signerInformation = (SignerInformation) it.next();
            if (!verified) {
                verified = signerInformation.verify(verifier);
                if( verified ){
                    return( signerInformation.getDigestAlgOID());
                }
            }
        }
        throw new GeneralSecurityException("getDigestAlgOIDFromSignature: Unable to identify signature algorithm.");
    }
    
    /**
     * Verifies a signature of a passed content against the passed certificate
     */
    public boolean verify(byte[] content, byte[] signature, Certificate cert) throws Exception {
        if (content == null) {
            throw new GeneralSecurityException("verify: Content is absent");
        }
        if (signature == null) {
            throw new GeneralSecurityException("verify: Signature is absent");
        }
        if (signature.length == 0) {
            throw new Exception("verify: Signature length is 0");
        }
        CMSTypedStream signedContent = new CMSTypedStream(new ByteArrayInputStream(content));
        CMSSignedDataParser dataParser = new CMSSignedDataParser(new BcDigestCalculatorProvider(), signedContent, new ByteArrayInputStream(signature));
        dataParser.getSignedContent().drain();
        SignerInformationStore signers = dataParser.getSignerInfos();
        Collection signerCollection = signers.getSigners();
        Iterator it = signerCollection.iterator();
        X509CertificateHolder certHolder = new X509CertificateHolder(cert.getEncoded());
        SignerInformationVerifier verifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(certHolder);
        boolean verified = false;
        while (it.hasNext()) {
            SignerInformation signerInformation = (SignerInformation) it.next();
            if (!verified) {
                verified = signerInformation.verify(verifier);
            }
            if (verified) {
                break;
            }
        }
        return (verified);
    }

    /**
     * Verifies a signature against the passed certificate
     *
     * @param encoding one of 7bit quoted-printable base64 8bit binary
     */
    public MimeBodyPart verify(Part part, Certificate cert) throws Exception {
        return (this.verify(part, null, cert));
    }

    /**
     * Verifies a signature against the passed certificate
     *
     * @param contentTransferEncoding one of 7bit quoted-printable base64 8bit
     * binary
     */
    public MimeBodyPart verify(Part part, String contentTransferEncoding, Certificate cert) throws Exception {
        if (part == null) {
            throw new GeneralSecurityException("Signature verification failed: Mime part is absent");
        }
        if (part.isMimeType("multipart/signed")) {
            MimeMultipart signedMultiPart = (MimeMultipart) part.getContent();
            //possible encoding: 7bit quoted-printable base64 8bit binary
            SMIMESigned signed = null;
            if (contentTransferEncoding == null) {
                //the default encoding in BC is 7bit but the default content transfer encoding in AS2 is binary.
                signed = new SMIMESigned(signedMultiPart, "binary");
            } else {
                signed = new SMIMESigned(signedMultiPart, contentTransferEncoding);
            }
            X509Certificate x509Cert = this.castCertificate(cert);
            X509CertificateHolder certHolder = new X509CertificateHolder(cert.getEncoded());
            SignerInformationVerifier verifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(certHolder);
            SignerInformationStore signerStore = signed.getSignerInfos();
            Iterator<SignerInformation> iterator = signerStore.getSigners().iterator();
            while (iterator.hasNext()) {
                SignerInformation signerInfo = iterator.next();                                
                if (!signerInfo.verify(verifier)) {
                    StringBuilder signatureCertInfo = new StringBuilder();
                    //try to gain more information about the problem
                    if (signerInfo.getSID() != null) {
                        if (signerInfo.getSID().getSerialNumber() != null) {
                            signatureCertInfo.append("Serial number (DEC): ");
                            signatureCertInfo.append(signerInfo.getSID().getSerialNumber());
                        }
                        if (signerInfo.getSID().getIssuer() != null) {
                            if (signatureCertInfo.length() > 0) {
                                signatureCertInfo.append("\n");
                            }
                            signatureCertInfo.append("Issuer: ");
                            signatureCertInfo.append(signerInfo.getSID().getIssuer().toString());
                        }
                    }
                    if (signatureCertInfo.length() > 0) {
                        signatureCertInfo.insert(0, "Signature certificate information:\n");
                    }
                    StringBuilder checkCertInfo = new StringBuilder();
                    KeystoreCertificate certificate = new KeystoreCertificate();
                    certificate.setCertificate(x509Cert);
                    checkCertInfo.append("Verification certificate information:\n");
                    checkCertInfo.append("Serial number (DEC): ");
                    checkCertInfo.append(certificate.getSerialNumberDEC());
                    checkCertInfo.append("\n");
                    checkCertInfo.append("Serial number (HEX): ");
                    checkCertInfo.append(certificate.getSerialNumberHEX());
                    checkCertInfo.append("\n");
                    checkCertInfo.append("Finger print (SHA-1): ");
                    checkCertInfo.append(certificate.getFingerPrintSHA1());
                    checkCertInfo.append("\n");
                    checkCertInfo.append("Valid from: ");                    
                    checkCertInfo.append(DateFormat.getDateInstance(DateFormat.SHORT).format(certificate.getNotBefore()));
                    checkCertInfo.append("\n");
                    checkCertInfo.append("Valid to: ");                    
                    checkCertInfo.append(DateFormat.getDateInstance(DateFormat.SHORT).format(certificate.getNotAfter()));
                    checkCertInfo.append("\n");
                    checkCertInfo.append("Issuer: ");
                    checkCertInfo.append(x509Cert.getIssuerX500Principal().toString());
                    StringBuilder message = new StringBuilder("Verification failed");
                    message.append("\n\n");
                    message.append(signatureCertInfo);
                    message.append("\n\n");
                    message.append(checkCertInfo);
                    throw new SignatureException(message.toString());
                }
            }
            return signed.getContent();
        } else {
            throw new GeneralSecurityException("Content-Type indicates data isn't signed");
        }
    }

    private X509Certificate castCertificate(Certificate cert) throws GeneralSecurityException {
        if (cert == null) {
            throw new GeneralSecurityException("castCertificate: Certificate is absent");
        }
        if (!(cert instanceof X509Certificate)) {
            throw new GeneralSecurityException("castCertificate: Certificate must be an instance of X509Certificate");
        } else {
            return (X509Certificate) cert;
        }
    }

    private PrivateKey getPrivateKey(Key key) throws GeneralSecurityException {
        if (key == null) {
            throw new GeneralSecurityException("getPrivateKey: Key is absent");
        }
        if (!(key instanceof PrivateKey)) {
            throw new GeneralSecurityException("getPrivateKey: Key must implement PrivateKey interface");
        } else {
            return (PrivateKey) key;
        }
    }

    /**
     * Converts the passed algorithm or OID
     */
    public String convertAlgorithmNameToOID(String algorithm) throws NoSuchAlgorithmException {
        if (algorithm == null) {
            throw new NoSuchAlgorithmException("convertAlgorithmNameToOID: Unable to proceed - Algorithm is absent");
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_MD5)) {
            return (CMSSignedGenerator.DIGEST_MD5);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_SHA1)) {
            return (CMSSignedGenerator.DIGEST_SHA1);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_SHA224) || algorithm.equalsIgnoreCase("sha224")) {
            return (CMSSignedGenerator.DIGEST_SHA224);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_SHA256) || algorithm.equalsIgnoreCase("sha256")) {
            return (CMSSignedGenerator.DIGEST_SHA256);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_SHA384) || algorithm.equalsIgnoreCase("sha384")) {
            return (CMSSignedGenerator.DIGEST_SHA384);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_SHA512) || algorithm.equalsIgnoreCase("sha512")) {
            return (CMSSignedGenerator.DIGEST_SHA512);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_3DES)) {
            return ("1.2.840.113549.3.7");
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_DES)) {
            return ("1.3.14.3.2.7");
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_CAST5)) {
            return (CMSEnvelopedDataGenerator.CAST5_CBC);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_IDEA)) {
            return (CMSEnvelopedDataGenerator.IDEA_CBC);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_RC2)) {
            return (CMSEnvelopedDataGenerator.RC2_CBC);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_RC4)) {
            return ("1.2.840.113549.3.4");
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_AES_128)) {
            return (CMSEnvelopedDataGenerator.AES128_CBC);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_AES_192)) {
            return (CMSEnvelopedDataGenerator.AES192_CBC);
        } else if (algorithm.equalsIgnoreCase(ALGORITHM_AES_256)) {
            return (CMSEnvelopedDataGenerator.AES256_CBC);
        } else {
            throw new NoSuchAlgorithmException("Unsupported algorithm: " + algorithm);
        }
    }

    /**
     * Converts the passed algorithm or OID
     */
    public String convertOIDToAlgorithmName(String oid) throws NoSuchAlgorithmException {
        if (oid == null) {
            throw new NoSuchAlgorithmException("convertOIDToAlgorithmName: OID is absent");
        } else if (oid.equalsIgnoreCase(CMSSignedGenerator.DIGEST_MD5)) {
            return (ALGORITHM_MD5);
        } else if (oid.equalsIgnoreCase(CMSSignedGenerator.DIGEST_SHA1)) {
            return (ALGORITHM_SHA1);
        } else if (oid.equalsIgnoreCase(CMSSignedGenerator.DIGEST_SHA224)) {
            return (ALGORITHM_SHA224);
        } else if (oid.equalsIgnoreCase(CMSSignedGenerator.DIGEST_SHA256)) {
            return (ALGORITHM_SHA256);
        } else if (oid.equalsIgnoreCase(CMSSignedGenerator.DIGEST_SHA384)) {
            return (ALGORITHM_SHA384);
        } else if (oid.equalsIgnoreCase(CMSSignedGenerator.DIGEST_SHA512)) {
            return (ALGORITHM_SHA512);
        } else if (oid.equalsIgnoreCase(CMSEnvelopedDataGenerator.CAST5_CBC)) {
            return (ALGORITHM_CAST5);
        } else if (oid.equalsIgnoreCase(CMSEnvelopedDataGenerator.DES_EDE3_CBC)) {
            return (ALGORITHM_3DES);
        } else if (oid.equalsIgnoreCase("1.3.14.3.2.7")) {
            return (ALGORITHM_DES);
        } else if (oid.equalsIgnoreCase(CMSEnvelopedDataGenerator.IDEA_CBC)) {
            return (ALGORITHM_IDEA);
        } else if (oid.equalsIgnoreCase(CMSEnvelopedDataGenerator.RC2_CBC)) {
            return (ALGORITHM_RC2);
        } else if (oid.equalsIgnoreCase("1.2.840.113549.3.4")) {
            return (ALGORITHM_RC4);
        } else if (oid.equalsIgnoreCase(CMSEnvelopedDataGenerator.AES128_CBC)) {
            return (ALGORITHM_AES_128);
        } else if (oid.equalsIgnoreCase(CMSEnvelopedDataGenerator.AES192_CBC)) {
            return (ALGORITHM_AES_192);
        } else if (oid.equalsIgnoreCase(CMSEnvelopedDataGenerator.AES256_CBC)) {
            return (ALGORITHM_AES_256);
        } else {
            throw new NoSuchAlgorithmException("Unsupported algorithm: OID " + oid);
        }
    }

    /**
     *
     * @param type Keystore type which should be one of the class constants
     * @return
     * @throws java.security.KeyStoreException
     * @throws java.security.NoSuchProviderException
     */
    public KeyStore createKeyStoreInstance(String type) throws KeyStoreException, NoSuchProviderException {
        if (type.equals(KEYSTORE_PKCS12)) {
            return KeyStore.getInstance(type, "BC");
        } else {
            return KeyStore.getInstance(type);
        }
    }

    /**
     * returns a CMS encrypted byte array
     */
    public byte[] encryptCMS(byte[] data, final String ALGORITHM_NAME, Certificate cert) throws Exception {
        ByteArrayInputStream dataMem = new ByteArrayInputStream(data);
        ByteArrayOutputStream encryptedMem = new ByteArrayOutputStream();
        this.encryptCMS(dataMem, encryptedMem, ALGORITHM_NAME, cert, true);
        dataMem.close();
        encryptedMem.close();
        return (encryptedMem.toByteArray());
    }

    /**
     * Encrypts data to a stream
     */
    public void encryptCMS(InputStream rawStream, OutputStream encryptedStream,
            final String ALGORITHM_NAME, Certificate cert, boolean inMemory) throws Exception {
        X509Certificate x509Cert = this.castCertificate(cert);
        CMSEnvelopedDataStreamGenerator dataStreamGenerator = new CMSEnvelopedDataStreamGenerator();
        dataStreamGenerator.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(x509Cert).setProvider("BC"));
        String oid = this.convertAlgorithmNameToOID(ALGORITHM_NAME);
        if (inMemory) {
            ByteArrayOutputStream memBuffer = new ByteArrayOutputStream();
            OutputStream cmsEnveloped = null;
            try {
                ASN1ObjectIdentifier objectIdentifier = new ASN1ObjectIdentifier(oid);
                OutputEncryptor outputEncryptor = new JceCMSContentEncryptorBuilder(objectIdentifier).build();
                cmsEnveloped = dataStreamGenerator.open(memBuffer, outputEncryptor);
                this.copyStreams(rawStream, cmsEnveloped);
            } finally {
                if (cmsEnveloped != null) {
                    cmsEnveloped.flush();
                    cmsEnveloped.close();
                }
            }
            encryptedStream.write(memBuffer.toByteArray());
        } else {
            File tempFile = File.createTempFile("encrypt", ".temp");
            FileOutputStream fileBuffer = null;
            OutputStream cmsEnveloped = null;
            try {
                fileBuffer = new FileOutputStream(tempFile);
                ASN1ObjectIdentifier objectIdentifier = new ASN1ObjectIdentifier(oid);
                OutputEncryptor outputEncryptor = new JceCMSContentEncryptorBuilder(objectIdentifier).build();
                cmsEnveloped = dataStreamGenerator.open(fileBuffer, outputEncryptor);
                this.copyStreams(rawStream, cmsEnveloped);
            } finally {
                if (cmsEnveloped != null) {
                    cmsEnveloped.flush();
                    cmsEnveloped.close();
                }
                if (fileBuffer != null) {
                    fileBuffer.flush();
                    fileBuffer.close();
                }
            }
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream(tempFile);
                this.copyStreams(fileIn, encryptedStream);
            } finally {
                if (fileIn != null) {
                    fileIn.close();
                }
            }
            boolean deleted = tempFile.delete();
        }
    }

    /**
     * Decrypts a formerly encrypted byte array
     */
    public byte[] decryptCMS(byte[] encrypted, Certificate cert, Key key) throws Exception {
        ByteArrayInputStream encryptedMem = new ByteArrayInputStream(encrypted);
        ByteArrayOutputStream decryptedMem = new ByteArrayOutputStream();
        this.decryptCMS(encryptedMem, decryptedMem, cert, key);
        encryptedMem.close();
        decryptedMem.close();
        return (decryptedMem.toByteArray());
    }

    /**
     * Decrypts a formerly encrypted stream. An exception will be thrown if
     * decryption is not possible
     */
    public void decryptCMS(InputStream encrypted, OutputStream decrypted, Certificate cert, Key key) throws Exception {
        BufferedInputStream bufferedEncrypted = new BufferedInputStream(encrypted);
        BufferedOutputStream bufferedDecrypted = new BufferedOutputStream(decrypted);
        X509Certificate x509Cert = this.castCertificate(cert);
        CMSEnvelopedDataParser parser = new CMSEnvelopedDataParser(bufferedEncrypted);
        RecipientId recipientId = new JceKeyTransRecipientId(x509Cert);
        RecipientInformation recipient = parser.getRecipientInfos().get(recipientId);
        if (recipient != null) {
            CMSTypedStream cmsEncrypted = recipient.getContentStream(
                    new JceKeyTransEnvelopedRecipient(this.getPrivateKey(key)).setProvider("BC"));
            InputStream encryptedContent = cmsEncrypted.getContentStream();
            this.copyStreams(encryptedContent, bufferedDecrypted);
            bufferedDecrypted.flush();
        } else {
            throw new GeneralSecurityException("Wrong key used to decrypt the data.");
        }
    }

    /**
     * Uncompresses a data stream
     */
    public void uncompressCMS(InputStream compressed, OutputStream uncompressed) throws Exception {
        CMSCompressedDataParser compressedParser = new CMSCompressedDataParser(new BufferedInputStream(compressed));
        this.copyStreams(compressedParser.getContent(new ZlibExpanderProvider()).getContentStream(), uncompressed);
        uncompressed.flush();
    }

    /**
     * Compress a data stream
     */
    public void compressCMS(InputStream uncompressed, OutputStream compressed, boolean inMemory) throws Exception {
        //streamed compression does not work without a stream buffer in bc 1.45 and before
        CMSCompressedDataStreamGenerator generator = new CMSCompressedDataStreamGenerator();
        if (inMemory) {
            ByteArrayOutputStream memBuffer = new ByteArrayOutputStream();
            OutputStream cOut = generator.open(memBuffer, new ZlibCompressor());
            this.copyStreams(uncompressed, cOut);
            cOut.flush();
            cOut.close();
            compressed.write(memBuffer.toByteArray());
        } else {
            File tempFile = File.createTempFile("compress", ".temp");
            FileOutputStream fileBuffer = null;
            try {
                fileBuffer = new FileOutputStream(tempFile);
                OutputStream cOut = generator.open(fileBuffer, new ZlibCompressor());
                this.copyStreams(uncompressed, cOut);
                cOut.flush();
                cOut.close();
            } finally {
                if (fileBuffer != null) {
                    fileBuffer.flush();
                    fileBuffer.close();
                }
            }
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream(tempFile);
                this.copyStreams(fileIn, compressed);
            } finally {
                if (fileIn != null) {
                    fileIn.close();
                }
            }
            boolean deleted = tempFile.delete();
        }
    }

    public void signCMS(InputStream unsigned, OutputStream signed, final String ALGORITHM_NAME, Certificate signCert, Key signKey,
            boolean inMemory) throws Exception {
        CMSSignedDataStreamGenerator generator = new CMSSignedDataStreamGenerator();
        PrivateKey signPrivKey = this.getPrivateKey(signKey);
        ContentSigner contentSigner = new JcaContentSignerBuilder(ALGORITHM_NAME).setProvider("BC").build(signPrivKey);
        generator.addSignerInfoGenerator(
                new JcaSignerInfoGeneratorBuilder(
                        new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
                .build(contentSigner, new X509CertificateHolder(signCert.getEncoded())));
        if (inMemory) {
            ByteArrayOutputStream memBuffer = new ByteArrayOutputStream();
            OutputStream signedOut = generator.open(memBuffer, true);
            this.copyStreams(unsigned, signedOut);
            signedOut.flush();
            signedOut.close();
            signed.write(memBuffer.toByteArray());
        } else {
            File tempFile = File.createTempFile("sign", ".temp");
            FileOutputStream fileBuffer = null;
            OutputStream signedOut = null;
            try {
                fileBuffer = new FileOutputStream(tempFile);
                signedOut = generator.open(fileBuffer, true);
                this.copyStreams(unsigned, signedOut);
            } finally {
                if (signedOut != null) {
                    signedOut.flush();
                    signedOut.close();
                }
                if (fileBuffer != null) {
                    fileBuffer.flush();
                    fileBuffer.close();
                }
            }
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream(tempFile);
                this.copyStreams(fileIn, signed);
            } finally {
                if (fileIn != null) {
                    fileIn.close();
                }
            }
            boolean deleted = tempFile.delete();
        }
    }

    public boolean verifySignatureCMS(InputStream signed, Certificate cert) throws Exception {
        CMSSignedDataParser parser = new CMSSignedDataParser(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build(), signed);
        parser.getSignedContent().drain();
        SignerInformationStore signers = parser.getSignerInfos();
        Collection signerCollection = signers.getSigners();
        Iterator it = signerCollection.iterator();
        boolean verified = false;
        X509CertificateHolder certHolder = new X509CertificateHolder(cert.getEncoded());
        SignerInformationVerifier verifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(certHolder);
        while (it.hasNext()) {
            SignerInformation signerInformation = (SignerInformation) it.next();
            if (!verified) {
                verified = signerInformation.verify(verifier);                
            }
            if (verified) {
                break;
            }
        }
        return (verified);
    }

    public void removeSignatureCMS(InputStream signed, OutputStream unsigned, Certificate cert) throws Exception {
        CMSSignedDataParser parser = new CMSSignedDataParser(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build(), signed);
        InputStream signedContent = parser.getSignedContent().getContentStream();
        this.copyStreams(signedContent, unsigned);
        unsigned.flush();
    }

    /**
     * Generates a hash of a passed input stream
     */
    public byte[] generateFileHash(MessageDigest digest, InputStream in) throws IOException {
        BufferedInputStream inStream = new BufferedInputStream(in);
        byte[] buffer = new byte[4096];
        int sizeRead = -1;
        while ((sizeRead = inStream.read(buffer)) != -1) {
            digest.update(buffer, 0, sizeRead);
        }
        inStream.close();
        byte[] hash = null;
        hash = digest.digest();
        return hash;
    }

//    public static final void main( String[] args ){
//        Security.addProvider(new BouncyCastleProvider());
//        System.out.println( CMSSignedGenerator.DIGEST_MD5);
//    }
}
