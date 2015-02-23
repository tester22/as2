//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/cem/ResourceBundleCEM_de.java,v 1.1 2015/01/06 11:07:31 heller Exp $
package de.mendelson.comm.as2.cem;
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
public class ResourceBundleCEM_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {                
        {"cem.validated.schema", "{0}: Die CEM Nachricht wurde erfolgreich validiert." },
        {"cert.already.imported", "{0}: Das �bermittelte CEM Zertifikat exisitert bereits in dem System (Alias {1}), der Import wurde �bersprungen."},
        {"cert.imported.success", "{0}: Das �bermittelte CEM Zertifikat wurde erfolgreich in das System importiert (Alias {1})."},
        {"category." + CEMEntry.CATEGORY_CRYPT, "Verschl�sselung" },
        {"category." + CEMEntry.CATEGORY_SIGN, "Signatur" },
        {"category." + CEMEntry.CATEGORY_SSL, "SSL" },
        {"state." + CEMEntry.STATUS_ACCEPTED_INT, "Akzeptiert von {0}" },
        {"state." + CEMEntry.STATUS_PENDING_INT, "Noch keine Antwort von {0}" },
        {"state." + CEMEntry.STATUS_REJECTED_INT, "Abgelehnt von {0}" },
        {"state." + CEMEntry.STATUS_CANCELED_INT, "Vorgang abgebrochen" },
        {"state." + CEMEntry.STATUS_PROCESSING_ERROR_INT, "Verarbeitungsfehler" },
        {"cemtype.response", "{0}: Die CEM Nachricht ist vom Typ \"certificate response\"" },
        {"cemtype.request", "{0}: Die CEM Nachricht ist vom Typ \"certificate request\"" },
        {"cem.response.relatedrequest.found", "{0}: Die CEM Nachricht bezieht sich auf die Anfrage \"{1}\"" },
        {"cem.response.prepared", "{0}: CEM Antwortnachricht erstellt f�r die Anfrage {1}" },
    };
    
}