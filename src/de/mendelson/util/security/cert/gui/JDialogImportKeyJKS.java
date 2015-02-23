//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/JDialogImportKeyJKS.java,v 1.1 2015/01/06 11:07:58 heller Exp $
package de.mendelson.util.security.cert.gui;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.MecFileChooser;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.JKSKeys2PKCS12;
import de.mendelson.util.security.KeyStoreUtil;
import java.security.KeyStore;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Import a JKS key into the PCKS#12 keystore
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class JDialogImportKeyJKS extends JDialog {
    
    /**ResourceBundle to localize the GUI*/
    private MecResourceBundle rb = null;    
    private CertificateManager manager = null;    
    private String newAlias = null;
    private Logger logger = null;
    
    /** Creates new form JDialogPartnerConfig
     *@param manager Manager that handles the certificates
     */
    public JDialogImportKeyJKS(JFrame parent, Logger logger, CertificateManager manager ) {
        super( parent, true );
        //load resource bundle
        try{
            this.rb = (MecResourceBundle)ResourceBundle.getBundle(
                    ResourceBundleImportKeyJKS.class.getName());
        } catch ( MissingResourceException e ) {
            throw new RuntimeException( "Oops..resource bundle "
                    + e.getClassName() + " not found." );
        }
        this.setTitle( this.rb.getResourceString( "title" ));
        initComponents();
        this.logger = logger;
        this.manager = manager;
        this.getRootPane().setDefaultButton( this.jButtonOk );
    }
    
    public String getNewAlias(){
        return( this.newAlias );
    }
    
    /**Sets the ok and cancel buttons of this GUI*/
    private void setButtonState(){
        this.jButtonOk.setEnabled( this.jTextFieldImportJKSFile.getText().length() > 0
                && this.jPasswordFieldPassphrase.getPassword().length > 0 );
    }
    
    /**Finally import the key*/
    private void performImport(){
        JFrame parent = (JFrame)SwingUtilities.getAncestorOfClass( JFrame.class, this );
        try{
            KeyStoreUtil util = new KeyStoreUtil();            
            KeyStore sourceKeystore = KeyStore.getInstance( "JKS", "SUN" );
            util.loadKeyStore( sourceKeystore, this.jTextFieldImportJKSFile.getText(), 
                    this.jPasswordFieldPassphrase.getPassword());
            Vector<String> keyAliasesList = util.getKeyAliases(sourceKeystore);
            String selectedAlias = null;
            if( keyAliasesList.isEmpty() ){
                throw new Exception( this.rb.getResourceString( "keystore.contains.nokeys" ));
            }else if( keyAliasesList.size() == 1 ){
                selectedAlias = keyAliasesList.get(0);
            }else{
                //multiple keys available
                Object[] aliasArray = new Object[keyAliasesList.size()];
                for( int i = 0; i < keyAliasesList.size(); i++ ){
                    aliasArray[i] = keyAliasesList.get(i);
                }
                Object selectedAliasObject = JOptionPane.showInputDialog( parent, 
                        this.rb.getResourceString( "multiple.keys.message" ),
                        this.rb.getResourceString( "multiple.keys.title" ), JOptionPane.QUESTION_MESSAGE,
                        null, aliasArray, aliasArray[0] );
                //user break
                if( selectedAliasObject == null ){
                    return;
                }
                selectedAlias = selectedAliasObject.toString();
            }
            JKSKeys2PKCS12 importer = new JKSKeys2PKCS12(this.logger);
            importer.setTargetKeyStore( this.manager.getKeystore());
            
            char[] sourceKeypass = this.requestKeyPass( parent, selectedAlias);
            //user canceled
            if( sourceKeypass == null ){
                return;
            }
            importer.exportKey( sourceKeystore, sourceKeypass, selectedAlias );
            this.newAlias = selectedAlias;
            JOptionPane.showMessageDialog( this,
                    this.rb.getResourceString( "key.import.success.message" ),
                    this.rb.getResourceString( "key.import.success.title" ),
                    JOptionPane.INFORMATION_MESSAGE );            
        } catch( Exception e ){
            JOptionPane.showMessageDialog( this,
                    this.rb.getResourceString( "key.import.error.message", e.getMessage()),
                    this.rb.getResourceString( "key.import.error.title" ),
                    JOptionPane.ERROR_MESSAGE );
        }
    }
    
    private char[] requestKeyPass( JFrame parent, String alias ){
        final JPasswordField passwordField = new JPasswordField(20);
        final JOptionPane dialog = new JOptionPane(passwordField, 
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION){
            @Override public void selectInitialValue() {
                passwordField.requestFocusInWindow();
            }
        };
        dialog.createDialog(parent, this.rb.getResourceString( "enter.keypassword", alias )).setVisible(true);
        Object answer = dialog.getValue();
        if (answer == null || answer == JOptionPane.UNINITIALIZED_VALUE)
            return( null );
        else{
            int keyIndex = ((Integer)answer).intValue();
            if( keyIndex == 0 ){
                return( passwordField.getPassword() );
            }
        }
        return( null );
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
        jLabelImportKeystoreFile = new javax.swing.JLabel();
        jTextFieldImportJKSFile = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButtonBrowseImportFile = new javax.swing.JButton();
        jLabelKeystorePassphrase = new javax.swing.JLabel();
        jPasswordFieldPassphrase = new javax.swing.JPasswordField();
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

        jLabelImportKeystoreFile.setText(this.rb.getResourceString( "label.importkey"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jLabelImportKeystoreFile, gridBagConstraints);

        jTextFieldImportJKSFile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldImportJKSFileKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jTextFieldImportJKSFile, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelEdit.add(jPanel3, gridBagConstraints);

        jButtonBrowseImportFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/folder.gif"))); // NOI18N
        jButtonBrowseImportFile.setToolTipText(this.rb.getResourceString( "button.browse"));
        jButtonBrowseImportFile.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonBrowseImportFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseImportFileActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jButtonBrowseImportFile, gridBagConstraints);

        jLabelKeystorePassphrase.setText(this.rb.getResourceString( "label.keypass"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jLabelKeystorePassphrase, gridBagConstraints);

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
        setBounds((screenSize.width-409)/2, (screenSize.height-245)/2, 409, 245);
    }// </editor-fold>//GEN-END:initComponents
    
    private void jPasswordFieldPassphraseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldPassphraseKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jPasswordFieldPassphraseKeyReleased
    
    private void jButtonBrowseImportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseImportFileActionPerformed
        JFrame parent = (JFrame)SwingUtilities.getAncestorOfClass( JFrame.class, this );
        MecFileChooser chooser = new MecFileChooser(
                parent,
                this.rb.getResourceString( "filechooser.key.import" ));
        chooser.browseFilename( this.jTextFieldImportJKSFile );
    }//GEN-LAST:event_jButtonBrowseImportFileActionPerformed
    
    private void jTextFieldImportJKSFileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldImportJKSFileKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextFieldImportJKSFileKeyReleased
    
    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible( false );
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible( false );
        this.performImport();
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBrowseImportFile;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelImportKeystoreFile;
    private javax.swing.JLabel jLabelKeystorePassphrase;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelEdit;
    private javax.swing.JPasswordField jPasswordFieldPassphrase;
    private javax.swing.JTextField jTextFieldImportJKSFile;
    // End of variables declaration//GEN-END:variables
    
}
