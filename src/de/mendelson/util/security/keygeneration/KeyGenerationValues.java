//$Header: /cvsroot/mec-as2/b47/de/mendelson/util/security/keygeneration/KeyGenerationValues.java,v 1.1 2015/01/06 11:08:02 heller Exp $
package de.mendelson.util.security.keygeneration;

import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyUsage;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Stores key values for the generation process
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class KeyGenerationValues {

    public static final String KEYTYPE_DSA = KeyGenerator.KEYTYPE_DSA;
    public static final String KEYTYPE_RSA = KeyGenerator.KEYTYPE_RSA;
    public static final String SIGNATUREALGORITHM_MD5_WITH_RSA = KeyGenerator.SIGNATUREALGORITHM_MD5_WITH_RSA;
    public final static String SIGNATUREALGORITHM_SHA1_WITH_RSA = KeyGenerator.SIGNATUREALGORITHM_SHA1_WITH_RSA;

    private String keyType = KEYTYPE_RSA;
    private int keySize = 2048;
    private String commonName = "subdomain.yourdomain.com";
    private String organisationUnit = "Your company department";
    private String organisationName = "Your company";
    private String localityName = "Locality name";
    private String stateName = "State name";
    private String countryCode = "de";
    private String emailAddress = "webmaster@mailaddressondomain.to";
    private int keyValidInDays = 365;
    private String signatureAlgorithm = SIGNATUREALGORITHM_SHA1_WITH_RSA;
    private KeyUsage keyExtension = null;
    private ExtendedKeyUsage extendedKeyExtension = null;

    /**
     * @return the keyType
     */
    public String getKeyType() {
        return keyType;
    }

    /**
     * @param keyType the keyType to set
     */
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     * @return the keySize
     */
    public int getKeySize() {
        return keySize;
    }

    /**
     * @param keySize the keySize to set
     */
    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * @return the commonName
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * @param commonName the commonName to set
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * @return the organisationUnit
     */
    public String getOrganisationUnit() {
        return organisationUnit;
    }

    /**
     * @param organisationUnit the organisationUnit to set
     */
    public void setOrganisationUnit(String organisationUnit) {
        this.organisationUnit = organisationUnit;
    }

    /**
     * @return the organisationName
     */
    public String getOrganisationName() {
        return organisationName;
    }

    /**
     * @param organisationName the organisationName to set
     */
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    /**
     * @return the localityName
     */
    public String getLocalityName() {
        return localityName;
    }

    /**
     * @param localityName the localityName to set
     */
    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the keyValidInDays
     */
    public int getKeyValidInDays() {
        return keyValidInDays;
    }

    /**
     * @param keyValidInDays the keyValidInDays to set
     */
    public void setKeyValidInDays(int keyValidInDays) {
        this.keyValidInDays = keyValidInDays;
    }

    /**
     * @return the signatureAlgorithm
     */
    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    /**
     * @param signatureAlgorithm the signatureAlgorithm to set
     */
    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    /**
     * @return the keyExtension
     */
    public KeyUsage getKeyExtension() {
        return keyExtension;
    }

    /**
     * @param keyExtension the keyExtension to set
     */
    public void setKeyExtension(KeyUsage keyExtension) {
        this.keyExtension = keyExtension;
    }

    /**
     * @return the extendedKeyExtension
     */
    public ExtendedKeyUsage getExtendedKeyExtension() {
        return extendedKeyExtension;
    }

    /**
     * @param extendedKeyExtension the extendedKeyExtension to set
     */
    public void setExtendedKeyExtension(ExtendedKeyUsage extendedKeyExtension) {
        this.extendedKeyExtension = extendedKeyExtension;
    }
}
