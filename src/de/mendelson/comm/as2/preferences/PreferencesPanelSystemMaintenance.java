//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/preferences/PreferencesPanelSystemMaintenance.java,v 1.1 2015/01/06 11:07:45 heller Exp $
package de.mendelson.comm.as2.preferences;

import java.util.*;
import de.mendelson.util.*;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.clients.preferences.PreferencesClient;
import java.sql.Connection;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 *Panel to define the inbox settings
 * @author S.Heller
 * @version: $Revision: 1.1 $
 */
public class PreferencesPanelSystemMaintenance extends PreferencesPanel {

    /**Localize the GUI*/
    private MecResourceBundle rb = null;
    /**GUI prefs*/
    private PreferencesClient preferences;

    /** Creates new form PreferencesPanelDirectories */
    public PreferencesPanelSystemMaintenance(BaseClient baseClient) {
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePreferences.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        this.initComponents();
        this.preferences = new PreferencesClient(baseClient);
        if (this.preferences.getBoolean(PreferencesAS2.COMMUNITY_EDITION)) {
            this.jCheckBoxDeleteStatsOlderThan.setVisible(false);
            this.jTextFieldDeleteStatsOlderThan.setVisible(false);
            this.jLabelDays2.setVisible(false);
        }
    }

    /**Sets new preferences to this panel to changes/modify
     */
    @Override
    public void loadPreferences(){
        this.jCheckBoxDeleteMsgOlderThan.setSelected(this.preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE));
        this.jCheckBoxDeleteMsgOlderThanLog.setSelected(this.preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE_LOG));
        this.jTextFieldDeleteMsgOlderThan.setText(String.valueOf(this.preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)));
        if (!this.preferences.getBoolean(PreferencesAS2.COMMUNITY_EDITION)) {
            this.jCheckBoxDeleteStatsOlderThan.setSelected(this.preferences.getBoolean(PreferencesAS2.AUTO_STATS_DELETE));
            this.jTextFieldDeleteStatsOlderThan.setText(String.valueOf(this.preferences.getInt(PreferencesAS2.AUTO_STATS_DELETE_OLDERTHAN)));
        }
    }

    /**Stores the GUI settings in the preferences
     */
    @Override
    public void savePreferences() {
        try {
            int olderThanFiles = Integer.valueOf(this.jTextFieldDeleteMsgOlderThan.getText()).intValue();
            //do not allow negative values or the 0
            if (olderThanFiles <= 0) {
                olderThanFiles = Integer.getInteger(this.preferences.getDefaultValue(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)).intValue();
            }
            this.preferences.putInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN, olderThanFiles);
            this.preferences.putBoolean(PreferencesAS2.AUTO_MSG_DELETE, this.jCheckBoxDeleteMsgOlderThan.isSelected());
            this.preferences.putBoolean(PreferencesAS2.AUTO_MSG_DELETE_LOG, this.jCheckBoxDeleteMsgOlderThanLog.isSelected());
            //stats auto delete capabilites
            if (!this.preferences.getBoolean(PreferencesAS2.COMMUNITY_EDITION)) {
                int olderThanStats = Integer.valueOf(this.jTextFieldDeleteStatsOlderThan.getText()).intValue();
                if (olderThanStats <= 0) {
                    olderThanStats = Integer.getInteger(this.preferences.getDefaultValue(PreferencesAS2.AUTO_STATS_DELETE_OLDERTHAN)).intValue();
                }
                this.preferences.putInt(PreferencesAS2.AUTO_STATS_DELETE_OLDERTHAN, olderThanStats);
                this.preferences.putBoolean(PreferencesAS2.AUTO_STATS_DELETE, this.jCheckBoxDeleteStatsOlderThan.isSelected());
            }
        } catch (Exception nop) {
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

        jPanelSpace = new javax.swing.JPanel();
        jCheckBoxDeleteMsgOlderThan = new javax.swing.JCheckBox();
        jTextFieldDeleteMsgOlderThan = new javax.swing.JTextField();
        jLabelDays1 = new javax.swing.JLabel();
        jCheckBoxDeleteMsgOlderThanLog = new javax.swing.JCheckBox();
        jCheckBoxDeleteStatsOlderThan = new javax.swing.JCheckBox();
        jTextFieldDeleteStatsOlderThan = new javax.swing.JTextField();
        jLabelDays2 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanelSpace, gridBagConstraints);

        jCheckBoxDeleteMsgOlderThan.setText(this.rb.getResourceString( "label.deletemsgolderthan"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jCheckBoxDeleteMsgOlderThan, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jTextFieldDeleteMsgOlderThan, gridBagConstraints);

        jLabelDays1.setText(this.rb.getResourceString( "label.days" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabelDays1, gridBagConstraints);

        jCheckBoxDeleteMsgOlderThanLog.setText(this.rb.getResourceString( "label.deletemsglog"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jCheckBoxDeleteMsgOlderThanLog, gridBagConstraints);

        jCheckBoxDeleteStatsOlderThan.setText(this.rb.getResourceString( "label.deletestatsolderthan"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jCheckBoxDeleteStatsOlderThan, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jTextFieldDeleteStatsOlderThan, gridBagConstraints);

        jLabelDays2.setText(this.rb.getResourceString( "label.days" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabelDays2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxDeleteMsgOlderThan;
    private javax.swing.JCheckBox jCheckBoxDeleteMsgOlderThanLog;
    private javax.swing.JCheckBox jCheckBoxDeleteStatsOlderThan;
    private javax.swing.JLabel jLabelDays1;
    private javax.swing.JLabel jLabelDays2;
    private javax.swing.JPanel jPanelSpace;
    private javax.swing.JTextField jTextFieldDeleteMsgOlderThan;
    private javax.swing.JTextField jTextFieldDeleteStatsOlderThan;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getIconResource() {
        return ("/de/mendelson/comm/as2/preferences/maintenance32x32.gif");
    }

    @Override
    public String getTabResource() {
        return ("tab.maintenance");
    }
}
