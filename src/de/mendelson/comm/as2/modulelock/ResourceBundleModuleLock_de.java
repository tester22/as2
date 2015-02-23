//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/modulelock/ResourceBundleModuleLock_de.java,v 1.1 2015/01/06 11:07:42 heller Exp $
package de.mendelson.comm.as2.modulelock;

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
public class ResourceBundleModuleLock_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {ModuleLock.MODULE_ENCSIGN_KEYSTORE, "Zertifikatverwaltung (Verschl�sselungs-/Signatur)" },
        {ModuleLock.MODULE_PARTNER, "Partnerverwaltung" },
        {ModuleLock.MODULE_SERVER_SETTINGS, "Servereinstellungen" },
        {ModuleLock.MODULE_SSL_KEYSTORE, "Zertifikatverwaltung (SSL)" },
        {"modifications.notallowed.message", "�nderungen sind im Moment nicht m�glich" },
        {"configuration.changed.otherclient", "Ein anderer Client k�nnte �nderungen im Modul {0} vorgenommen haben.\nBitte �ffnen Sie diese Konfigurationsoberfl�che erneut, um die aktuelle Konfiguration neu zu laden." },
        {"configuration.locked.otherclient", "Das Modul {0} ist exklusiv von einem anderen Client ge�ffnet, Sie k�nnen aktuell keine �nderungen vornehmen.\nDetails des anderen Clients:\nIP: {1}\nBenutzer: {2}" },                
    };
}
