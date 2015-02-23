//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/partner/gui/JTreePartner.java,v 1.1 2015/01/06 11:07:44 heller Exp $
package de.mendelson.comm.as2.partner.gui;

import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerCertificateInformation;
import de.mendelson.comm.as2.partner.clientserver.PartnerListRequest;
import de.mendelson.comm.as2.partner.clientserver.PartnerListResponse;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.tree.SortableTreeNode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.*;
import javax.swing.*;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Tree to display the AS2 partner
 * @author  S.Heller
 * @version $Revision: 1.1 $
 */
public class JTreePartner extends JTree {

    /**Holds a new partner ID for every created partner that is always negativ
     *but unique in this lifecycle
     */
    private int uniquePartnerIdCounter = -1;
    /**This is the root node*/
    private SortableTreeNode root = null;
    private BaseClient baseClient;

    /**Tree constructor*/
    public JTreePartner(BaseClient baseClient) {
        super(new SortableTreeNode());
        this.baseClient = baseClient;
        this.setRootVisible(false);
        this.root = (SortableTreeNode) this.getModel().getRoot();
        this.setCellRenderer(new TreeCellRendererPartner());
    }

    /**Returns the partner that is the localstation or null if none exists
     */
    public List<Partner> getLocalStations() {
        List<Partner> localStationList = new ArrayList<Partner>();
        for (int i = 0; i < this.root.getChildCount(); i++) {
            SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
            Partner partner = (Partner) child.getUserObject();
            if (partner.isLocalStation()) {
                localStationList.add(partner);
            }
        }        
        return (localStationList);
    }

    /**Returns that a local station is set in the tree. The configuration should
     *always work, it should be impossible to create a configuration without local station
     */
    public boolean localStationIsSet() {
        List<Partner> localStations = this.getLocalStations();
        return (localStations != null && !localStations.isEmpty());
    }

    /**Sets a partner as local station in the tree
     */
    public void setToLocalStation(Partner newLocalStation) {
        for (int i = 0; i < this.root.getChildCount(); i++) {
            SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
            Partner partner = (Partner) child.getUserObject();
            if (partner.equals(newLocalStation)) {
                partner.setLocalStation(partner.equals(newLocalStation));
                this.getModel().valueForPathChanged(new TreePath(child.getPath()), partner);
            }
        }
    }

    /**Builds up the tree*/
    public List<Partner> buildTree() throws Exception {
        this.root.removeAllChildren();
        PartnerListResponse response = (PartnerListResponse) this.baseClient.sendSync(new PartnerListRequest(PartnerListRequest.LIST_ALL));
        List<Partner> partnerList = response.getList();
        SortableTreeNode nodePartner = null;
        SortableTreeNode firstNodePartner = null;
        for (int i = 0; i < partnerList.size(); i++) {
            nodePartner = new SortableTreeNode(partnerList.get(i));
            this.root.add(nodePartner);
            if (firstNodePartner == null) {
                firstNodePartner = nodePartner;
            }
        }
        if (nodePartner != null) {
            this.expandPath(new TreePath(nodePartner.getPath()));
            this.fireTreeExpanded(new TreePath(nodePartner.getPath()));

        }
        if (firstNodePartner != null) {
            this.setSelectionPath(new TreePath(firstNodePartner.getPath()));
        }
        return (partnerList);
    }

    /**Returns the number specified partner from the tree*/
    public int getPartnerCountByName(String name) {
        int count = 0;
        for (int i = 0; i < this.root.getChildCount(); i++) {
            SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
            Partner partner = (Partner) child.getUserObject();
            if (partner.getName().equals(name)) {
                count++;
            }
        }
        return (count);
    }

    /**Returns the number specified partner from the tree*/
    public int getPartnerCountByAS2Id(String as2Identification) {
        int count = 0;
        for (int i = 0; i < this.root.getChildCount(); i++) {
            SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
            Partner partner = (Partner) child.getUserObject();
            if (partner.getAS2Identification().equals(as2Identification)) {
                count++;
            }
        }
        return (count);
    }

    /**Returns a specified partner from the tree*/
    public Partner getPartnerByName(String name) {
        for (int i = 0; i < this.root.getChildCount(); i++) {
            SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
            Partner partner = (Partner) child.getUserObject();
            if (partner.getName().equals(name)) {
                return (partner);
            }
        }
        return (null);
    }

    /**Returns a specified partner from the tree*/
    public Partner getPartnerByAS2Id(String id) {
        for (int i = 0; i < this.root.getChildCount(); i++) {
            SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
            Partner partner = (Partner) child.getUserObject();
            if (partner.getAS2Identification().equals(id)) {
                return (partner);
            }
        }
        return (null);
    }

    /**Creates a new partner, adds it to the tree and selects it
     */
    public Partner createNewPartner(CertificateManager certificateManager) {
        Partner partner = new Partner();
        partner.setDBId(this.uniquePartnerIdCounter);
        this.uniquePartnerIdCounter--;
        partner.setName("Partner");
        partner.setAS2Identification("AS2Ident");
        partner.setURL(partner.getDefaultURL());
        partner.setMdnURL(partner.getDefaultURL());
        partner.setLocalStation(false);
        List<KeystoreCertificate> list = certificateManager.getKeyStoreCertificateList();
        if (list.size() > 0) {
            KeystoreCertificate certificate = list.get(0);
            //just take the first entry
            PartnerCertificateInformation signInfo = new PartnerCertificateInformation(
                    certificate.getFingerPrintSHA1(),
                    PartnerCertificateInformation.CATEGORY_SIGN);
            partner.setCertificateInformation(signInfo);
            PartnerCertificateInformation cryptInfo = new PartnerCertificateInformation(
                    certificate.getFingerPrintSHA1(),
                    PartnerCertificateInformation.CATEGORY_CRYPT);
            partner.setCertificateInformation(cryptInfo);
        }
        this.addPartner( partner );
        return( partner );
    }

    public void addPartner( Partner partner ){
        SortableTreeNode node = new SortableTreeNode(partner);
        this.root.add(node);
        ((DefaultTreeModel) this.getModel()).nodeStructureChanged(this.root);
        this.setSelectionPath(new TreePath(node.getPath()));
    }
    
    
    /**Returns the selected partner or null if no partner is selected*/
    public Partner getSelectedPartner() {
        SortableTreeNode selectedNode = this.getSelectedNode();
        if (selectedNode == null) {
            return (null);
        }
        Partner partner = (Partner) selectedNode.getUserObject();
        return (partner);
    }

    /**Tries to delete the selected partner, will return null on any problem
     */
    public Partner deleteSelectedPartner() {
        //do not delete the last child
        if (this.root.getChildCount() == 1) {
            return (null);
        }
        SortableTreeNode selectedNode = this.getSelectedNode();
        if (selectedNode == null) {
            return (null);
        }
        Partner partner = (Partner) selectedNode.getUserObject();
        int index = this.root.getIndex(selectedNode);
        this.root.remove(selectedNode);
        if (index > 0) {
            index--;
        }
        ((DefaultTreeModel) this.getModel()).nodeStructureChanged(this.root);
        SortableTreeNode newSelectedNode = (SortableTreeNode) root.getChildAt(index);
        this.setSelectionPath(new TreePath(newSelectedNode.getPath()));
        return (partner);
    }

    /**Returns the selected node of the Tree
     */
    public SortableTreeNode getSelectedNode() {
        TreePath path = this.getSelectionPath();
        if (path != null) {
            return ((SortableTreeNode) path.getLastPathComponent());
        }
        return (null);
    }
}
