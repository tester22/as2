//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/server/ResourceBundleAS2Server_fr.java,v 1.1 2015/01/06 11:07:49 heller Exp $
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
 * @author E.Pailleau
 * @version $Revision: 1.1 $
 */
public class ResourceBundleAS2Server_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"fatal.limited.strength", "La force principale limit�e a �t� d�tect�e dans le JVM. Veuillez installer le \"Unlimited jurisdiction key strength policy\" dossiers avant de courir le serveur " + AS2ServerVersion.getProductName() + "." },
        {"server.willstart", "{0}"},
        {"server.started", "D�marrage du " + AS2ServerVersion.getFullProductName() + " dans {0} ms."},
        {"server.already.running", "Une instance de " + AS2ServerVersion.getProductName() + " semble d�j� en cours.\nIl est aussi possible qu''une instance pr�c�dente du programme ne s''est pas termin�e correctement. Si vous �tes s�r qu''aucune autre instance n''est en cours\nmerci de supprimer le fichier de lock \"{0}\" (Date de d�marrage {1}) et red�marrer le serveur."},
        {"server.nohttp", "Le HTTP serveur int�gr� n''a pas �t� commenc�." },
    };
}
