//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/timing/FileDeleteController.java,v 1.1 2015/01/06 11:07:50 heller Exp $
package de.mendelson.comm.as2.timing;

import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import java.io.File;
import java.sql.Connection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Controlles the timed deletion of AS2 file entries from the file system
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class FileDeleteController {

    /**
     * Logger to log inforamtion to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private PreferencesAS2 preferences = new PreferencesAS2();
    private FileDeleteThread deleteThread;
    private ClientServer clientserver = null;
    private MecResourceBundle rb = null;
    private Connection configConnection;
    private Connection runtimeConnection;

    public FileDeleteController(ClientServer clientserver, Connection configConnection,
            Connection runtimeConnection) {
        this.clientserver = clientserver;
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleFileDeleteController.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Starts the embedded task that guards the files to delete
     */
    public void startAutoDeleteControl() {
        this.deleteThread = new FileDeleteThread(this.configConnection, this.runtimeConnection);
        Executors.newSingleThreadExecutor().submit(this.deleteThread);
    }

    public class FileDeleteThread implements Runnable {

        private boolean stopRequested = false;
        //wait this time between checks, once an hour
        private final long WAIT_TIME = TimeUnit.HOURS.toMillis(1);
        //DB connection
        private Connection configConnection;
        private Connection runtimeConnection;

        public FileDeleteThread(Connection configConnection, Connection runtimeConnection) {
            this.configConnection = configConnection;
            this.runtimeConnection = runtimeConnection;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("Contol auto AS2 file delete");
            while (!stopRequested) {
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    //nop
                }
                if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE)) {
                    File rawIncomingDir = new File(new File(preferences.get(PreferencesAS2.DIR_MSG)).getAbsolutePath() + File.separator + "_rawincoming");
                    File[] fileList = rawIncomingDir.listFiles();
                    int numberOfDays = preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN);
                    long cutoff = TimeUnit.DAYS.toMillis(numberOfDays);
                    if (fileList != null) {
                        for (File file : fileList) {
                            if (file.isFile()) {
                                long diff = System.currentTimeMillis() - file.lastModified();
                                if (diff > cutoff) {
                                    file.delete();
                                }
                            }
                        }
                    }
                    AS2Tools.deleteTempFilesOlderThan(numberOfDays);
                }
            }
        }
    }
}
