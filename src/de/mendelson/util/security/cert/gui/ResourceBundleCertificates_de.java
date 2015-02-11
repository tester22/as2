//$Header: /cvsroot/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleCertificates_de.java,v 1.1 2015/01/06 11:07:59 heller Exp $
package de.mendelson.util.security.cert.gui;

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
public class ResourceBundleCertificates_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"display.root.certs", "Root Zertifikate anzeigen"},
        {"button.delete", "Schlüssel/Zertifikat löschen"},
        {"button.edit", "Alias umbenennen"},
        {"button.newkey", "Schlüssel importieren"},
        {"button.newcertificate", "Zertifikat importieren"},
        {"button.export", "Zertifikat exportieren"},
        {"menu.file", "Datei" },        
        {"menu.file.close", "Beenden" },
        {"menu.import", "Import"},
        {"menu.export", "Export"},
        {"menu.tools", "Erweitert"},
        {"menu.tools.generatekey", "Neuen Schlüssel generieren (Self signed)"},
        {"menu.tools.generatecsr", "Zertifikat beglaubigen: CSR generieren (an CA)"},
        {"menu.tools.generatecsr.renew", "Zertifikat erneuern: CSR generieren (an CA)"},
        {"menu.tools.importcsr", "Zertifikat beglaubigen: Antwort der CA auf CSR importieren"},
        {"menu.tools.importcsr.renew", "Zertifikat erneuern: Antwort der CA auf CSR importieren"},
        {"label.selectcsrfile", "Bitte wählen Sie die Datei zum Speichern des CSR"},
        {"label.cert.import", "Zertifikat importieren (vom Partner)"},
        {"label.cert.export", "Zertifikat exportieren (für den Partner)"},
        {"label.key.import.pem", "Eigenen privaten Schlüssel importieren (von PEM)"},
        {"label.key.import.pkcs12", "Eigenen privaten Schlüssel importieren (von PKCS#12)"},
        {"label.key.import.jks", "Eigenen privaten Schlüssel importieren (von JKS, JAVA Keystore Format)"},
        {"label.key.export.pkcs12", "Schlüssel exportieren (PKCS#12) (nur für Backup Zwecke!)"},
        {"label.keystore", "Keystore Datei:"},
        {"title.signencrypt", "Verfügbare Schlüssel und Zertifikate (Verschlüsselung, Signaturen)"},
        {"title.ssl", "Verfügbare Schlüssel und Zertifikate (SSL)"},
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"filechooser.certificate.import", "Bitte wählen Sie die Zertifikatdatei für den Import"},
        {"certificate.import.success.message", "Das Zertifikat wurde erfolgreich mit dem Alias \"{0}\" importiert."},
        {"certificate.root.import.success.message", "Das Root Zertifikat wurde erfolgreich mit dem Alias \"{0}\" importiert."},
        {"certificate.import.success.title", "Erfolg"},
        {"certificate.import.error.message", "Es gab einen Fehler während des Imports:\n{0}"},
        {"certificate.import.error.title", "Fehler"},
        {"certificate.import.alias", "Alias für dieses Zertifikat:"},
        {"keystore.readonly.message", "Die zugrundeliegende Keystore Datei ist schreibgeschützt.\nDiese Operation ist nicht möglich."},
        {"keystore.readonly.title", "Keystore schreibgeschützt"},
        {"modifications.notalllowed.message", "Modifikationen sind nicht möglich"},
        {"generatekey.error.message", "{0}"},
        {"generatekey.error.title", "Fehler bei der Schlüsselerstellung"},
        {"tab.info.basic", "Details"},
        {"tab.info.extension", "Erweiterungen"},
        {"csr.title", "Zertificate beglaubigen: Certificate Sign Request" },
        {"csr.title.renew", "Zertificate erneuern: Certificate Sign Request" },
        {"csr.message.storequestion", "Möchten Sie den Schlüssel bei der mendelson CA beglaubigen lassen\noder die Anfrage in einer Datei speichern?" },
        {"csr.message.storequestion.renew", "Möchten Sie den Schlüssel bei der mendelson CA erneuern lassen\noder die Anfrage in einer Datei speichern?" },
        {"csr.option.1", "Beglaubigen bei der mendelson CA" },
        {"csr.option.1.renew", "Erneuern bei der mendelson CA" },
        {"csr.option.2", "In Datei speichern" },
        {"csr.generation.success.message", "Die generierte Anfrage zur Beglaubigung wurde in der Datei\n\"{0}\" gespeichert.\nBitte senden Sie diese Daten an Ihre CA.\nWir empfehlen Ihnen die mendelson CA (http://ca.mendelson-e-c.com)."},
        {"csr.generation.success.title", "CSR wurde erfolgreich erstellt"},
        {"csr.generation.failure.title", "Fehler bei der CSR Erstellung"},
        {"csr.generation.failure.message", "{0}"},
        {"label.selectcsrrepsonsefile", "Bitte wählen Sie die Antwortsdatei der CA"},
        {"csrresponse.import.success.message", "Der Schlüssel wurde erfolgreich mit der Antwort der CA gepatched."},
        {"csrresponse.import.success.title", "Erfolg"},
        {"csrresponse.import.failure.message", "{0}"},
        {"csrresponse.import.failure.title", "Problem beim Patchen des Schlüssels"},
        {"dialog.cert.delete.message", "Wollen Sie wirklich das Zertifikat mit dem Alias \"{0}\" löschen?"},
        {"dialog.cert.delete.title", "Zertifikat löschen"},
        {"title.cert.in.use", "Zertifikat wird verwendet" },
        {"cert.delete.impossible", "Der Eintrag kann nicht gelöscht werden:" },
        {"module.locked", "Diese Zertifikatverwaltung wird aktuell exklusiv von einem anderen Client geöffnet, Sie können keine Änderungen vornehmen!" },
    };
}
