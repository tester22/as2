//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/datasheet/gui/JDialogCreateDataSheet.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.datasheet.gui;

import de.mendelson.comm.as2.client.AS2StatusBar;
import de.mendelson.comm.as2.client.ListCellRendererEncryption;
import de.mendelson.comm.as2.client.ListCellRendererSignature;
import de.mendelson.comm.as2.datasheet.DatasheetBuilder;
import de.mendelson.comm.as2.datasheet.DatasheetInformation;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.ResourceBundleAS2Message;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerCertificateInformation;
import de.mendelson.comm.as2.partner.clientserver.PartnerListRequest;
import de.mendelson.comm.as2.partner.clientserver.PartnerListResponse;
import de.mendelson.comm.as2.partner.gui.ListCellRendererPartner;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.clients.preferences.PreferencesClient;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.CertificateManager;
import java.awt.Desktop;
import java.io.File;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.apache.commons.io.FileUtils;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Winzard to create a PDF that contains a data sheet
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class JDialogCreateDataSheet extends JDialog {

    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private AS2StatusBar statusbar;
    private MecResourceBundle rb;
    private Partner localStation = null;
    private CertificateManager certificateManagerEncSign;
    private CertificateManager certificateManagerSSL;
    private PreferencesClient preferenceClient;

    /**
     * Creates new form JDialogCreateDataSheet
     */
    public JDialogCreateDataSheet(JFrame parent, BaseClient baseClient,
            AS2StatusBar statusbar, CertificateManager certificateManagerEncSign,
            CertificateManager certificateManagerSSL) {
        super(parent, true);
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCreateDataSheet.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }        
        this.certificateManagerEncSign = certificateManagerEncSign;
        this.certificateManagerSSL = certificateManagerSSL;
        this.statusbar = statusbar;
        this.setTitle(this.rb.getResourceString("title"));
        initComponents();
        PartnerListResponse response = (PartnerListResponse) baseClient.sendSync(new PartnerListRequest(PartnerListRequest.LIST_ALL));
        List<Partner> partnerList = response.getList();
        this.jComboBoxRemotePartner.addItem(this.rb.getResourceString("label.newpartner"));
        for (Partner partner : partnerList) {
            if (partner.isLocalStation()) {
                this.jComboBoxLocalPartner.addItem(partner);
                if (partner.isLocalStation()) {
                    this.localStation = partner;
                }
            } else {
                this.jComboBoxRemotePartner.addItem(partner);
            }
        }
        this.jComboBoxLocalPartner.setRenderer(new ListCellRendererPartner());
        this.jComboBoxRemotePartner.setRenderer(new ListCellRendererPartner());
        if (this.localStation == null) {
            throw new RuntimeException("JDialogCreateDataSheet: No local station defined, aborted.");
        }
        this.preferenceClient = new PreferencesClient(baseClient);
        this.jTextFieldReceiptURL.setText(this.preferenceClient.get(PreferencesAS2.DATASHEET_RECEIPT_URL));
        this.initializeComboboxes();
        this.setButtonState();
    }

    private void setButtonState() {
        this.jButtonOk.setEnabled(this.jTextFieldReceiptURL.getText().length() > 0);
    }

    private void initializeComboboxes() {
        this.jComboBoxEncryptionType.setRenderer(new ListCellRendererEncryption());
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_NONE));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_3DES));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_40));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_64));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_128));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC2_196));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_128));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_192));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_AES_256));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC4_40));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC4_56));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_RC4_128));
        this.jComboBoxEncryptionType.addItem(Integer.valueOf(AS2Message.ENCRYPTION_DES));
        
        this.jComboBoxSignType.setRenderer( new ListCellRendererSignature());
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_NONE));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA1));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_MD5));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA256));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA384));
        this.jComboBoxSignType.addItem(Integer.valueOf(AS2Message.SIGNATURE_SHA512));
        this.jComboBoxEncryptionType.setSelectedItem(Integer.valueOf(AS2Message.ENCRYPTION_3DES));
        this.jComboBoxSignType.setSelectedItem(Integer.valueOf(AS2Message.SIGNATURE_SHA1));
    }
    
    
    private void createPDF() {
        final String uniqueId = this.getClass().getName() + ".createPDF." + System.currentTimeMillis();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                //display wait indicator
                JDialogCreateDataSheet.this.statusbar.startProgressIndeterminate(JDialogCreateDataSheet.this.rb.getResourceString("progress"), uniqueId);
                try {
                    DatasheetInformation information = new DatasheetInformation();
                    Partner localPartner = (Partner) JDialogCreateDataSheet.this.jComboBoxLocalPartner.getSelectedItem();
                    Partner remotePartner = null;
                    if (JDialogCreateDataSheet.this.jComboBoxRemotePartner.getSelectedItem() != null
                            && JDialogCreateDataSheet.this.jComboBoxRemotePartner.getSelectedItem() instanceof Partner) {
                        remotePartner = (Partner) JDialogCreateDataSheet.this.jComboBoxRemotePartner.getSelectedItem();
                    }
                    information.setReceiptURL(JDialogCreateDataSheet.this.jTextFieldReceiptURL.getText());
                    information.setComment(JDialogCreateDataSheet.this.jTextAreaComment.getText());
                    information.setEncryption(((Integer)JDialogCreateDataSheet.this.jComboBoxEncryptionType.getSelectedItem()).intValue());
                    information.setSignature(((Integer)JDialogCreateDataSheet.this.jComboBoxSignType.getSelectedItem()).intValue());
                    information.setRequestSyncMDN(JDialogCreateDataSheet.this.jCheckBoxSyncMDN.isSelected());
                    information.setRequestSignedMDN(JDialogCreateDataSheet.this.jCheckBoxSignedMDN.isSelected());
                    information.setCompression(JDialogCreateDataSheet.this.jCheckBoxCompression.isSelected() ? AS2Message.COMPRESSION_ZLIB : AS2Message.COMPRESSION_NONE);
                    CertificateManager certificateManagerEncSign = JDialogCreateDataSheet.this.certificateManagerEncSign;
                    KeyStoreUtil keystoreUtil = new KeyStoreUtil();
                    PartnerCertificateInformation infoEncryption = localPartner.getCertificateInformation(PartnerCertificateInformation.CATEGORY_CRYPT, 1);
                    if (infoEncryption != null) {
                        String alias = certificateManagerEncSign.getAliasByFingerprint(infoEncryption.getFingerprintSHA1());
                        byte[] pkcs7 = keystoreUtil.exportX509Certificate(certificateManagerEncSign.getKeystore(), alias, "PKCS7");
                        information.setCertDecryptData(pkcs7);
                    }
                    PartnerCertificateInformation infoSignature = localPartner.getCertificateInformation(PartnerCertificateInformation.CATEGORY_SIGN, 1);
                    if (infoSignature != null) {
                        String alias = certificateManagerEncSign.getAliasByFingerprint(infoSignature.getFingerprintSHA1());
                        byte[] pkcs7 = keystoreUtil.exportX509Certificate(certificateManagerEncSign.getKeystore(), alias, "PKCS7");
                        information.setCertVerifySignature(pkcs7);
                    }
                    PartnerCertificateInformation infoSSL = localPartner.getCertificateInformation(PartnerCertificateInformation.CATEGORY_SSL, 1);
                    if (infoSSL != null) {
                        String alias = certificateManagerSSL.getAliasByFingerprint(infoSSL.getFingerprintSHA1());
                        byte[] pkcs7 = keystoreUtil.exportX509Certificate(certificateManagerSSL.getKeystore(), alias, "PKCS7");
                        information.setCertSSL(pkcs7);
                    }
                    DatasheetBuilder builder = new DatasheetBuilder(localPartner, remotePartner, information);
                    try {
                        //there is a lock on the created file - no idea how to remove it
                        File tempFile = AS2Tools.createTempFile("as2_datasheet_temp", ".pdf");
                        File outFile = AS2Tools.createTempFile("as2_datasheet", ".pdf");
                        builder.create(tempFile);
                        FileUtils.copyFile(tempFile, outFile);
                        JDialogCreateDataSheet.this.logger.info(JDialogCreateDataSheet.this.rb.getResourceString("file.written", outFile.getAbsolutePath()));
                        Desktop.getDesktop().open(outFile);
                    } catch (Exception e) {
                        JDialogCreateDataSheet.this.logger.warning("createPDF: " + e.getMessage());
                        e.printStackTrace();
                    }
                } catch (Throwable e) {
                    JDialogCreateDataSheet.this.logger.warning("createPDF: " + e.getMessage());
                } finally {
                    JDialogCreateDataSheet.this.statusbar.stopProgressIfExists(uniqueId);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelMain = new javax.swing.JPanel();
        jLabelLocalStation = new javax.swing.JLabel();
        jComboBoxLocalPartner = new javax.swing.JComboBox();
        jLabelRemotePartner = new javax.swing.JLabel();
        jComboBoxRemotePartner = new javax.swing.JComboBox();
        jLabelComment = new javax.swing.JLabel();
        jScrollPaneComment = new javax.swing.JScrollPane();
        jTextAreaComment = new javax.swing.JTextArea();
        jLabelReceiptURL = new javax.swing.JLabel();
        jTextFieldReceiptURL = new javax.swing.JTextField();
        jLabelInfo = new javax.swing.JLabel();
        jCheckBoxCompression = new javax.swing.JCheckBox();
        jCheckBoxSyncMDN = new javax.swing.JCheckBox();
        jCheckBoxSignedMDN = new javax.swing.JCheckBox();
        jComboBoxEncryptionType = new javax.swing.JComboBox();
        jComboBoxSignType = new javax.swing.JComboBox();
        jLabelEncryption = new javax.swing.JLabel();
        jLabelSignature = new javax.swing.JLabel();
        jPanelButton = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelMain.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelMain.setLayout(new java.awt.GridBagLayout());

        jLabelLocalStation.setText(this.rb.getResourceString( "label.localpartner"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMain.add(jLabelLocalStation, gridBagConstraints);

        jComboBoxLocalPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLocalPartnerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jComboBoxLocalPartner, gridBagConstraints);

        jLabelRemotePartner.setText(this.rb.getResourceString( "label.remotepartner"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMain.add(jLabelRemotePartner, gridBagConstraints);

        jComboBoxRemotePartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRemotePartnerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jComboBoxRemotePartner, gridBagConstraints);

        jLabelComment.setText(this.rb.getResourceString( "label.comment"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMain.add(jLabelComment, gridBagConstraints);

        jTextAreaComment.setColumns(20);
        jTextAreaComment.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTextAreaComment.setRows(5);
        jTextAreaComment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextAreaCommentKeyReleased(evt);
            }
        });
        jScrollPaneComment.setViewportView(jTextAreaComment);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 10);
        jPanelMain.add(jScrollPaneComment, gridBagConstraints);

        jLabelReceiptURL.setText(this.rb.getResourceString( "label.receipturl"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMain.add(jLabelReceiptURL, gridBagConstraints);

        jTextFieldReceiptURL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldReceiptURLKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelMain.add(jTextFieldReceiptURL, gridBagConstraints);

        jLabelInfo.setText(this.rb.getResourceString( "label.info"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        jPanelMain.add(jLabelInfo, gridBagConstraints);

        jCheckBoxCompression.setText(this.rb.getResourceString( "label.compression"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanelMain.add(jCheckBoxCompression, gridBagConstraints);

        jCheckBoxSyncMDN.setText(this.rb.getResourceString( "label.syncmdn"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanelMain.add(jCheckBoxSyncMDN, gridBagConstraints);

        jCheckBoxSignedMDN.setText(this.rb.getResourceString( "label.signedmdn"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 5);
        jPanelMain.add(jCheckBoxSignedMDN, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jComboBoxEncryptionType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jComboBoxSignType, gridBagConstraints);

        jLabelEncryption.setText(this.rb.getResourceString( "label.encryption")
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMain.add(jLabelEncryption, gridBagConstraints);

        jLabelSignature.setText(this.rb.getResourceString( "label.signature")
        );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelMain.add(jLabelSignature, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanelMain, gridBagConstraints);

        jPanelButton.setLayout(new java.awt.GridBagLayout());

        jButtonOk.setText(this.rb.getResourceString( "button.ok"));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelButton.add(jButtonOk, gridBagConstraints);

        jButtonCancel.setText(this.rb.getResourceString( "button.cancel"));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelButton.add(jButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jPanelButton, gridBagConstraints);

        setSize(new java.awt.Dimension(614, 613));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldReceiptURLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldReceiptURLKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextFieldReceiptURLKeyReleased

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible(false);
        //store the entered preferences
        this.preferenceClient.put(PreferencesAS2.DATASHEET_RECEIPT_URL, this.jTextFieldReceiptURL.getText());
        this.createPDF();
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jComboBoxLocalPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLocalPartnerActionPerformed
        this.localStation = (Partner) this.jComboBoxLocalPartner.getSelectedItem();
    }//GEN-LAST:event_jComboBoxLocalPartnerActionPerformed

    private void jTextAreaCommentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextAreaCommentKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextAreaCommentKeyReleased

    private void jComboBoxRemotePartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRemotePartnerActionPerformed
        Object remoteObject = this.jComboBoxRemotePartner.getSelectedItem();
        if (!(remoteObject instanceof Partner)) {
            this.jCheckBoxCompression.setEnabled(true);
            this.jCheckBoxCompression.setSelected(false);
            this.jCheckBoxSignedMDN.setEnabled(true);
            this.jCheckBoxSignedMDN.setSelected(false);
            this.jCheckBoxSyncMDN.setEnabled(true);
            this.jCheckBoxSyncMDN.setSelected(false);
            this.jComboBoxEncryptionType.setEnabled(true);
            this.jComboBoxEncryptionType.setSelectedItem(Integer.valueOf(AS2Message.ENCRYPTION_3DES));
            this.jComboBoxSignType.setEnabled(true);
            this.jComboBoxSignType.setSelectedItem(Integer.valueOf(AS2Message.SIGNATURE_SHA1));
        } else {
            Partner remotePartner = (Partner) remoteObject;
            this.jCheckBoxCompression.setEnabled(false);
            this.jCheckBoxCompression.setSelected(remotePartner.getCompressionType() != AS2Message.COMPRESSION_NONE);
            this.jCheckBoxSignedMDN.setEnabled(false);
            this.jCheckBoxSignedMDN.setSelected(remotePartner.isSignedMDN());
            this.jCheckBoxSyncMDN.setEnabled(false);
            this.jCheckBoxSyncMDN.setSelected(remotePartner.isSyncMDN());
            this.jComboBoxEncryptionType.setEnabled(false);
            this.jComboBoxEncryptionType.setSelectedItem(Integer.valueOf(remotePartner.getEncryptionType()));
            this.jComboBoxSignType.setEnabled(false);
            this.jComboBoxSignType.setSelectedItem(Integer.valueOf(remotePartner.getSignType()));
        }
    }//GEN-LAST:event_jComboBoxRemotePartnerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JCheckBox jCheckBoxCompression;
    private javax.swing.JCheckBox jCheckBoxSignedMDN;
    private javax.swing.JCheckBox jCheckBoxSyncMDN;
    private javax.swing.JComboBox jComboBoxEncryptionType;
    private javax.swing.JComboBox jComboBoxLocalPartner;
    private javax.swing.JComboBox jComboBoxRemotePartner;
    private javax.swing.JComboBox jComboBoxSignType;
    private javax.swing.JLabel jLabelComment;
    private javax.swing.JLabel jLabelEncryption;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelLocalStation;
    private javax.swing.JLabel jLabelReceiptURL;
    private javax.swing.JLabel jLabelRemotePartner;
    private javax.swing.JLabel jLabelSignature;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPaneComment;
    private javax.swing.JTextArea jTextAreaComment;
    private javax.swing.JTextField jTextFieldReceiptURL;
    // End of variables declaration//GEN-END:variables
}
