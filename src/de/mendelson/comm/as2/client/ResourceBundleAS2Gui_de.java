//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/ResourceBundleAS2Gui_de.java,v 1.1 2015/01/06 11:07:37 heller Exp $
package de.mendelson.comm.as2.client;

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
public class ResourceBundleAS2Gui_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"menu.file", "Datei"},
        {"menu.file.exit", "Beenden"},
        {"menu.file.partner", "Partner"},
        {"menu.file.datasheet", "Datenblatt f�r Anbindung"},        
        {"menu.file.certificates", "Zertifikate"},
        {"menu.file.certificate", "Zertifikate"},
        {"menu.file.certificate.signcrypt", "Zertifikate (Signatur, Verschl�sselung)"},
        {"menu.file.certificate.ssl", "Zertifikate (SSL)"},
        {"menu.file.cem", "Verwaltung Zertifikataustausch (CEM)"},
        {"menu.file.cemsend", "Zertifikate mit Partnern tauschen (CEM)"},
        {"menu.file.preferences", "Einstellungen"},
        {"menu.file.send", "Datei an Partner versenden"},
        {"menu.file.resend", "Als neue Transaktion versenden"},
        {"menu.file.resend.multiple", "Als neue Transaktionen versenden"},
        {"menu.file.statistic", "Statistik"},
        {"menu.file.quota", "Kontingente"},
        {"menu.file.export", "Konfiguration exportieren"},
        {"menu.file.import", "Konfiguration importieren"},
        {"menu.help", "Hilfe"},
        {"menu.help.about", "�ber"},
        {"menu.help.shop", "mendelson Online Shop"},
        {"menu.help.helpsystem", "Hilfesystem"},
        {"menu.help.forum", "Forum"},
        {"details", "Nachrichtendetails"},
        {"filter.showfinished", "Fertige anzeigen"},
        {"filter.showpending", "Wartende anzeigen"},
        {"filter.showstopped", "Gestoppte anzeigen"},
        {"filter.none", "-- Keine --"},
        {"filter.partner", "Partnerbeschr�nkung:"},
        {"filter.localstation", "Beschr�nkung der lokalen Station:"},
        {"filter.direction", "Richtungsbeschr�nkung:"},
        {"filter.direction.inbound", "Eingehend"},
        {"filter.direction.outbound", "Ausgehend"},
        {"filter", "Filter"},
        {"keyrefresh", "Zertifikatliste aktualisieren"},
        {"delete.msg", "Selektierte Nachrichten l�schen"},
        {"dialog.msg.delete.message", "Wollen Sie die selektierten Nachrichten wirklich permanent l�schen?"},
        {"dialog.msg.delete.title", "L�schen von Nachrichten"},
        {"stoprefresh.msg", "Aktualisierung an/aus"},
        {"welcome", "Willkommen, {0}"},
        {"warning.eval", "Dies ist eine Evaluierungsversion."},
        {"warning.refreshstopped", "Die Aktualisierung der Oberfl�che ist abgeschaltet."},
        {"tab.welcome", "News und Updates"},
        {"tab.transactions", "Transaktionen"},
        {"new.version", "Eine neue Version ist verf�gbar. Hier klicken, um sie herunterzuladen."},
        {"filechooser.export", "Bitte w�hlen Sie eine Exportdatei"},
        {"filechooser.import", "Bitte w�hlen Sie eine Importdatei"},
        {"export.success", "Die Konfiguration wurde erfolgreich nach \"{0}\" exportiert."},
        {"dbconnection.failed.message", "Es konnte keine Verbindung zum AS2 Datenbankserver hergestellt werden: {0}"},
        {"dbconnection.failed.title", "Keine Verbindung m�glich"},
        {"login.failed.client.incompatible.message", "Der Server meldet, dass dieser Client nicht die richtige Version hat.\nBitte verwenden Sie den zum Server passenden Client."},
        {"login.failed.client.incompatible.title", "Login wurde zur�ckgewiesen"},
        {"uploading.to.server", "�bertrage zum Server"},
        {"refresh.overview", "Aktualisiere Transaktionsliste"},
        {"resend.performed", "Diese Transaktion wurde manuell als neue Transaktion erneut verschickt ([{0}])"},
        {"dialog.resend.message", "Wollen Sie die selektierte Transaktion wirklich erneut senden?"},
        {"dialog.resend.message.multiple", "Wollen Sie die {0} selektierten Transaktionen wirklich erneut senden?"},
        {"dialog.resend.title", "Daten erneut senden"},
    };
}