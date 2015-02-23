//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/AS2ShutdownThread.java,v 1.1 2015/01/06 11:07:30 heller Exp $
package de.mendelson.comm.as2;

import org.eclipse.jetty.server.Server;

import de.mendelson.comm.as2.database.DBServer;
import de.mendelson.comm.as2.sendorder.SendOrderReceiver;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
import de.mendelson.comm.as2.server.AS2Server;

/**
 * Thread that is executed if the VM will shut down (that means the server
 * is shut down)
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class AS2ShutdownThread extends Thread {

    private DBServer dbServer;
    private Server httpServer;
    private SendOrderReceiver receiver;

    public AS2ShutdownThread(DBServer dbServer, Server httpServer, SendOrderReceiver receiver) {
        this.dbServer = dbServer;
        this.httpServer = httpServer;
        this.receiver = receiver;
    }

    /**This will start the thread, it is called if the JVM shutdown is detected*/
    @Override
    public void run() {        
        try {
            this.receiver.stopReceiver();
        } catch (Throwable e) {
            //nop
        }
        try {
            this.httpServer.stop();
        } catch (Throwable e) {
            //nop
        }
        try {
            this.dbServer.shutdown();
        } catch (Throwable e) {
            //nop
        }
        //delete lock file
        AS2Server.deleteLockFile();
        System.out.println(AS2ServerVersion.getProductName() + " shutdown.");
    }
}
