//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/JDialogCertificates.java,v 1.1 2015/01/06 11:07:58 heller Exp $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.gui.keygeneration.JDialogGenerateKey;
import de.mendelson.util.ImageUtil;
import de.mendelson.util.MecFileChooser;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.AllowModificationCallback;
import de.mendelson.util.clientserver.GUIClient;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.CertificateInUseChecker;
import de.mendelson.util.security.cert.KeystoreStorage;
import de.mendelson.util.security.csr.CSRUtil;
import de.mendelson.util.security.keygeneration.KeyGenerationResult;
import de.mendelson.util.security.keygeneration.KeyGenerationValues;
import de.mendelson.util.security.keygeneration.KeyGenerator;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Dialog to configure the partner of the rosettanet server
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class JDialogCertificates extends JDialog implements ListSelectionListener {

    /**
     * Resource to locaize the GUI
     */
    private MecResourceBundle rb = null;
    private JPanelCertificates panelCertificates = null;
    private CertificateManager manager;
    private Logger logger = null;
    private GUIClient guiClient;
    private String productName;
    private List<AllowModificationCallback> allowModificationCallbackList = new ArrayList<AllowModificationCallback>();

    /**
     * Creates new form JDialogMessageMapping
     */
    public JDialogCertificates(JFrame parent, Logger logger, GUIClient guiClient,
            String title, String productName, boolean moduleLockedByAnotherClient) {
        super(parent, title, true);
        this.guiClient = guiClient;
        this.logger = logger;
        this.productName = productName;
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCertificates.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.initComponents();
        this.setJMenuBar(this.jMenuBar);
        this.getRootPane().setDefaultButton(this.jButtonOk);
        //mix up the add/edit/delete icons
        ImageUtil imageUtil = new ImageUtil();
        ImageIcon iconBase = new ImageIcon(this.getClass().
                getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"));
        ImageIcon iconDelete = new ImageIcon(this.getClass().
                getResource("/de/mendelson/util/security/cert/gui/mini_delete.gif"));
        ImageIcon iconEdit = new ImageIcon(this.getClass().
                getResource("/de/mendelson/util/security/cert/gui/mini_edit.gif"));
        ImageIcon iconMixedDelete = imageUtil.mixImages(iconBase, iconDelete);
        ImageIcon iconMixedEdit = imageUtil.mixImages(iconBase, iconEdit);
        this.jButtonDeleteCertificate.setIcon(iconMixedDelete);
        this.jButtonEditCertificate.setIcon(iconMixedEdit);
        this.jMenuItemFileDelete.setIcon(iconMixedDelete);
        this.jMenuItemFileEdit.setIcon(iconMixedEdit);
        this.panelCertificates = new JPanelCertificates(this.logger, this);
        this.panelCertificates.setButtons(this.jButtonEditCertificate, this.jButtonDeleteCertificate);
        this.panelCertificates.setMenuItems(this.jMenuItemFileEdit, this.jMenuItemFileDelete);
        this.jPanelCertificatesMain.add(this.panelCertificates);
        this.jPanelModuleLockWarning.setVisible( moduleLockedByAnotherClient );
    }

    /**
     * Adds a callback that is called if a user tries to modify the
     * configuration A modification will be prevented if one of the callbacks
     * does not allow it
     */
    public void addAllowModificationCallback(AllowModificationCallback callback) {
        this.allowModificationCallbackList.add(callback);
        this.panelCertificates.addAllowModificationCallback(callback);
    }

    /**
     * Initializes the keystore gui
     */
    public void initialize(KeystoreStorage keystoreStorage) {
        this.manager = new CertificateManager(this.logger);
        this.setTitle(this.getTitle() + " | " + keystoreStorage.getKeystoreType());
        manager.loadKeystoreCertificates(keystoreStorage);
        this.jTextFieldCertFileInfo.setText(keystoreStorage.getOriginalKeystoreFilename());
        this.panelCertificates.addKeystore(manager);
        this.setButtonState();
    }

    public void setSelectionByAlias(String selectedAlias) {
        this.panelCertificates.setSelectionByAlias(selectedAlias);
    }

    public void addCertificateInUseChecker(CertificateInUseChecker checker) {
        this.panelCertificates.addCertificateInUseChecker(checker);
    }

    /**
     * Checks if the operation is possible because the keystore is R/O and
     * displayes a message if not It's also possible to set the module into a
     * mode where modifications are not allowed - this will be displayed, too
     */
    private boolean isOperationAllowed() {
        for (AllowModificationCallback callback : this.allowModificationCallbackList) {
            boolean modificationAllowed = callback.allowModification();
            if (!modificationAllowed) {
                return (false);
            }
        }
        boolean readWrite = true;
        readWrite = readWrite && this.manager.canWrite();
        if (!readWrite) {
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("keystore.readonly.message"),
                    this.rb.getResourceString("keystore.readonly.title"),
                    JOptionPane.ERROR_MESSAGE);
        }
        return (readWrite);
    }

    /**
     * Imports a certificate into the keystore
     */
    private void importCertificate() {
        if (!this.isOperationAllowed()) {
            return;
        }
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        MecFileChooser chooser = new MecFileChooser(
                parent,
                this.rb.getResourceString("filechooser.certificate.import"));
        String importFilename = chooser.browseFilename();
        if (importFilename == null) {
            return;
        }
        JDialogInfoOnExternalCertificate infoDialog = new JDialogInfoOnExternalCertificate(parent, new File(importFilename));
        infoDialog.setVisible(true);
        if (!infoDialog.importPressed()) {
            return;
        }
        int selectedCertificateIndex = infoDialog.getCertificateIndex();
        FileInputStream inStream = null;
        try {
            KeyStoreUtil util = new KeyStoreUtil();
            Provider provBC = new BouncyCastleProvider();
            inStream = new FileInputStream(importFilename);
            List<X509Certificate> certList = util.readCertificates(inStream, provBC);
            X509Certificate importCertificate = certList.get(selectedCertificateIndex);
            String proposedAlias = util.getProposalCertificateAliasForImport(importCertificate);
            String alias = JOptionPane.showInputDialog(this,
                    this.rb.getResourceString("certificate.import.alias"), proposedAlias);
            if (alias == null || alias.trim().length() == 0) {
                return;
            }
            util.importX509Certificate(this.manager.getKeystore(), importFilename, alias, provBC);
            this.manager.saveKeystore();
            this.manager.rereadKeystoreCertificates();
            this.panelCertificates.refreshData();
            this.panelCertificates.certificateAdded(alias);
            KeystoreCertificate keystoreCertificate = this.manager.getKeystoreCertificate(alias);
            String messageKey = "certificate.import.success.message";
            if (keystoreCertificate.isRootCertificate()) {
                messageKey = "certificate.root.import.success.message";
            }
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString(messageKey, alias),
                    this.rb.getResourceString("certificate.import.success.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            //multiple certificates: show the import dialog again
            if (certList.size() > 1) {
                infoDialog.setVisible(true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("certificate.import.error.message", e.getMessage()),
                    this.rb.getResourceString("certificate.import.error.title"),
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
    }

    /**
     * Imports a key in PEM format to the keystore
     */
    private void importPEMKey() {
        if (!isOperationAllowed()) {
            return;
        }
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        JDialogImportKeyPEM dialog = new JDialogImportKeyPEM(parent, this.manager,
                this.manager.getKeystoreType());
        dialog.setVisible(true);
        try {
            this.manager.saveKeystore();
            this.panelCertificates.refreshData();
            this.panelCertificates.certificateAdded(dialog.getNewAlias());
        } catch (Throwable e) {
            this.logger.severe(e.getMessage());
        }
    }

    /**
     * Imports a key in PKCS12 format to the keystore
     */
    private void importPKCS12Key() {
        if (!isOperationAllowed()) {
            return;
        }
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        JDialogImportKeyPKCS12 dialog = new JDialogImportKeyPKCS12(parent, this.logger, this.manager);
        dialog.setVisible(true);
        try {
            this.manager.saveKeystore();
            this.panelCertificates.refreshData();
            this.panelCertificates.certificateAdded(dialog.getNewAlias());
        } catch (Throwable e) {
            this.logger.severe(e.getMessage());
        }
    }

    /**
     * Imports a key in PKCS12 format to the keystore
     */
    private void importJKSKey() {
        if (!isOperationAllowed()) {
            return;
        }
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        JDialogImportKeyJKS dialog = new JDialogImportKeyJKS(parent, this.logger, this.manager);
        dialog.setVisible(true);
        try {
            this.manager.saveKeystore();
            this.panelCertificates.refreshData();
            this.panelCertificates.certificateAdded(dialog.getNewAlias());
        } catch (Throwable e) {
            this.logger.severe(e.getMessage());
        }
    }

    private void generateCSR(boolean initial) {
        CSRUtil util = new CSRUtil();
        KeystoreCertificate selectedCert = this.panelCertificates.getSelectedCertificate();
        try {
            PKCS10CertificationRequest csr = util.generateCSR(this.manager, selectedCert.getAlias());
            String title = this.rb.getResourceString("csr.title.renew");
            String storequestion = this.rb.getResourceString("csr.message.storequestion.renew");
            String[] options = new String[]{
                this.rb.getResourceString("csr.option.1.renew"),
                this.rb.getResourceString("csr.option.2"),};
            if (initial) {
                title = this.rb.getResourceString("csr.title");
                storequestion = this.rb.getResourceString("csr.message.storequestion");
                options = new String[]{
                    this.rb.getResourceString("csr.option.1"),
                    this.rb.getResourceString("csr.option.2"),};
            }
            //ask the user if the partner should be really deleted, all data is lost
            int requestValue = JOptionPane.showOptionDialog(this,
                    storequestion,
                    title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (requestValue == 0) {
                this.buyKeyAtMendelson(csr);
            } else {
                JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
                MecFileChooser chooser = new MecFileChooser(parent,
                        this.rb.getResourceString("label.selectcsrfile"));
                String outFilename = chooser.browseFilename();
                if (outFilename != null) {
                    File outFile = new File(outFilename);
                    util.storeCSRPEM(csr, outFile);
                    JOptionPane.showMessageDialog(this,
                            this.rb.getResourceString("csr.generation.success.message",
                                    outFile.getAbsolutePath()),
                            this.rb.getResourceString("csr.generation.success.title"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            this.logger.severe(e.getMessage());
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("csr.generation.failure.message", e.getMessage()),
                    this.rb.getResourceString("csr.generation.failure.title"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buyKeyAtMendelson(PKCS10CertificationRequest csr) throws Exception {
        CSRUtil util = new CSRUtil();
        String csrStr = util.storeCSRPEM(csr);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost("http://ca.mendelson-e-c.com/csr2session.php");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            BasicNameValuePair[] params = {
                new BasicNameValuePair("csrpem", csrStr),
                new BasicNameValuePair("source", this.productName),};
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(Arrays.asList(params));
            urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            ByteArrayOutputStream memOut = new ByteArrayOutputStream();
            response.getEntity().writeTo(memOut);
            memOut.close();
            String sessionId = new String(memOut.toByteArray());
            URI uri = new URI("http://ca.mendelson-e-c.com?area=buy&stage=checkcsr&sid=" + sessionId);
            Desktop.getDesktop().browse(uri);
        } finally {
            if (httpClient != null && httpClient.getConnectionManager() != null) {
                //shutdown the HTTPClient to release the resources
                httpClient.getConnectionManager().shutdown();
            }
        }

    }

    private void generateKeypair() {
        KeyGenerator generator = new KeyGenerator();
        try {
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
            JDialogGenerateKey dialog = new JDialogGenerateKey(parent, this.manager);
            dialog.setVisible(true);
            KeyGenerationValues values = dialog.getValues();
            if (values == null) {
                //user break
                return;
            }
            KeyGenerationResult result = generator.generateKeyPair(values);
            KeyStoreUtil util = new KeyStoreUtil();
            String alias = util.getProposalCertificateAliasForImport(result.getCertificate());
            alias = util.ensureUniqueAliasName(this.manager.getKeystore(), alias);
            this.manager.getKeystore().setKeyEntry(alias, result.getKeyPair().getPrivate(),
                    this.manager.getKeystorePass(), new X509Certificate[]{result.getCertificate()});
            this.manager.saveKeystore();
            this.manager.rereadKeystoreCertificates();
            this.panelCertificates.refreshData();
            this.panelCertificates.certificateAdded(alias);
        } catch (Throwable e) {
            String message = e.getClass().getName() + ": " + e.getMessage();
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("generatekey.error.message", message),
                    this.rb.getResourceString("generatekey.error.title"),
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void importCSRResponse(boolean renew) {
        CSRUtil util = new CSRUtil();
        KeystoreCertificate selectedCert = this.panelCertificates.getSelectedCertificate();
        String selectedCertAlias = selectedCert.getAlias();
        try {
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
            MecFileChooser chooser = new MecFileChooser(parent,
                    this.rb.getResourceString("label.selectcsrrepsonsefile"));
            String inFilename = chooser.browseFilename();
            if (inFilename != null) {
                if (renew) {
                    //clones the key entry and selects the new one
                    String newAlias = this.cloneSelectedCertificate();
                    if (newAlias != null) {
                        selectedCertAlias = newAlias;
                    } else {
                        throw new Exception("Processing failure: Unable to set new key alias");
                    }
                }
                util.importCSRReply(this.manager, selectedCertAlias, new File(inFilename));
                JOptionPane.showMessageDialog(this,
                        this.rb.getResourceString("csrresponse.import.success.message"),
                        this.rb.getResourceString("csrresponse.import.success.title"),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Throwable e) {
            this.logger.severe(e.getMessage());
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("csrresponse.import.failure.message", e.getMessage()),
                    this.rb.getResourceString("csrresponse.import.failure.title"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveAndClose() {
        if (this.manager != null) {
            try {
                this.manager.saveKeystore();
            } catch (Throwable e) {
                e.printStackTrace();
                JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
                JOptionPane.showMessageDialog(parent, e.getMessage());
                return;
            }
        }
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Refreshes the menues etc
     */
    private void setButtonState() {
        if (this.panelCertificates != null) {
            KeystoreCertificate selectedCert = this.panelCertificates.getSelectedCertificate();
            this.jMenuItemGenerateCSRInitial.setEnabled(selectedCert != null && selectedCert.getIsKeyPair()
                    && selectedCert.isSelfSigned());
            this.jMenuItemImportCSRResponseInitial.setEnabled(selectedCert != null && selectedCert.getIsKeyPair()
                    && selectedCert.isSelfSigned());
            this.jMenuItemGenerateCSRRenew.setEnabled(selectedCert != null && selectedCert.getIsKeyPair()
                    && !selectedCert.isSelfSigned());
            this.jMenuItemImportCSRResponseRenew.setEnabled(selectedCert != null && selectedCert.getIsKeyPair()
                    && !selectedCert.isSelfSigned());
        }
    }

    private String cloneSelectedCertificate() throws Throwable {
        String newAlias = null;
        if (this.panelCertificates != null) {
            KeystoreCertificate selectedCert = this.panelCertificates.getSelectedCertificate();
            if (selectedCert != null && selectedCert.getIsKeyPair()) {
                KeyStoreUtil util = new KeyStoreUtil();
                newAlias = util.getProposalCertificateAliasForImport(selectedCert.getX509Certificate());
                newAlias = util.ensureUniqueAliasName(this.manager.getKeystore(), newAlias);
                PrivateKey privateKey = (PrivateKey) this.manager.getKey(selectedCert.getAlias());
                this.manager.getKeystore().setKeyEntry(newAlias, privateKey,
                        this.manager.getKeystorePass(), new X509Certificate[]{selectedCert.getX509Certificate()});
                this.manager.saveKeystore();
                this.manager.rereadKeystoreCertificates();
                this.panelCertificates.refreshData();
                this.panelCertificates.certificateAdded(newAlias);
            }
        }
        return (newAlias);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemFileEdit = new javax.swing.JMenuItem();
        jMenuItemFileDelete = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItemFileClose = new javax.swing.JMenuItem();
        jMenuImport = new javax.swing.JMenu();
        jMenuItemImportKeyPEM = new javax.swing.JMenuItem();
        jMenuItemImportKeyPKCS12 = new javax.swing.JMenuItem();
        jMenuItemImportKeyJKS = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemImportCertificate = new javax.swing.JMenuItem();
        jMenuExport = new javax.swing.JMenu();
        jMenuItemExportKeyPKCS12 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItemExportCertificate = new javax.swing.JMenuItem();
        jMenuTools = new javax.swing.JMenu();
        jMenuItemGenerateKey = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemGenerateCSRInitial = new javax.swing.JMenuItem();
        jMenuItemImportCSRResponseInitial = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItemGenerateCSRRenew = new javax.swing.JMenuItem();
        jMenuItemImportCSRResponseRenew = new javax.swing.JMenuItem();
        jToolBar = new javax.swing.JToolBar();
        jButtonEditCertificate = new javax.swing.JButton();
        jButtonDeleteCertificate = new javax.swing.JButton();
        jPanelMain = new javax.swing.JPanel();
        jPanelModuleLockWarning = new javax.swing.JPanel();
        jLabelModuleLockedWarning = new javax.swing.JLabel();
        jPanelCertificatesMain = new javax.swing.JPanel();
        jPanelButton = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jTextFieldCertFileInfo = new javax.swing.JTextField();
        jLabelKeystore = new javax.swing.JLabel();

        jMenuFile.setText(this.rb.getResourceString( "menu.file"));

        jMenuItemFileEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jMenuItemFileEdit.setText(this.rb.getResourceString( "button.edit"));
        jMenuItemFileEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileEditActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemFileEdit);

        jMenuItemFileDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jMenuItemFileDelete.setText(this.rb.getResourceString( "button.delete"));
        jMenuItemFileDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileDeleteActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemFileDelete);
        jMenuFile.add(jSeparator3);

        jMenuItemFileClose.setText(this.rb.getResourceString( "menu.file.close"));
        jMenuItemFileClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileCloseActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemFileClose);

        jMenuBar.add(jMenuFile);

        jMenuImport.setText(this.rb.getResourceString( "menu.import" ));

        jMenuItemImportKeyPEM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/key16x16.gif"))); // NOI18N
        jMenuItemImportKeyPEM.setText(this.rb.getResourceString( "label.key.import.pem" ));
        jMenuItemImportKeyPEM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportKeyPEMActionPerformed(evt);
            }
        });
        jMenuImport.add(jMenuItemImportKeyPEM);

        jMenuItemImportKeyPKCS12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/key16x16.gif"))); // NOI18N
        jMenuItemImportKeyPKCS12.setText(this.rb.getResourceString( "label.key.import.pkcs12" ));
        jMenuItemImportKeyPKCS12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportKeyPKCS12ActionPerformed(evt);
            }
        });
        jMenuImport.add(jMenuItemImportKeyPKCS12);

        jMenuItemImportKeyJKS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/key16x16.gif"))); // NOI18N
        jMenuItemImportKeyJKS.setText(this.rb.getResourceString( "label.key.import.jks" ));
        jMenuItemImportKeyJKS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportKeyJKSActionPerformed(evt);
            }
        });
        jMenuImport.add(jMenuItemImportKeyJKS);
        jMenuImport.add(jSeparator1);

        jMenuItemImportCertificate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jMenuItemImportCertificate.setText(this.rb.getResourceString( "label.cert.import" ));
        jMenuItemImportCertificate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportCertificateActionPerformed(evt);
            }
        });
        jMenuImport.add(jMenuItemImportCertificate);

        jMenuBar.add(jMenuImport);

        jMenuExport.setText(this.rb.getResourceString( "menu.export" ));

        jMenuItemExportKeyPKCS12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/key16x16.gif"))); // NOI18N
        jMenuItemExportKeyPKCS12.setText(this.rb.getResourceString( "label.key.export.pkcs12" ));
        jMenuItemExportKeyPKCS12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportKeyPKCS12ActionPerformed(evt);
            }
        });
        jMenuExport.add(jMenuItemExportKeyPKCS12);
        jMenuExport.add(jSeparator2);

        jMenuItemExportCertificate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jMenuItemExportCertificate.setText(this.rb.getResourceString( "label.cert.export" ));
        jMenuItemExportCertificate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportCertificateActionPerformed(evt);
            }
        });
        jMenuExport.add(jMenuItemExportCertificate);

        jMenuBar.add(jMenuExport);

        jMenuTools.setText(this.rb.getResourceString( "menu.tools"));

        jMenuItemGenerateKey.setText(this.rb.getResourceString( "menu.tools.generatekey"));
        jMenuItemGenerateKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenerateKeyActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemGenerateKey);
        jMenuTools.add(jSeparator4);

        jMenuItemGenerateCSRInitial.setText(this.rb.getResourceString( "menu.tools.generatecsr"));
        jMenuItemGenerateCSRInitial.setEnabled(false);
        jMenuItemGenerateCSRInitial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenerateCSRInitialActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemGenerateCSRInitial);

        jMenuItemImportCSRResponseInitial.setText(this.rb.getResourceString( "menu.tools.importcsr"));
        jMenuItemImportCSRResponseInitial.setEnabled(false);
        jMenuItemImportCSRResponseInitial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportCSRResponseInitialActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemImportCSRResponseInitial);
        jMenuTools.add(jSeparator5);

        jMenuItemGenerateCSRRenew.setText(this.rb.getResourceString( "menu.tools.generatecsr.renew"));
        jMenuItemGenerateCSRRenew.setEnabled(false);
        jMenuItemGenerateCSRRenew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenerateCSRRenewActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemGenerateCSRRenew);

        jMenuItemImportCSRResponseRenew.setText(this.rb.getResourceString( "menu.tools.importcsr.renew"));
        jMenuItemImportCSRResponseRenew.setEnabled(false);
        jMenuItemImportCSRResponseRenew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportCSRResponseRenewActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemImportCSRResponseRenew);

        jMenuBar.add(jMenuTools);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);

        jButtonEditCertificate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jButtonEditCertificate.setText(this.rb.getResourceString( "button.edit"));
        jButtonEditCertificate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditCertificateActionPerformed(evt);
            }
        });
        jToolBar.add(jButtonEditCertificate);

        jButtonDeleteCertificate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jButtonDeleteCertificate.setText(this.rb.getResourceString( "button.delete"));
        jButtonDeleteCertificate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteCertificateActionPerformed(evt);
            }
        });
        jToolBar.add(jButtonDeleteCertificate);

        getContentPane().add(jToolBar, java.awt.BorderLayout.NORTH);

        jPanelMain.setLayout(new java.awt.GridBagLayout());

        jPanelModuleLockWarning.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 51, 0)));
        jPanelModuleLockWarning.setLayout(new java.awt.GridBagLayout());

        jLabelModuleLockedWarning.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelModuleLockedWarning.setForeground(new java.awt.Color(204, 51, 0));
        jLabelModuleLockedWarning.setText(this.rb.getResourceString( "module.locked"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelModuleLockWarning.add(jLabelModuleLockedWarning, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 9.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelModuleLockWarning, gridBagConstraints);

        jPanelCertificatesMain.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelCertificatesMain, gridBagConstraints);

        jPanelButton.setLayout(new java.awt.GridBagLayout());

        jButtonOk.setText(this.rb.getResourceString( "button.ok" ));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelButton.add(jButtonOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelButton, gridBagConstraints);

        jTextFieldCertFileInfo.setEditable(false);
        jTextFieldCertFileInfo.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanelMain.add(jTextFieldCertFileInfo, gridBagConstraints);

        jLabelKeystore.setText(this.rb.getResourceString( "label.keystore"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanelMain.add(jLabelKeystore, gridBagConstraints);

        getContentPane().add(jPanelMain, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(797, 692));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemImportKeyJKSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportKeyJKSActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.importJKSKey();
    }//GEN-LAST:event_jMenuItemImportKeyJKSActionPerformed

    private void jMenuItemExportKeyPKCS12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportKeyPKCS12ActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.panelCertificates.exportPKCS12Key();
    }//GEN-LAST:event_jMenuItemExportKeyPKCS12ActionPerformed

    private void jMenuItemExportCertificateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportCertificateActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.panelCertificates.exportSelectedCertificate();
    }//GEN-LAST:event_jMenuItemExportCertificateActionPerformed

    private void jMenuItemImportKeyPKCS12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportKeyPKCS12ActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.importPKCS12Key();
    }//GEN-LAST:event_jMenuItemImportKeyPKCS12ActionPerformed

    private void jMenuItemImportCertificateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportCertificateActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.importCertificate();
    }//GEN-LAST:event_jMenuItemImportCertificateActionPerformed

    private void jMenuItemImportKeyPEMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportKeyPEMActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.importPEMKey();
    }//GEN-LAST:event_jMenuItemImportKeyPEMActionPerformed

    private void jButtonDeleteCertificateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteCertificateActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.panelCertificates.deleteSelectedCertificate();
    }//GEN-LAST:event_jButtonDeleteCertificateActionPerformed

    private void jButtonEditCertificateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditCertificateActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.panelCertificates.renameSelectedAlias();
    }//GEN-LAST:event_jButtonEditCertificateActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.saveAndClose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeDialog

    private void jMenuItemGenerateKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenerateKeyActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.generateKeypair();
    }//GEN-LAST:event_jMenuItemGenerateKeyActionPerformed

    private void jMenuItemGenerateCSRInitialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenerateCSRInitialActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.generateCSR(true);
    }//GEN-LAST:event_jMenuItemGenerateCSRInitialActionPerformed

    private void jMenuItemImportCSRResponseInitialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportCSRResponseInitialActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.importCSRResponse(false);
        this.panelCertificates.refreshData();
        this.panelCertificates.displayTrustAnchor();
    }//GEN-LAST:event_jMenuItemImportCSRResponseInitialActionPerformed

    private void jMenuItemFileEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFileEditActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.panelCertificates.renameSelectedAlias();
    }//GEN-LAST:event_jMenuItemFileEditActionPerformed

    private void jMenuItemFileDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFileDeleteActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.panelCertificates.deleteSelectedCertificate();
    }//GEN-LAST:event_jMenuItemFileDeleteActionPerformed

    private void jMenuItemFileCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFileCloseActionPerformed
        this.saveAndClose();
    }//GEN-LAST:event_jMenuItemFileCloseActionPerformed

    private void jMenuItemGenerateCSRRenewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenerateCSRRenewActionPerformed
        if (!isOperationAllowed()) {
            return;
        }
        this.generateCSR(false);
    }//GEN-LAST:event_jMenuItemGenerateCSRRenewActionPerformed

    private void jMenuItemImportCSRResponseRenewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportCSRResponseRenewActionPerformed
        this.importCSRResponse(true);
        this.panelCertificates.refreshData();
        this.panelCertificates.displayTrustAnchor();
    }//GEN-LAST:event_jMenuItemImportCSRResponseRenewActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDeleteCertificate;
    private javax.swing.JButton jButtonEditCertificate;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelKeystore;
    private javax.swing.JLabel jLabelModuleLockedWarning;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuExport;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuImport;
    private javax.swing.JMenuItem jMenuItemExportCertificate;
    private javax.swing.JMenuItem jMenuItemExportKeyPKCS12;
    private javax.swing.JMenuItem jMenuItemFileClose;
    private javax.swing.JMenuItem jMenuItemFileDelete;
    private javax.swing.JMenuItem jMenuItemFileEdit;
    private javax.swing.JMenuItem jMenuItemGenerateCSRInitial;
    private javax.swing.JMenuItem jMenuItemGenerateCSRRenew;
    private javax.swing.JMenuItem jMenuItemGenerateKey;
    private javax.swing.JMenuItem jMenuItemImportCSRResponseInitial;
    private javax.swing.JMenuItem jMenuItemImportCSRResponseRenew;
    private javax.swing.JMenuItem jMenuItemImportCertificate;
    private javax.swing.JMenuItem jMenuItemImportKeyJKS;
    private javax.swing.JMenuItem jMenuItemImportKeyPEM;
    private javax.swing.JMenuItem jMenuItemImportKeyPKCS12;
    private javax.swing.JMenu jMenuTools;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JPanel jPanelCertificatesMain;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelModuleLockWarning;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTextField jTextFieldCertFileInfo;
    private javax.swing.JToolBar jToolBar;
    // End of variables declaration//GEN-END:variables

    /**
     * Let this class listen to the underlaying table liste events, makes it a
     * ListSelectionListener
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.setButtonState();
    }
}
