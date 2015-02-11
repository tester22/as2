//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/configurationcheck/ConfigurationIssue.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.configurationcheck;

import java.io.Serializable;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Contains a single configuration issue
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ConfigurationIssue implements Serializable{

    public static final int NO_KEY_IN_SSL_KEYSTORE = 1;
    public static final int MULTIPLE_KEYS_IN_SSL_KEYSTORE = 2;
    public static final int CERTIFICATE_EXPIRED_SSL = 3;
    public static final int CERTIFICATE_EXPIRED_ENC_SIGN = 4;
    public static final int HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE = 5;
    
    private int issueId;
    private String details = null;
    
    public ConfigurationIssue(int issueId) {
        this.issueId = issueId;
    }

    /**
     * @return the issueId
     */
    public int getIssueId() {
        return issueId;
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    
    
}
