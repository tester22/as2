//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/server/ResourceBundleAS2ServerProcessing_de.java,v 1.1 2015/01/06 11:07:49 heller Exp $
package de.mendelson.comm.as2.server;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleAS2ServerProcessing_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        {"send.failed", "Versand fehlgeschlagen"},
        {"unable.to.process", "Fehler beim Verarbeiten auf dem Server: {0}"},
        {"server.shutdown", "Der Benutzer {0} f�hrt den Server herunter."},
        {"sync.mdn.sent", "{0}: Synchrone MDN als Antwort auf {1} versandt."},
        {"invalid.request.from", "Eine ung�ltige Anfrage ist eingegangen. Sie wird nicht verarbeitet, weil kein as2-from Header vorhanden ist."},
        {"invalid.request.to", "Eine ung�ltige Anfrage ist eingegangen. Sie wird nicht verarbeitet, weil kein as2-to Header vorhanden ist."},
        {"invalid.request.messageid", "Eine ung�ltige Anfrage ist eingegangen. Sie wird nicht verarbeitet, weil kein message-id Header vorhanden ist."},        
    };
}