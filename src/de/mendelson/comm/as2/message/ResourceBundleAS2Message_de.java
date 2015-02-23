//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/ResourceBundleAS2Message_de.java,v 1.1 2015/01/06 11:07:40 heller Exp $
package de.mendelson.comm.as2.message;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleAS2Message_de extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"signature." + AS2Message.SIGNATURE_UNKNOWN, "Unbekannt" },
        {"signature." + AS2Message.SIGNATURE_NONE, "Keine Signatur" },
        {"signature." + AS2Message.SIGNATURE_MD5, "MD5" },
        {"signature." + AS2Message.SIGNATURE_SHA1, "SHA-1" },
        {"signature." + AS2Message.SIGNATURE_SHA224, "SHA-224" },
        {"signature." + AS2Message.SIGNATURE_SHA256, "SHA-256" },
        {"signature." + AS2Message.SIGNATURE_SHA384, "SHA-384" },
        {"signature." + AS2Message.SIGNATURE_SHA512, "SHA-512" },
        {"encryption." + AS2Message.ENCRYPTION_UNKNOWN, "Unbekannt" },        
        {"encryption." + AS2Message.ENCRYPTION_NONE, "Keine Verschl�sselung" },        
        {"encryption." + AS2Message.ENCRYPTION_3DES, "3DES" },        
        {"encryption." + AS2Message.ENCRYPTION_RC2_40, "RC2-40" },        
        {"encryption." + AS2Message.ENCRYPTION_RC2_64, "RC2-64" },
        {"encryption." + AS2Message.ENCRYPTION_RC2_128, "RC2-128" },
        {"encryption." + AS2Message.ENCRYPTION_RC2_196, "RC2-196" },
        {"encryption." + AS2Message.ENCRYPTION_RC2_UNKNOWN, "RC2" },
        {"encryption." + AS2Message.ENCRYPTION_AES_128, "AES-128" },
        {"encryption." + AS2Message.ENCRYPTION_AES_192, "AES-192" },
        {"encryption." + AS2Message.ENCRYPTION_AES_256, "AES-256" },
        {"encryption." + AS2Message.ENCRYPTION_RC4_40, "RC4-40" },
        {"encryption." + AS2Message.ENCRYPTION_RC4_56, "RC4-56" },
        {"encryption." + AS2Message.ENCRYPTION_RC4_128, "RC4-128" },
        {"encryption." + AS2Message.ENCRYPTION_RC4_UNKNOWN, "RC4" },
        {"encryption." + AS2Message.ENCRYPTION_DES, "DES" },
        {"encryption." + AS2Message.ENCRYPTION_UNKNOWN_ALGORITHM, "Unbekannt" },
        {"compression." + AS2Message.COMPRESSION_NONE, "Keine"},
        {"compression." + AS2Message.COMPRESSION_UNKNOWN, "Unbekannt"},
        {"compression." + AS2Message.COMPRESSION_ZLIB, "ZLIB"},
        {"direction." + AS2MessageInfo.DIRECTION_IN, "Eingehend"},
        {"direction." + AS2MessageInfo.DIRECTION_OUT, "Ausgehend"},
        {"direction." + AS2MessageInfo.DIRECTION_UNKNOWN, "Unbekannt"},
    };
    
}