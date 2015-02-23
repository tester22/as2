//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/csr/ResourceBundleCSRUtil_de.java,v 1.1 2015/01/06 11:08:02 heller Exp $
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
public class ResourceBundleCSRUtil_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {                
        {"verification.failed", "Operation fehlgeschlagen - Die �berpr�fung des erstellten Certificate Sign Requests (CSR) ist fehlgeschlagen." },
        {"no.certificates.in.reply", "Operation fehlgeschlagen - Der Schl�ssel konnte nicht gepatcht werden, es wurden in der CA Antwort keine Zertifikate gefunden." },
        {"missing.cert.in.trustchain", "Operation fehlgeschlagen - Es fehlen f�r diese Operation Zertifikate im System.\nBitte importieren Sie zun�chst das Zertifikat mit den Eckdaten (issuer)\n{0}." },
        {"response.chain.incomplete", "Operation fehlgeschlagen - Der Trust Chain der CSR Antwort ist unvollst�ndig." },
        {"response.verification.failed", "Operation fehlgeschlagen - Der Trust Chain der CSR Antwort konnte nicht verifiziert werden: {0}" },
        {"response.public.key.does.not.match", "Operation fehlgeschlagen - Diese Antwort der CA passt nicht zu diesem Schl�ssel." },
    };


    

}