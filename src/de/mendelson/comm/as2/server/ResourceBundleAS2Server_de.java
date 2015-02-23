//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/server/ResourceBundleAS2Server_de.java,v 1.1 2015/01/06 11:07:49 heller Exp $
package de.mendelson.comm.as2.server;

import de.mendelson.comm.as2.AS2ServerVersion;
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
public class ResourceBundleAS2Server_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"fatal.limited.strength", "Diese Java VM unterst�tzt nicht die notwendige Schl�ssell�nge. Bitte installieren Sie die \"Unlimited jurisdiction key strength policy\" Dateien, bevor Sie den " + AS2ServerVersion.getProductName() + " Server starten." },
        {"server.willstart", "{0}"},
        {"server.started", AS2ServerVersion.getFullProductName() + " gestartet in {0} ms."},
        {"server.already.running", "Eine " + AS2ServerVersion.getProductName() + " Instanz scheint bereits zu laufen.\nEs k�nnte jedoch auch sein, dass eine vorherige Instanz nicht korrekt beendet wurde." + " Wenn Sie sicher sind, dass keine andere Instanz l�uft,\nl�schen Sie bitte die Lock Datei \"{0}\"\n(Start Datum {1}) und starten den Server erneut."},
        {"server.nohttp", "Der integrierte HTTP Server wurde nicht gestartet." },
    };
}