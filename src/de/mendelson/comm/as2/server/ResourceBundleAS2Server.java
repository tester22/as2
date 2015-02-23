//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/server/ResourceBundleAS2Server.java,v 1.1 2015/01/06 11:07:49 heller Exp $
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
public class ResourceBundleAS2Server extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"fatal.limited.strength", "Limited key strength has been detected in the JVM. Please install the \"Unlimited jurisdiction key strength policy files\" before running the " + AS2ServerVersion.getProductName() + " server." },
        {"server.willstart", "{0}"},
        {"server.started", AS2ServerVersion.getFullProductName() + " startup in {0} ms."},
        {"server.already.running", "An instance of " + AS2ServerVersion.getProductName() + " seems to be already running.\nIt is also possible that the previous instance of the program did not exit correctly. If you are sure that no other instance is running\nplease delete the lock file \"{0}\" (start date {1}) and restart the server."},
        {"server.nohttp", "The integrated HTTP server has not been started." },
    };
}