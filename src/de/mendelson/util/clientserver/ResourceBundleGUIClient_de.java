//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/ResourceBundleGUIClient_de.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products - if you want to localize
 * eagle to your language, please contact us: localize@mendelson.de
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleGUIClient_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        //dialog
        {"password.required", "Fehler beim Login, es wird ein Passwort f�r den Benutzer {0} ben�tigt."},
        {"connectionrefused.message", "{0}: Keine Verbindung m�glich. Bitte stellen Sie sicher, dass der Server l�uft."},
        {"connectionrefused.title", "Verbindungsproblem"},
        {"login.success", "Angemeldet als Benutzer \"{0}\""},
        {"login.failure", "Login als Benutzer \"{0}\" fehlgeschlagen"},
        {"connection.success", "Client verbunden mit {0}"},
        {"logout.from.server", "Es wurde ein Logout vom Server durchgef�hrt"},
        {"connection.closed", "Die lokale Client-Server Verbindung wurde vom Server getrennt"},
        {"connection.closed.title", "Lokaler Verbindungsabbruch"},
        {"connection.closed.message", "Die lokale Client-Server Verbindung wurde vom Server getrennt"},
        {"client.received.unprocessed.message", "Der Server hat eine Nachricht geschickt, die vom Client nicht verarbeitet wurde: {0}"},
        {"error", "Problem: {0}"},
        {"login.failed.client.incompatible.message", "Der Server meldet, dass dieser Client nicht die richtige Version hat.\nBitte verwenden Sie den zum Server passenden Client."},
        {"login.failed.client.incompatible.title", "Login wurde zur�ckgewiesen"},
    };
}