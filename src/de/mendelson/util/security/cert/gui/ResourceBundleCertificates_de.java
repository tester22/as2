//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/ResourceBundleCertificates_de.java,v 1.1 2015/01/06 11:07:59 heller Exp $
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
        {"button.delete", "Schl�ssel/Zertifikat l�schen"},
        {"button.edit", "Alias umbenennen"},
        {"button.newkey", "Schl�ssel importieren"},
        {"button.newcertificate", "Zertifikat importieren"},
        {"button.export", "Zertifikat exportieren"},
        {"menu.file", "Datei" },        
        {"menu.file.close", "Beenden" },
        {"menu.import", "Import"},
        {"menu.export", "Export"},
        {"menu.tools", "Erweitert"},
        {"menu.tools.generatekey", "Neuen Schl�ssel generieren (Self signed)"},
        {"menu.tools.generatecsr", "Zertifikat beglaubigen: CSR generieren (an CA)"},
        {"menu.tools.generatecsr.renew", "Zertifikat erneuern: CSR generieren (an CA)"},
        {"menu.tools.importcsr", "Zertifikat beglaubigen: Antwort der CA auf CSR importieren"},
        {"menu.tools.importcsr.renew", "Zertifikat erneuern: Antwort der CA auf CSR importieren"},
        {"label.selectcsrfile", "Bitte w�hlen Sie die Datei zum Speichern des CSR"},
        {"label.cert.import", "Zertifikat importieren (vom Partner)"},
        {"label.cert.export", "Zertifikat exportieren (f�r den Partner)"},
        {"label.key.import.pem", "Eigenen privaten Schl�ssel importieren (von PEM)"},
        {"label.key.import.pkcs12", "Eigenen privaten Schl�ssel importieren (von PKCS#12)"},
        {"label.key.import.jks", "Eigenen privaten Schl�ssel importieren (von JKS, JAVA Keystore Format)"},
        {"label.key.export.pkcs12", "Schl�ssel exportieren (PKCS#12) (nur f�r Backup Zwecke!)"},
        {"label.keystore", "Keystore Datei:"},
        {"title.signencrypt", "Verf�gbare Schl�ssel und Zertifikate (Verschl�sselung, Signaturen)"},
        {"title.ssl", "Verf�gbare Schl�ssel und Zertifikate (SSL)"},
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"filechooser.certificate.import", "Bitte w�hlen Sie die Zertifikatdatei f�r den Import"},
        {"certificate.import.success.message", "Das Zertifikat wurde erfolgreich mit dem Alias \"{0}\" importiert."},
        {"certificate.root.import.success.message", "Das Root Zertifikat wurde erfolgreich mit dem Alias \"{0}\" importiert."},
        {"certificate.import.success.title", "Erfolg"},
        {"certificate.import.error.message", "Es gab einen Fehler w�hrend des Imports:\n{0}"},
        {"certificate.import.error.title", "Fehler"},
        {"certificate.import.alias", "Alias f�r dieses Zertifikat:"},
        {"keystore.readonly.message", "Die zugrundeliegende Keystore Datei ist schreibgesch�tzt.\nDiese Operation ist nicht m�glich."},
        {"keystore.readonly.title", "Keystore schreibgesch�tzt"},
        {"modifications.notalllowed.message", "Modifikationen sind nicht m�glich"},
        {"generatekey.error.message", "{0}"},
        {"generatekey.error.title", "Fehler bei der Schl�sselerstellung"},
        {"tab.info.basic", "Details"},
        {"tab.info.extension", "Erweiterungen"},
        {"csr.title", "Zertificate beglaubigen: Certificate Sign Request" },
        {"csr.title.renew", "Zertificate erneuern: Certificate Sign Request" },
        {"csr.message.storequestion", "M�chten Sie den Schl�ssel bei der mendelson CA beglaubigen lassen\noder die Anfrage in einer Datei speichern?" },
        {"csr.message.storequestion.renew", "M�chten Sie den Schl�ssel bei der mendelson CA erneuern lassen\noder die Anfrage in einer Datei speichern?" },
        {"csr.option.1", "Beglaubigen bei der mendelson CA" },
        {"csr.option.1.renew", "Erneuern bei der mendelson CA" },
        {"csr.option.2", "In Datei speichern" },
        {"csr.generation.success.message", "Die generierte Anfrage zur Beglaubigung wurde in der Datei\n\"{0}\" gespeichert.\nBitte senden Sie diese Daten an Ihre CA.\nWir empfehlen Ihnen die mendelson CA (http://ca.mendelson-e-c.com)."},
        {"csr.generation.success.title", "CSR wurde erfolgreich erstellt"},
        {"csr.generation.failure.title", "Fehler bei der CSR Erstellung"},
        {"csr.generation.failure.message", "{0}"},
        {"label.selectcsrrepsonsefile", "Bitte w�hlen Sie die Antwortsdatei der CA"},
        {"csrresponse.import.success.message", "Der Schl�ssel wurde erfolgreich mit der Antwort der CA gepatched."},
        {"csrresponse.import.success.title", "Erfolg"},
        {"csrresponse.import.failure.message", "{0}"},
        {"csrresponse.import.failure.title", "Problem beim Patchen des Schl�ssels"},
        {"dialog.cert.delete.message", "Wollen Sie wirklich das Zertifikat mit dem Alias \"{0}\" l�schen?"},
        {"dialog.cert.delete.title", "Zertifikat l�schen"},
        {"title.cert.in.use", "Zertifikat wird verwendet" },
        {"cert.delete.impossible", "Der Eintrag kann nicht gel�scht werden:" },
        {"module.locked", "Diese Zertifikatverwaltung wird aktuell exklusiv von einem anderen Client ge�ffnet, Sie k�nnen keine �nderungen vornehmen!" },
    };
}
