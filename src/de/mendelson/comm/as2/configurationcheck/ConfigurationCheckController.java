//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/configurationcheck/ConfigurationCheckController.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.configurationcheck;

import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.timing.CertificateExpireController;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Controlles the certificates and checks if they will expire soon
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ConfigurationCheckController {

    private CertificateManager managerEncSign;
    private CertificateManager managerSSL;
    private ConfigurationCheckThread checkThread;
    private Connection configConnection;
    private Connection runtimeConnection;
    private final List<ConfigurationIssue> issueList = Collections.synchronizedList(new ArrayList<ConfigurationIssue>());
    private PreferencesAS2 preferences = new PreferencesAS2();

    public ConfigurationCheckController(CertificateManager managerEncSign, CertificateManager managerSSL, Connection configConnection,
            Connection runtimeConnection) {
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.managerEncSign = managerEncSign;
        this.managerSSL = managerSSL;
    }

    /**
     * Returns all available issues that are detected by the server control
     */
    public List<ConfigurationIssue> getIssues() {
        List<ConfigurationIssue> issues = new ArrayList<ConfigurationIssue>();
        synchronized (this.issueList) {
            issues.addAll(this.issueList);
        }
        return (issues);
    }

    /**
     * Starts the embedded task that guards the log
     */
    public void start() {
        this.checkThread = new ConfigurationCheckThread(this.configConnection, this.runtimeConnection);
        new Thread(this.checkThread).start();
    }

    public class ConfigurationCheckThread implements Runnable {

        private Connection configConnection;
        private Connection runtimeConnection;
        private boolean stopRequested = false;
        //wait this time between checks, once a day
        private final long WAIT_TIME = TimeUnit.SECONDS.toMillis(30);

        public ConfigurationCheckThread(Connection configConnection, Connection runtimeConnection) {
            this.configConnection = configConnection;
            this.runtimeConnection = runtimeConnection;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("Configuration check thread");
            while (!stopRequested) {
                synchronized (issueList) {
                    issueList.clear();
                }
                this.checkCertificatesExpired();
                this.checkSSLKeystore();
                this.checkAutoDelete();
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    //nop
                }
            }
        }

        private void checkCertificatesExpired() {
            List<KeystoreCertificate> encSignList = managerEncSign.getKeyStoreCertificateList();
            for (KeystoreCertificate cert : encSignList) {
                if (CertificateExpireController.getCertificateExpireDuration(cert) <= 0) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.CERTIFICATE_EXPIRED_ENC_SIGN);
                    issue.setDetails(cert.getAlias());
                    synchronized (issueList) {
                        issueList.add(issue);
                    }
                }
            }
            List<KeystoreCertificate> sslList = managerSSL.getKeyStoreCertificateList();
            for (KeystoreCertificate cert : sslList) {
                if (CertificateExpireController.getCertificateExpireDuration(cert) <= 0) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.CERTIFICATE_EXPIRED_SSL);
                    issue.setDetails(cert.getAlias());
                    synchronized (issueList) {
                        issueList.add(issue);
                    }
                }
            }
        }

        private void checkSSLKeystore() {
            List<KeystoreCertificate> sslList = managerSSL.getKeyStoreCertificateList();
            StringBuilder aliasList = new StringBuilder();
            int keyCount = 0;
            for (KeystoreCertificate cert : sslList) {
                if (cert.getIsKeyPair()) {
                    if (aliasList.length() > 0) {
                        aliasList.append(", ");
                    }
                    aliasList.append(cert.getAlias());
                    keyCount++;
                }
            }
            if (keyCount == 0) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.NO_KEY_IN_SSL_KEYSTORE);
                synchronized (issueList) {
                    issueList.add(issue);
                }
            }
            if (keyCount > 1) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.MULTIPLE_KEYS_IN_SSL_KEYSTORE);
                issue.setDetails(aliasList.toString());
                synchronized (issueList) {
                    issueList.add(issue);
                }
            }
        }

        private void checkAutoDelete() {
            if (!preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE)) {
                MessageAccessDB messageAccess = new MessageAccessDB(configConnection, runtimeConnection);
                int transmissionCount = messageAccess.getMessageCount();
                if (transmissionCount > 30000) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE);
                    issue.setDetails(String.valueOf(transmissionCount));
                    synchronized (issueList) {
                        issueList.add(issue);
                    }
                }
            }
        }
    }
}
