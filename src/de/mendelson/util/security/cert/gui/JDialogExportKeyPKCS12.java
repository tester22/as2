//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/JDialogExportKeyPKCS12.java,v 1.1 2015/01/06 11:07:58 heller Exp $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.MecFileChooser;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.BCCryptoHelper;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.PKCS122PKCS12;
import java.io.File;
import java.security.KeyStore;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Export a private key into a pkcs#12 keystore
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class JDialogExportKeyPKCS12 extends JDialog {

    /**ResourceBundle to localize the GUI*/
    private MecResourceBundle rb = null;
    private CertificateManager manager = null;
    private Logger logger = null;

    /** Creates new form JDialogPartnerConfig
     *@param manager Manager that handles the certificates
     */
    public JDialogExportKeyPKCS12(JFrame parent, Logger logger, CertificateManager manager,
            String selectedAlias) throws Exception {
        super(parent, true);
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleExportKeyPKCS12.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        this.logger = logger;
        this.setTitle(this.rb.getResourceString("title"));
        initComponents();
        this.manager = manager;
        this.populateAliasList(selectedAlias);
        this.getRootPane().setDefaultButton(this.jButtonOk);
    }

    private void populateAliasList(String preselection) throws Exception {
        this.jComboBoxAlias.removeAllItems();
        KeyStoreUtil util = new KeyStoreUtil();
        KeyStore sourceKeystore = this.manager.getKeystore();
        Vector<String> keyAliasesList = util.getKeyAliases(sourceKeystore);
        if (keyAliasesList.isEmpty()) {
            throw new Exception(this.rb.getResourceString("keystore.contains.nokeys"));
        } else {
            for (int i = 0; i < keyAliasesList.size(); i++) {
                this.jComboBoxAlias.addItem(keyAliasesList.get(i));
            }
            if (preselection != null) {
                this.jComboBoxAlias.setSelectedItem(preselection);
            } else {
                this.jComboBoxAlias.setSelectedItem(0);
            }
        }
    }

    /**Sets the ok and cancel buttons of this GUI*/
    private void setButtonState() {
        this.jButtonOk.setEnabled(this.jTextFieldExportPKCS12File.getText().length() > 0
                && this.jPasswordFieldPassphrase.getPassword().length > 0);
    }

    /**Finally import the key*/
    private void performExport() {
        try {
            KeyStoreUtil util = new KeyStoreUtil();
            KeyStore sourceKeystore = this.manager.getKeystore();
            Vector<String> keyAliasesList = util.getKeyAliases(sourceKeystore);
            String selectedAlias = (String) this.jComboBoxAlias.getSelectedItem();
            KeyStore targetKeystore = KeyStore.getInstance(BCCryptoHelper.KEYSTORE_PKCS12, "BC");
            util.loadKeyStore(targetKeystore, this.jTextFieldExportPKCS12File.getText(),
                    this.jPasswordFieldPassphrase.getPassword());
            PKCS122PKCS12 importer = new PKCS122PKCS12(this.logger);
            importer.setTargetKeyStore(targetKeystore, this.jPasswordFieldPassphrase.getPassword());
            importer.importKey(sourceKeystore, selectedAlias);
            util.saveKeyStore(targetKeystore, this.jPasswordFieldPassphrase.getPassword(), this.jTextFieldExportPKCS12File.getText());
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("key.export.success.message"),
                    this.rb.getResourceString("key.export.success.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            this.logger.fine(this.rb.getResourceString("key.exported.to.file",
                    new Object[]{
                        selectedAlias,
                        new File(this.jTextFieldExportPKCS12File.getText()).getAbsolutePath()
                    }));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("key.export.error.message", e.getMessage()),
                    this.rb.getResourceString("key.export.error.title"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelEdit = new javax.swing.JPanel();
        jLabelIcon = new javax.swing.JLabel();
        jLabelExportPKCS12File = new javax.swing.JLabel();
        jTextFieldExportPKCS12File = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButtonBrowseExportFile = new javax.swing.JButton();
        jLabelPassphrase = new javax.swing.JLabel();
        jPasswordFieldPassphrase = new javax.swing.JPasswordField();
        jComboBoxAlias = new javax.swing.JComboBox();
        jLabelAlias = new javax.swing.JLabel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelEdit.setLayout(new java.awt.GridBagLayout());

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/key32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelEdit.add(jLabelIcon, gridBagConstraints);

        jLabelExportPKCS12File.setText(this.rb.getResourceString( "label.exportkey"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jLabelExportPKCS12File, gridBagConstraints);

        jTextFieldExportPKCS12File.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldExportPKCS12FileKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jTextFieldExportPKCS12File, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelEdit.add(jPanel3, gridBagConstraints);

        jButtonBrowseExportFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/folder.gif"))); // NOI18N
        jButtonBrowseExportFile.setToolTipText(this.rb.getResourceString( "button.browse"));
        jButtonBrowseExportFile.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonBrowseExportFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseExportFileActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jButtonBrowseExportFile, gridBagConstraints);

        jLabelPassphrase.setText(this.rb.getResourceString( "label.keypass"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jLabelPassphrase, gridBagConstraints);

        jPasswordFieldPassphrase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldPassphraseKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jPasswordFieldPassphrase, gridBagConstraints);

        jComboBoxAlias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jComboBoxAlias, gridBagConstraints);

        jLabelAlias.setText(this.rb.getResourceString( "label.alias" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jLabelAlias, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanelEdit, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jButtonOk.setText(this.rb.getResourceString( "button.ok" ));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelButtons.add(jButtonOk, gridBagConstraints);

        jButtonCancel.setText(this.rb.getResourceString( "button.cancel" ));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelButtons.add(jButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanelButtons, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-466)/2, (screenSize.height-287)/2, 466, 287);
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordFieldPassphraseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldPassphraseKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jPasswordFieldPassphraseKeyReleased

    private void jButtonBrowseExportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseExportFileActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        MecFileChooser chooser = new MecFileChooser(
                parent,
                this.rb.getResourceString("filechooser.key.export"));
        chooser.browseFilename(this.jTextFieldExportPKCS12File);
    }//GEN-LAST:event_jButtonBrowseExportFileActionPerformed

    private void jTextFieldExportPKCS12FileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldExportPKCS12FileKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextFieldExportPKCS12FileKeyReleased

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible(false);
        this.performExport();
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBrowseExportFile;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JComboBox jComboBoxAlias;
    private javax.swing.JLabel jLabelAlias;
    private javax.swing.JLabel jLabelExportPKCS12File;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelPassphrase;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelEdit;
    private javax.swing.JPasswordField jPasswordFieldPassphrase;
    private javax.swing.JTextField jTextFieldExportPKCS12File;
    // End of variables declaration//GEN-END:variables
}
