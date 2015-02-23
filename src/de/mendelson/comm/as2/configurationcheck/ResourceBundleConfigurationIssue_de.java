//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/configurationcheck/ResourceBundleConfigurationIssue_de.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.configurationcheck;

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
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleConfigurationIssue_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        //preferences localized
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_ENC_SIGN), "Zertifikat ist abgelaufen (enc/sign)"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_SSL), "Zertifikat ist abgelaufen (SSL)"},
        {String.valueOf(ConfigurationIssue.MULTIPLE_KEYS_IN_SSL_KEYSTORE), "Mehrere Schlüssel im SSL Keystore gefunden"},
        {String.valueOf(ConfigurationIssue.NO_KEY_IN_SSL_KEYSTORE), "Kein Schlüssel im SSL Keystore gefunden"},
        {String.valueOf(ConfigurationIssue.HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE), "Aktivieren Sie automatisches Löschen - Im System ist eine grosse Menge von Transaktionen"},
    };
}
