//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/modulelock/LockRefreshThread.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.modulelock;

import de.mendelson.comm.as2.clientserver.message.ModuleLockRequest;
import de.mendelson.comm.as2.clientserver.message.ModuleLockResponse;
import de.mendelson.util.clientserver.BaseClient;
import java.util.concurrent.TimeUnit;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Refreshes the lock if a client has the exclusive lock on a module
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class LockRefreshThread implements Runnable {

    private String moduleName;
    private boolean stop = false;
    private BaseClient baseClient;

    public LockRefreshThread(BaseClient baseClient, String moduleName) {
        this.moduleName = moduleName;
        this.baseClient = baseClient;
    }

    public void pleaseStop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!this.stop) {            
            ModuleLockRequest request = new ModuleLockRequest(this.moduleName, ModuleLockRequest.TYPE_REFRESH);
            ModuleLockResponse response = (ModuleLockResponse) this.baseClient.sendSync(request);
            try {
                Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(15));
            } catch (Exception e) {
                //NOP
            }
        }
    }

}
