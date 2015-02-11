//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/server/UpgradeRequiredException.java,v 1.1 2015/01/06 11:07:49 heller Exp $
package de.mendelson.comm.as2.server;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Exception that is thrown if a database upgrade is required
 * @author  S.Heller
 */
public class UpgradeRequiredException extends Exception {

    public UpgradeRequiredException(String message) {
        super(message);
    }
}
