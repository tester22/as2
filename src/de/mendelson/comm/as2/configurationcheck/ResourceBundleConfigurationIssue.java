//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/configurationcheck/ResourceBundleConfigurationIssue.java,v 1.1 2015/01/06 11:07:39 heller Exp $
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
public class ResourceBundleConfigurationIssue extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        //preferences localized
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_ENC_SIGN), "Certificate expired (enc/sign)"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_SSL), "Certificate expired (SSL)"},
        {String.valueOf(ConfigurationIssue.MULTIPLE_KEYS_IN_SSL_KEYSTORE), "Multiple keys found in SSL keystore"},
        {String.valueOf(ConfigurationIssue.NO_KEY_IN_SSL_KEYSTORE), "No key found in SSL keystore"},
        {String.valueOf(ConfigurationIssue.HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE), "Setup auto delete process - Huge amount of transactions in the system"},
    };
}
