//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/client/ModuleStarter.java,v 1.1 2015/01/06 11:07:37 heller Exp $ 
package de.mendelson.comm.as2.client;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Interface to start some client modules
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public interface ModuleStarter {

    public void displayCertificateManagerEncSign(String selectedAlias);

    public void displayCertificateManagerSSL(String selectedAlias);

    public void displayPreferences(String selectedTab);

}
