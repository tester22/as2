//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/partner/ResourceBundleCertificateInformation_de.java,v 1.1 2015/01/06 11:07:43 heller Exp $
package de.mendelson.comm.as2.partner;

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
public class ResourceBundleCertificateInformation_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"localstation.decrypt.prio1", "Eingehende Nachrichten f�r die lokale Station \"{0}\" werden mit Hilfe des Zertifikats \"{1}\" entschl�sselt."},
        {"localstation.decrypt.prio2", "Ausserdem kann \"{0}\" eingehende Nachrichten auch mit dem Zertifikat \"{1}\" entschl�sseln."},
        {"localstation.sign.prio1", "Ausgehende Nachrichten der lokalen Station \"{0}\" werden �ber das Zertifikat \"{1}\" digital signiert."},
        {"partner.encrypt.prio1", "Ausgehende Nachrichten an den Partner \"{0}\" werden mit Hilfe des Zertifikats \"{1}\" verschl�sselt."},
        {"partner.sign.prio1", "Die digitale Signatur eingehender Nachrichten des Partners \"{0}\" werden mit Hilfe des Zertifikats \"{1}\" �berpr�ft."},
        {"partner.sign.prio2", "Das System ist ausserdem dazu konfiguriert, die digitalen Signaturen eingehender Nachrichten des Partners \"{0}\" �ber das Zertifikat \"{1}\" zu pr�fen."},};
}
