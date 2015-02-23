//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/JPanelCertificates.java,v 1.1 2015/01/06 11:07:59 heller Exp $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.ImageUtil;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.AllowModificationCallback;
import de.mendelson.util.security.cert.CertificateInUseChecker;
import de.mendelson.util.security.cert.CertificateInUseInfo;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.TableModelCertificates;
import de.mendelson.util.tables.JTableColumnResizer;
import de.mendelson.util.tables.TableCellRendererDate;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */

/**
 * Panel to configure the Certificates
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class JPanelCertificates extends JPanel implements ListSelectionListener, PopupMenuListener {

    private Logger logger = null;
    /**
     * Title used to render subdialogs
     */
    private String title = null;
    private JButton editButton = null;
    private JButton deleteButton = null;
    private JMenuItem itemEdit = null;
    private JMenuItem itemDelete = null;
    private CertificateManager manager = null;
    private String keystoreType;
    private MecResourceBundle rb = null;
    public static final ImageIcon ICON_CERTIFICATE_ROOT = TableModelCertificates.ICON_CERTIFICATE_ROOT;
    public static final ImageIcon ICON_CERTIFICATE_UNTRUSTED = new ImageIcon(JPanelCertificates.class.getResource(
            "/de/mendelson/util/security/cert/gui/cert_untrusted16x16.gif"));
    private final List<CertificateInUseChecker> inUseChecker = Collections.synchronizedList(new ArrayList<CertificateInUseChecker>());
    private List<AllowModificationCallback> allowModificationCallbackList = new ArrayList<AllowModificationCallback>();

    /**
     * Creates new form JPanelPartnerConfig
     */
    public JPanelCertificates(Logger logger, ListSelectionListener additionalListener) {
        this.logger = logger;
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCertificates.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        initComponents();
        //add row sorter
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.jTable.getModel());
        this.jTable.setRowSorter(sorter);
        this.jTable.getTableHeader().setReorderingAllowed(false);
        this.jTable.getColumnModel().getColumn(0).setMaxWidth(20);
        this.jTable.getColumnModel().getColumn(1).setMaxWidth(20);
        this.jTable.getSelectionModel().addListSelectionListener(additionalListener);
        this.jTable.getSelectionModel().addListSelectionListener(this);
        this.jTable.setDefaultRenderer(Date.class, new TableCellRendererDate(DateFormat.getDateInstance(DateFormat.SHORT)));
        this.jPopupMenu.setInvoker(this.jScrollPaneTable);
        this.jPopupMenu.addPopupMenuListener(this);
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
        this.jMenuItemPopupRenameAlias.setIcon(iconMixedEdit);
        this.jMenuItemPopupDeleteEntry.setIcon(iconMixedDelete);

    }

    /**
     * Adds a callback that is called if a user tries to modify the
     * configuration A modification will be prevented if one of the callbacks
     * does not allow it
     */
    public void addAllowModificationCallback(AllowModificationCallback callback) {
        this.allowModificationCallbackList.add(callback);
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

    public void addKeystore(CertificateManager manager) {
        this.manager = manager;
        this.keystoreType = manager.getKeystoreType();
        this.refreshData();
        JTableColumnResizer.adjustColumnWidthByContent(this.jTable);
        if (this.jTable.getRowCount() > 0) {
            this.jTable.getSelectionModel().setSelectionInterval(0, 0);
        }
    }

    private boolean keystoreIsReadonly() {
        return (!this.manager.canWrite());
    }

    public void addCertificateInUseChecker(CertificateInUseChecker checker) {
        synchronized (this.inUseChecker) {
            this.inUseChecker.add(checker);
        }
    }

    public void setSelectionByAlias(String selectedAlias) {
        if (selectedAlias != null) {
            for (int i = 0; i < ((TableModelCertificates) this.jTable.getModel()).getRowCount(); i++) {
                KeystoreCertificate certificate = ((TableModelCertificates) this.jTable.getModel()).getParameter(i);
                if (certificate.getAlias().equals(selectedAlias)) {
                    this.jTable.getSelectionModel().setSelectionInterval(i, i);
                    break;
                }
            }
        }
    }

    /**
     * Returns a single certificate of a row of the embedded table
     */
    public KeystoreCertificate getSelectedCertificate() {
        int row = this.jTable.getSelectedRow();
        if (row < 0) {
            return (null);
        }
        return (((TableModelCertificates) this.jTable.getModel()).getParameter(row));
    }

    /**
     * Returns the actual selected row
     */
    public int getSelectedRow() {
        return (this.jTable.getSelectedRow());
    }

    public void refreshData() {
        //try to keep the mark
        int selectedRow = this.jTable.getSelectedRow();
        String selectedAlias = null;
        if (selectedRow >= 0) {
            selectedAlias = (((TableModelCertificates) this.jTable.getModel()).getParameter(selectedRow)).getAlias();
        }
        if (this.jCheckBoxShowRootCertificates.isSelected()) {
            ((TableModelCertificates) this.jTable.getModel()).setNewData(this.manager.getKeyStoreCertificateList());
        } else {
            List<KeystoreCertificate> keystoreCertList = new Vector<KeystoreCertificate>();
            List<KeystoreCertificate> keystoreCertListAll = this.manager.getKeyStoreCertificateList();
            for (KeystoreCertificate cert : keystoreCertListAll) {
                if (!cert.isRootCertificate()) {
                    keystoreCertList.add(cert);
                }
            }
            ((TableModelCertificates) this.jTable.getModel()).setNewData(keystoreCertList);
        }
        for (int i = 0, rowCount = this.jTable.getRowCount(); i < rowCount; i++) {
            KeystoreCertificate cert = ((TableModelCertificates) this.jTable.getModel()).getParameter(i);
            if (cert.getAlias().equals(selectedAlias)) {
                this.jTable.getSelectionModel().setSelectionInterval(i, i);
                break;
            }
        }
    }

    /**
     * Lets this gui refresh the table
     */
    public void certificateDeleted(int lastRow) {
        //last row? dec
        if (lastRow > this.jTable.getRowCount() - 1 && lastRow != 0) {
            lastRow--;
        }
        if (this.jTable.getRowCount() > 0) {
            this.jTable.getSelectionModel().setSelectionInterval(lastRow, lastRow);
        }
    }

    /**
     * Lets this gui refresh the table
     */
    public void certificateAdded(String newAlias) {
        if (newAlias == null) {
            return;
        }
        for (int i = 0, rowCount = this.jTable.getRowCount(); i < rowCount; i++) {
            KeystoreCertificate cert = ((TableModelCertificates) this.jTable.getModel()).getParameter(i);
            if (cert.getAlias().equals(newAlias)) {
                this.jTable.getSelectionModel().setSelectionInterval(i, i);
                break;
            }
        }
    }

    /**
     * Lets this gui refresh the table
     */
    public void certificateRenamedTo(String newAlias) {
        if (newAlias == null) {
            return;
        }
        for (int i = 0, rowCount = this.jTable.getRowCount(); i < rowCount; i++) {
            KeystoreCertificate cert = ((TableModelCertificates) this.jTable.getModel()).getParameter(i);
            if (cert.getAlias().equals(newAlias)) {
                this.jTable.getSelectionModel().setSelectionInterval(i, i);
                break;
            }
        }
    }

    /**
     * Allows the GUI to control the passed buttons
     */
    public void setButtons(JButton editButton, JButton deleteButton) {
        this.editButton = editButton;
        this.deleteButton = deleteButton;
        this.setButtonState();
    }

    public void setMenuItems(JMenuItem itemEdit, JMenuItem itemDelete) {
        this.itemEdit = itemEdit;
        this.itemDelete = itemDelete;
        this.setButtonState();
    }

    /**
     * Control the state of the panels buttons
     */
    private void setButtonState() {
        if (this.deleteButton != null) {
            this.deleteButton.setEnabled(this.jTable.getSelectedRow() >= 0);
        }
        if (this.editButton != null) {
            this.editButton.setEnabled(this.jTable.getSelectedRow() >= 0);
        }
        if (this.itemEdit != null) {
            this.itemEdit.setEnabled(this.jTable.getSelectedRow() >= 0);
        }
        if (this.itemDelete != null) {
            this.itemDelete.setEnabled(this.jTable.getSelectedRow() >= 0);
        }
    }

    /**
     * Makes this a listSelection Listener
     */
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        int selectedRow = this.jTable.getSelectedRow();
        if (selectedRow >= 0) {
            KeystoreCertificate certificate = ((TableModelCertificates) this.jTable.getModel()).getParameter(selectedRow);
            String infoText = certificate.getInfo();
            String extensionText = certificate.getInfoExtension();
            this.jTextAreaInfo.setText(infoText);
            this.jTextAreaInfoExtension.setText(extensionText);
            this.displayTrustAnchor();
        }
        this.setButtonState();
    }

    public void displayTrustAnchor() {
        int selectedRow = this.jTable.getSelectedRow();
        if (selectedRow >= 0) {
            KeystoreCertificate certificate = ((TableModelCertificates) this.jTable.getModel()).getParameter(selectedRow);
            if (certificate.isRootCertificate()) {
                this.jLabelTrustAnchorValue.setIcon(ICON_CERTIFICATE_ROOT);
                this.jLabelTrustAnchorValue.setText("Root certificate");
            } else if (certificate.isSelfSigned()) {
                //figure out the icon used to render the cert entry in the table
                this.jLabelTrustAnchorValue.setIcon(((TableModelCertificates) this.jTable.getModel()).getIconForCertificate(certificate));
                this.jLabelTrustAnchorValue.setText("Self signed");
            } else {
                PKIXCertPathBuilderResult result = certificate.getPKIXCertPathBuilderResult(this.manager.getKeystore(), this.manager.getX509CertificateList());
                if (result == null) {
                    this.jLabelTrustAnchorValue.setIcon(ICON_CERTIFICATE_UNTRUSTED);
                    this.jLabelTrustAnchorValue.setText("Untrusted");
                } else {
                    TrustAnchor anchor = result.getTrustAnchor();
                    if (anchor == null) {
                        this.jLabelTrustAnchorValue.setIcon(ICON_CERTIFICATE_UNTRUSTED);
                        this.jLabelTrustAnchorValue.setText("Untrusted");
                    } else {
                        //found a root in the cert path
                        this.jLabelTrustAnchorValue.setIcon(ICON_CERTIFICATE_ROOT);
                        List<KeystoreCertificate> availableCerts = this.manager.getKeyStoreCertificateList();
                        for (KeystoreCertificate foundCert : availableCerts) {
                            if (foundCert.getX509Certificate().equals(anchor.getTrustedCert())) {
                                this.jLabelTrustAnchorValue.setText(foundCert.getAlias());
                            }
                        }
                    }
                }
            }
        } else {
            this.jLabelTrustAnchorValue.setIcon(null);
            this.jLabelTrustAnchorValue.setText("--");
        }
    }

    public void performDeleteParameter() {
        try {
            KeystoreCertificate certificate = ((TableModelCertificates) this.jTable.getModel()).getParameter(this.jTable.getSelectedRow());
            this.manager.deleteKeystoreEntry(certificate.getAlias());
            this.manager.saveKeystore();
            int selectedRow = this.jTable.getSelectedRow();
            this.refreshData();
            if (this.jTable.getRowCount() - 1 >= selectedRow) {
                this.jTable.getSelectionModel().setSelectionInterval(
                        selectedRow, selectedRow);
            } else if (this.jTable.getRowCount() > 0) {
                this.jTable.getSelectionModel().setSelectionInterval(
                        this.jTable.getRowCount() - 1, this.jTable.getRowCount() - 1);
            }
        } catch (Throwable e) {
            this.logger.severe(e.getMessage());
        }
    }

    /**
     * Renames the selected alias
     */
    public void performEditParameter() {
        try {
            KeystoreCertificate certificate = ((TableModelCertificates) this.jTable.getModel()).getParameter(this.jTable.getSelectedRow());
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(
                    JFrame.class, this);
            JDialogRenameEntry dialog = new JDialogRenameEntry(parent, this.manager, certificate.getAlias(), this.keystoreType);
            dialog.setVisible(true);
            this.manager.saveKeystore();
            this.refreshData();
        } catch (Throwable e) {
            this.logger.severe(e.getMessage());
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void renameSelectedAlias() {
        KeystoreCertificate selectedCertificate = this.getSelectedCertificate();
        String oldAlias = selectedCertificate.getAlias();
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        JDialogRenameEntry dialog = new JDialogRenameEntry(parent, this.manager, oldAlias, this.keystoreType);
        dialog.setVisible(true);
        String newAlias = dialog.getNewAlias();
        dialog.dispose();
        this.refreshData();
        this.certificateRenamedTo(newAlias);
    }

    public void deleteSelectedCertificate() {
        KeystoreCertificate selectedCertificate = this.getSelectedCertificate();
        StringBuilder builder = new StringBuilder();
        synchronized (this.inUseChecker) {
            for (CertificateInUseChecker checker : this.inUseChecker) {
                for (CertificateInUseInfo singleInfo : checker.checkUsed(selectedCertificate)) {
                    if (builder.length() > 0) {
                        builder.append("\n");
                    }
                    builder.append(singleInfo.getMessage());
                }
            }
        }
        if (builder.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    this.rb.getResourceString("cert.delete.impossible")
                    + "\n\n" + builder.toString(),
                    this.rb.getResourceString("title.cert.in.use"),
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        //ask the user if the cert should be really deleted, all data is lost
        int requestValue = JOptionPane.showConfirmDialog(
                this, this.rb.getResourceString("dialog.cert.delete.message", selectedCertificate.getAlias()),
                this.rb.getResourceString("dialog.cert.delete.title"),
                JOptionPane.YES_NO_OPTION);
        if (requestValue != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            int selectedRow = this.getSelectedRow();
            this.manager.deleteKeystoreEntry(selectedCertificate.getAlias());
            this.manager.saveKeystore();
            this.refreshData();
            this.certificateDeleted(selectedRow);
        } catch (Throwable e) {
            this.logger.warning(e.getMessage());
        }
    }

    /**
     * Exports a selected certificate
     */
    public void exportSelectedCertificate() {
        KeystoreCertificate selectedCertificate = this.getSelectedCertificate();
        if (selectedCertificate != null) {
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
            JDialogExportCertificate dialog = new JDialogExportCertificate(parent, this.manager,
                    selectedCertificate.getAlias());
            dialog.setVisible(true);
        }
    }

    /**
     * Exports a key to a pkcs12 keystore
     */
    public void exportPKCS12Key() {
        KeystoreCertificate selectedCertificate = this.getSelectedCertificate();
        String preselectionAlias = selectedCertificate == null ? null : selectedCertificate.getAlias();
        try {
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
            JDialogExportKeyPKCS12 dialog = new JDialogExportKeyPKCS12(parent, this.logger, this.manager,
                    preselectionAlias);
            dialog.setVisible(true);
        } catch (Exception e) {
            this.logger.severe(e.getMessage());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu = new javax.swing.JPopupMenu();
        jMenuItemPopupExportKey = new javax.swing.JMenuItem();
        jMenuItemPopupExportCert = new javax.swing.JMenuItem();
        jMenuItemPopupRenameAlias = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemPopupDeleteEntry = new javax.swing.JMenuItem();
        jSplitPane = new javax.swing.JSplitPane();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jTable = new de.mendelson.util.tables.JTableSortable();
        jTabbedPaneInfo = new javax.swing.JTabbedPane();
        jScrollPaneInfo = new javax.swing.JScrollPane();
        jTextAreaInfo = new javax.swing.JTextArea();
        jScrollPaneInfoExtension = new javax.swing.JScrollPane();
        jTextAreaInfoExtension = new javax.swing.JTextArea();
        jCheckBoxShowRootCertificates = new javax.swing.JCheckBox();
        jLabelTrustAnchor = new javax.swing.JLabel();
        jLabelTrustAnchorValue = new javax.swing.JLabel();

        jMenuItemPopupExportKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/key16x16.gif"))); // NOI18N
        jMenuItemPopupExportKey.setText(this.rb.getResourceString("label.key.export.pkcs12"));
        jMenuItemPopupExportKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPopupExportKeyActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuItemPopupExportKey);

        jMenuItemPopupExportCert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jMenuItemPopupExportCert.setText(this.rb.getResourceString("label.cert.export"));
        jMenuItemPopupExportCert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPopupExportCertActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuItemPopupExportCert);

        jMenuItemPopupRenameAlias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jMenuItemPopupRenameAlias.setText(this.rb.getResourceString("button.edit"));
        jMenuItemPopupRenameAlias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPopupRenameAliasActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuItemPopupRenameAlias);
        jPopupMenu.add(jSeparator1);

        jMenuItemPopupDeleteEntry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/certificate16x16.gif"))); // NOI18N
        jMenuItemPopupDeleteEntry.setText(this.rb.getResourceString( "button.delete"));
        jMenuItemPopupDeleteEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPopupDeleteEntryActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuItemPopupDeleteEntry);

        setLayout(new java.awt.GridBagLayout());

        jSplitPane.setDividerLocation(200);
        jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTable.setModel(new TableModelCertificates());
        jTable.setShowHorizontalLines(false);
        jTable.setShowVerticalLines(false);
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPaneTable.setViewportView(jTable);

        jSplitPane.setLeftComponent(jScrollPaneTable);

        jTextAreaInfo.setEditable(false);
        jTextAreaInfo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextAreaInfo.setLineWrap(true);
        jTextAreaInfo.setWrapStyleWord(true);
        jScrollPaneInfo.setViewportView(jTextAreaInfo);

        jTabbedPaneInfo.addTab(this.rb.getResourceString( "tab.info.basic" ), jScrollPaneInfo);

        jTextAreaInfoExtension.setEditable(false);
        jTextAreaInfoExtension.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextAreaInfoExtension.setLineWrap(true);
        jTextAreaInfoExtension.setWrapStyleWord(true);
        jScrollPaneInfoExtension.setViewportView(jTextAreaInfoExtension);

        jTabbedPaneInfo.addTab(this.rb.getResourceString( "tab.info.extension" ), jScrollPaneInfoExtension);

        jSplitPane.setRightComponent(jTabbedPaneInfo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jSplitPane, gridBagConstraints);

        jCheckBoxShowRootCertificates.setText(this.rb.getResourceString( "display.root.certs"));
        jCheckBoxShowRootCertificates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowRootCertificatesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        add(jCheckBoxShowRootCertificates, gridBagConstraints);

        jLabelTrustAnchor.setText("Trust anchor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        add(jLabelTrustAnchor, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabelTrustAnchorValue, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void jCheckBoxShowRootCertificatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowRootCertificatesActionPerformed
    this.refreshData();
}//GEN-LAST:event_jCheckBoxShowRootCertificatesActionPerformed

private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
    if (evt.isPopupTrigger() || evt.isMetaDown()) {
        if (this.jTable.getSelectedRowCount() == 0) {
            return;
        }
        this.jPopupMenu.show(evt.getComponent(), evt.getX(),
                evt.getY());
    }
}//GEN-LAST:event_jTableMouseClicked

private void jMenuItemPopupDeleteEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPopupDeleteEntryActionPerformed
    if (!this.isOperationAllowed()) {
        return;
    }
    this.deleteSelectedCertificate();
}//GEN-LAST:event_jMenuItemPopupDeleteEntryActionPerformed

private void jMenuItemPopupRenameAliasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPopupRenameAliasActionPerformed
    if (!this.isOperationAllowed()) {
        return;
    }
    this.renameSelectedAlias();
}//GEN-LAST:event_jMenuItemPopupRenameAliasActionPerformed

private void jMenuItemPopupExportKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPopupExportKeyActionPerformed
    if (!this.isOperationAllowed()) {
        return;
    }
    this.exportPKCS12Key();
}//GEN-LAST:event_jMenuItemPopupExportKeyActionPerformed

private void jMenuItemPopupExportCertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPopupExportCertActionPerformed
    if (!this.isOperationAllowed()) {
        return;
    }
    this.exportSelectedCertificate();
}//GEN-LAST:event_jMenuItemPopupExportCertActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxShowRootCertificates;
    private javax.swing.JLabel jLabelTrustAnchor;
    private javax.swing.JLabel jLabelTrustAnchorValue;
    private javax.swing.JMenuItem jMenuItemPopupDeleteEntry;
    private javax.swing.JMenuItem jMenuItemPopupExportCert;
    private javax.swing.JMenuItem jMenuItemPopupExportKey;
    private javax.swing.JMenuItem jMenuItemPopupRenameAlias;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPaneInfo;
    private javax.swing.JScrollPane jScrollPaneInfoExtension;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JTabbedPane jTabbedPaneInfo;
    private de.mendelson.util.tables.JTableSortable jTable;
    private javax.swing.JTextArea jTextAreaInfo;
    private javax.swing.JTextArea jTextAreaInfoExtension;
    // End of variables declaration//GEN-END:variables

    /**
     * Makes this a popup menu listener
     */
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        if (e.getSource() == this.jPopupMenu) {
            KeystoreCertificate selectedCertificate = this.getSelectedCertificate();
            this.jMenuItemPopupExportCert.setEnabled(selectedCertificate != null);
            this.jMenuItemPopupExportKey.setEnabled(selectedCertificate != null
                    && selectedCertificate.getIsKeyPair());
            //later enhancement possible here
            this.jMenuItemPopupDeleteEntry.setEnabled(!this.keystoreIsReadonly());
            this.jMenuItemPopupRenameAlias.setEnabled(!this.keystoreIsReadonly());
        }
    }

    /**
     * Makes this a popup menu listener
     */
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    }

    /**
     * Makes this a popup menu listener
     */
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
    }
}
