//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/cem/gui/ResourceBundleCEMOverview_de.java,v 1.1 2015/01/06 11:07:35 heller Exp $
package de.mendelson.comm.as2.cem.gui;
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
public class ResourceBundleCEMOverview_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }

    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"title", "Verwaltung Zertifikataustausch" },
        {"button.sendcem", "Neues Zertifikat austauschen" },
        {"button.requestdetails", "Details der Anfrage" },
        {"button.responsedetails", "Details der Antwort" },
        {"button.exit", "Schliessen" },
        {"button.cancel", "Abbrechen" },
        {"button.refresh", "Aktualisieren" },
        {"button.remove", "L�schen" },
        {"header.state", "Antwort" },
        {"header.category", "Benutzt f�r" },
        {"header.requestdate", "Anfragedatum" },
        {"header.initiator", "Von" },
        {"header.receiver", "An" },
        {"label.certificate", "Zertifikat:"},
        {"header.alias", "Zertifikat"},
        {"header.activity", "Systemaktivit�t" },
        {"activity.waitingforprocessing", "Warte auf Verarbeitung" },
        {"activity.waitingforanswer", "Warte auf Antwort" },
        {"activity.waitingfordate", "Warte bis zum Aktivierungdatum ({0})" },
        {"activity.activated", "Keine - Aktiviert am {0}" },
        {"activity.none", "Keine" },
        {"tab.certificate", "Zertifikatinformation" },
        {"tab.reasonforrejection", "Ablehnungsbegr�ndung" },
    };
    
}