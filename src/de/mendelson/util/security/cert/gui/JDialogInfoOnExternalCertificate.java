//$Header: /cvsroot/mec-as2/b47/de/mendelson/util/security/cert/gui/JDialogInfoOnExternalCertificate.java,v 1.1 2015/01/06 11:07:58 heller Exp $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.io.File;
import java.io.FileInputStream;
import java.security.Provider;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Dialog to display information about certificates - either in an external file or a passed object
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class JDialogInfoOnExternalCertificate extends JDialog {

    /**
     * ResourceBundle to localize the GUI
     */
    private MecResourceBundle rb = null;
    private boolean certificateIsOk = true;
    private boolean importPressed = false;
    private List<String> infoTextList = new ArrayList<String>();
    private int certificateIndex = 0;

    /**
     * Creates new form JDialogPartnerConfig
     *
     * @param parameter Parameter to edit, null for a new one
     * @param parameterList List of available parameter
     */
    public JDialogInfoOnExternalCertificate(JFrame parent, File certFile) {
        super(parent, true);
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleInfoOnExternalCertificate.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        initComponents();
        this.getRootPane().setDefaultButton(this.jButtonImport);
        this.infoTextList = this.getInfo(certFile);
        if (this.infoTextList.size() == 1) {
            this.setTitle(this.rb.getResourceString("title.single"));
        } else {
            this.setTitle(this.rb.getResourceString("title.multiple"));
        }
        this.displayCertificateInformation();
        this.setButtonState();
    }

    /**
     * Creates new form JDialogPartnerConfig
     *
     * @param parameter Parameter to edit, null for a new one
     * @param parameterList List of available parameter
     */
    public JDialogInfoOnExternalCertificate(JFrame parent, List<X509Certificate> certs) {
        super(parent, true);
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleInfoOnExternalCertificate.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        initComponents();
        this.getRootPane().setDefaultButton(this.jButtonImport);
        this.infoTextList = this.getInfo(certs);
        if (this.infoTextList.size() == 1) {
            this.setTitle(this.rb.getResourceString("title.single"));
        } else {
            this.setTitle(this.rb.getResourceString("title.multiple"));
        }
        this.displayCertificateInformation();
        this.setButtonState();
    }
    
    private void displayCertificateInformation() {
        this.jTextAreaInfo.setText(this.infoTextList.get(this.getCertificateIndex()));
        if (this.infoTextList.size() > 1) {
            this.jLabelIcon.setText(this.rb.getResourceString("certinfo.index",
                    new Object[]{String.valueOf(this.getCertificateIndex() + 1),
                        String.valueOf(this.infoTextList.size())}));
        }
    }

    private List<String> getInfo(List<X509Certificate> certList) {
        List<String> infoList = new ArrayList<String>();
        for (int i = 0; i < certList.size(); i++) {
            StringBuilder infoText = new StringBuilder();
            X509Certificate cert = (X509Certificate) certList.get(i);            
            KeystoreCertificate keystoreCert = new KeystoreCertificate();
            keystoreCert.setCertificate(cert);
            infoText.append(keystoreCert.getInfo());
            infoList.add(infoText.toString());
        }
        return( infoList );
    }

    private List<String> getInfo(File certFile) {
        List<String> infoList = null;
        FileInputStream inStream = null;
        try {
            Provider provBC = new BouncyCastleProvider();
            inStream = new FileInputStream(certFile);
            KeyStoreUtil util = new KeyStoreUtil();
            List<X509Certificate> certList = util.readCertificates(inStream, provBC);     
            infoList = this.getInfo(certList);
            //add file info to info text
            StringBuilder fileInfoText = new StringBuilder();
            fileInfoText.append(this.rb.getResourceString("certinfo.certfile", certFile.getAbsolutePath()));
            fileInfoText.append("\n---\n");            
            for( int i = 0; i < infoList.size(); i++ ){
                infoList.set(i, fileInfoText.toString() + infoList.get(i));
            }
        } catch (Exception e) {
            this.certificateIsOk = false;
            infoList.clear();
            infoList.add(e.getMessage());
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (Exception e) {
                //nop
            }
        }
        return (infoList);
    }

    /**
     * Sets the ok and cancel buttons of this GUI
     */
    private void setButtonState() {
        this.jButtonImport.setEnabled(this.certificateIsOk);
        this.jButtonIndexUp.setVisible(this.infoTextList.size() > 1);
        this.jButtonIndexDown.setVisible(this.infoTextList.size() > 1);
        this.jButtonIndexDown.setEnabled(this.getCertificateIndex() > 0);
        this.jButtonIndexUp.setEnabled(this.getCertificateIndex() < this.infoTextList.size() - 1);
    }

    public boolean importPressed() {
        return (this.importPressed);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelEdit = new javax.swing.JPanel();
        jScrollPaneInfo = new javax.swing.JScrollPane();
        jTextAreaInfo = new javax.swing.JTextArea();
        jPanelHeader = new javax.swing.JPanel();
        jButtonIndexDown = new javax.swing.JButton();
        jButtonIndexUp = new javax.swing.JButton();
        jLabelIcon = new javax.swing.JLabel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonImport = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelEdit.setLayout(new java.awt.GridBagLayout());

        jTextAreaInfo.setColumns(20);
        jTextAreaInfo.setEditable(false);
        jTextAreaInfo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextAreaInfo.setRows(5);
        jScrollPaneInfo.setViewportView(jTextAreaInfo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelEdit.add(jScrollPaneInfo, gridBagConstraints);

        jPanelHeader.setLayout(new java.awt.GridBagLayout());

        jButtonIndexDown.setText("<<");
        jButtonIndexDown.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonIndexDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIndexDownActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHeader.add(jButtonIndexDown, gridBagConstraints);

        jButtonIndexUp.setText(">>");
        jButtonIndexUp.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonIndexUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIndexUpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelHeader.add(jButtonIndexUp, gridBagConstraints);

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelHeader.add(jLabelIcon, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanelEdit.add(jPanelHeader, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanelEdit, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jButtonImport.setText(this.rb.getResourceString( "button.ok" ));
        jButtonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelButtons.add(jButtonImport, gridBagConstraints);

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

        setSize(new java.awt.Dimension(620, 426));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportActionPerformed
        this.importPressed = true;
        this.setVisible(false);
    }//GEN-LAST:event_jButtonImportActionPerformed

    private void jButtonIndexDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIndexDownActionPerformed
        if (this.getCertificateIndex() > 0) {
            this.certificateIndex--;
            this.displayCertificateInformation();
            this.setButtonState();
        }
    }//GEN-LAST:event_jButtonIndexDownActionPerformed

    private void jButtonIndexUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIndexUpActionPerformed
        if (this.getCertificateIndex() < this.infoTextList.size() - 1) {
            this.certificateIndex++;
            this.displayCertificateInformation();
            this.setButtonState();
        }
    }//GEN-LAST:event_jButtonIndexUpActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonImport;
    private javax.swing.JButton jButtonIndexDown;
    private javax.swing.JButton jButtonIndexUp;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelEdit;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JScrollPane jScrollPaneInfo;
    private javax.swing.JTextArea jTextAreaInfo;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the certificateIndex
     */
    public int getCertificateIndex() {
        return certificateIndex;
    }
}
