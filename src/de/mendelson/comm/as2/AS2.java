//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/AS2.java,v 1.1 2015/01/06 11:07:29 heller Exp $
package de.mendelson.comm.as2;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Locale;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.mendelson.comm.as2.client.AS2Gui;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.server.AS2Agent;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.comm.as2.server.UpgradeRequiredException;
import de.mendelson.util.Splash;
import de.mendelson.util.security.BCCryptoHelper;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Start the AS2 server and the configuration GUI
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class AS2 {

    /**Displays a usage of how to use this class
     */
    public static void printUsage() {
        System.out.println("java " + AS2.class.getName() + " <options>");
        System.out.println("Start up a " + AS2ServerVersion.getProductNameShortcut() + " server ");
        System.out.println("Options are:");
        System.out.println("-lang <String>: Language to use for the server, nonpersistent. Possible values are \"en\", \"fr\" and \"de\".");
        System.out.println("-nohttpserver: Do not start the integrated HTTP server, only useful if you are integrating the product into an other web container");
        System.out.println("-allowallclients: Allows client connections from every host. Without this setting client connections are allowed from localhost only");
        System.out.println("-nostartserver: Do not start the integrated AS2 server");
        System.out.println("-nostartclient: Do not start the integrated client");    
    }

    /**Method to start the server on from the command line*/
    public static void main(String args[]) {
        String language = null;
        boolean startHTTP = true;
        boolean allowAllClients = false;
        boolean startServer = true;
        boolean startClient = true;
        int optind;
        for (optind = 0; optind < args.length; optind++) {
            if (args[optind].toLowerCase().equals("-lang")) {
                language = args[++optind];
            } else if (args[optind].toLowerCase().equals("-nohttpserver")) {
                startHTTP = false;
            } else if (args[optind].toLowerCase().equals("-allowallclients")) {
                allowAllClients = true;
            } else if (args[optind].toLowerCase().equals("-nostartserver")) {
                startServer = false;
            } else if (args[optind].toLowerCase().equals("-nostartclient")) {
                startClient = false;
            } else if (args[optind].toLowerCase().equals("-?")) {
                AS2.printUsage();
                System.exit(1);
            } else if (args[optind].toLowerCase().equals("-h")) {
                AS2.printUsage();
                System.exit(1);
            } else if (args[optind].toLowerCase().equals("-help")) {
                AS2.printUsage();
                System.exit(1);
            }
        }
        //load language from preferences
        if (language == null) {
            PreferencesAS2 preferences = new PreferencesAS2();
            language = preferences.get(PreferencesAS2.LANGUAGE);
        }
        if (language != null) {
            if (language.toLowerCase().equals("en")) {
                Locale.setDefault(Locale.ENGLISH);
            } else if (language.toLowerCase().equals("de")) {
                Locale.setDefault(Locale.GERMAN);
            } else if (language.toLowerCase().equals("fr")) {
                Locale.setDefault(Locale.FRENCH);
            } else {
                AS2.printUsage();
                System.out.println();
                System.out.println("Language " + language + " is not supported.");
                System.exit(1);
            }
        }

        //initialize the security provider
        BCCryptoHelper helper = new BCCryptoHelper();
        helper.initialize();

        
        if (startServer == true) {

            //start server
            try {
                //register the database drivers for the VM
                Class.forName("org.hsqldb.jdbcDriver");
                AS2Server as2Server = new AS2Server(startHTTP, allowAllClients);
                AS2Agent agent = new AS2Agent(as2Server);
            } catch (UpgradeRequiredException e) {
                //an upgrade to HSQLDB 2.x is required, delete the lock file
                Logger.getLogger(AS2Server.SERVER_LOGGER_NAME).warning(e.getMessage());
                Logger.getLogger(AS2Server.SERVER_LOGGER_NAME).warning(e.getClass().getName() + ": " + e.getMessage());
                AS2Server.deleteLockFile();
                System.exit(1);
            } catch (Throwable e) {
                e.printStackTrace();
                String message = e.getMessage();
                if( message == null ){
                    message = "[" + e.getClass().getName() + "]";
                }
                Logger.getLogger(AS2Server.SERVER_LOGGER_NAME).warning(message);
                AS2Server.deleteLockFile();
                System.exit(1);
            }
        	

        }        
        if (startClient == true) {
        
        Splash splash = new Splash("/de/mendelson/comm/as2/client/Splash.jpg");
        AffineTransform transform = new AffineTransform();
        splash.setTextAntiAliasing(false);
        transform.setToScale(1.0, 1.0);
        splash.addDisplayString(new Font("Verdana", Font.BOLD, 11),
                7, 262, AS2ServerVersion.getFullProductName(),
                new Color(0x65, 0xB1, 0x80), transform);
        splash.setVisible(true);
        splash.toFront();
        //start client
        AS2Gui gui = new AS2Gui(splash, "localhost");
        gui.setVisible(true);
        splash.destroy();
        splash.dispose();
        }
    }
}