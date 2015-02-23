//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/ResourceBundleAS2MessageParser_de.java,v 1.1 2015/01/06 11:07:40 heller Exp $
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
public class ResourceBundleAS2MessageParser_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"mdn.incoming", "{0}: Eingehende �bertragung ist eine Empfangsbest�tigung (MDN)."},
        {"mdn.answerto", "{0}: Empfangsbest�tigung (MDN) ist die Antwort auf die AS2 Nachricht \"{1}\"."},
        {"mdn.state", "{0}: Status der Empfangsbest�tigung (MDN) ist [{1}]."},
        {"mdn.details", "{0}: Details der Empfangsbest�tigung (MDN) des entfernten AS2 Servers: {1}"},
        {"msg.incoming", "{0}: Eingehende �bertragung ist eine AS2 Nachricht, Rohdatengr�sse: {1}"},
        {"mdn.signed", "{0}: Empfangsbest�tigung (MDN) ist digital signiert."},
        {"mdn.unsigned.error", "{0}: Empfangsbest�tigung (MDN) ist entgegen der Konfiguration des Partners \"{1}\" NICHT digital signiert."},
        {"mdn.signed.error", "{0}: Empfangsbest�tigung (MDN) ist entgegen der Konfiguration des Partners \"{1}\" digital signiert."},
        {"msg.signed", "{0}: AS2 Nachricht ist digital signiert."},
        {"msg.encrypted", "{0}: AS2 Nachricht ist verschl�sselt."},
        {"msg.notencrypted", "{0}: AS2 Nachricht ist nicht verschl�sselt."},
        {"msg.notsigned", "{0}: AS2 Nachricht ist nicht signiert."},
        {"mdn.notsigned", "{0}: Empfangsbest�tigung (MDN) ist nicht signiert."},
        {"signature.ok", "{0}: Digitale Signatur wurde erfolgreich �berpr�ft."},
        {"signature.failure", "{0}: �berpr�fung der digitalen Signatur schlug fehl - {1}"},
        {"signature.using.alias", "{0}: Benutze das Zertifikat \"{1}\" zum �berpr�fen der digitalen Signatur."},
        {"decryption.done.alias", "{0}: Die Daten wurden mit Hilfe des Schl�ssels \"{1}\" entschl�sselt."},
        {"mdn.unexpected.messageid", "{0}: Die Empfangsbest�tigung (MDN) referenziert eine AS2 Nachricht der Referenznummer \"{1}\", die nicht existert."},
        {"mdn.unexpected.messageid", "{0}: Die Empfangsbest�tigung (MDN) referenziert die AS2 Nachricht der Referenznummer \"{1}\", die keine MDN erwartet."},
        {"data.compressed.expanded", "{0}: Die komprimierten Nutzdaten wurden von {1} auf {2} expandiert."},
        {"found.attachments", "{0}: Es wurden {1} Anh�nge mit Nutzdaten in der AS2 Nachricht gefunden."},
        {"decryption.inforequired", "{0}: Zum Entschl�sseln der Daten ist ein Schl�ssel mit folgenden Parametern notwendig:\n{1}"},
        {"decryption.infoassigned", "{0}: Zum Entschl�sseln wurde ein Schl�ssel mit folgenden Parametern benutzt (Alias \"{1}\"):\n{2}"},
        {"signature.analyzed.digest", "{0}: F�r die digitale Signatur wurde vom Sender der Algorithmus {1} verwendet."},
        {"filename.extraction.error", "{0}: Extrahieren des Originaldateinamen ist nicht m�glich: \"{1}\", wird ignoriert."},
        {"contentmic.match", "{0}: Der Message Integrity Code (MIC) stimmt mit der gesandten AS2 Nachricht �berein."},
        {"contentmic.failure", "{0}: Der Message Integrity Code (MIC) stimmt nicht mit der gesandten AS2 Nachricht �berein (erwartet: {1}, erhalten: {2})."},
        {"found.cem", "{0}: Die Nachricht ist eine Anfrage f�r einen Zertifikataustausch (CEM)."},
        {"data.unable.to.process.content.transfer.encoding", "Es sind Daten empfangen worden, die nicht verarbeitet werden konnten, weil sie Fehler enthalten: Das Content Transfer Encoding \"{0}\" ist unbekannt."},
    };
}
