//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/server/ResourceBundleAS2ServerProcessing_fr.java,v 1.1 2015/01/06 11:07:49 heller Exp $
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
public class ResourceBundleAS2ServerProcessing_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        {"send.failed", "Send a échoué"},
        {"unable.to.process", "Impossible de traiter sur serveur : {0}"},
        {"server.shutdown", "L''utilisateur {0} demande l''arrêt du serveur."},
        {"sync.mdn.sent", "{0}: MDN synchrone envoyé comme réponse au message {1}." },
    };
}
