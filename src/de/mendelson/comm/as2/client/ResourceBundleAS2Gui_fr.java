//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/ResourceBundleAS2Gui_fr.java,v 1.1 2015/01/06 11:07:37 heller Exp $
package de.mendelson.comm.as2.client;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/** 
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 1.1 $
 */
public class ResourceBundleAS2Gui_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"menu.file", "Fichier"},
        {"menu.file.exit", "Fermer"},
        {"menu.file.partner", "Partenaire"},
        {"menu.file.datasheet", "Cr�er une fiche de communication"},
        {"menu.file.certificates", "Certificats"},
        {"menu.file.certificate", "Certificats"},
        {"menu.file.certificate.signcrypt", "Certificats (signature, cryptage)"},
        {"menu.file.certificate.ssl", "Certificats (SSL)"},
        {"menu.file.cem", "Certificat d'�change pr�sentation (CEM)"},
        {"menu.file.cemsend", "Certificats d''�change avec des partenaires (CEM)"},
        {"menu.file.statistic", "Statistiques"},
        {"menu.file.quota", "Quota"},
        {"menu.file.export", "Configuration d''exportation"},
        {"menu.file.import", "Configuration d''importation"},
        {"menu.file.preferences", "Pr�f�rences"},
        {"menu.file.send", "Envoyer un fichier � un partenaire"},
        {"menu.file.resend", "Envoyer en tant que nouvelle transaction"},
        {"menu.file.resend.multiple", "Envoyer en tant que nouvelles transactions"},
        {"menu.help", "Aide"},
        {"menu.help.about", "A propos"},
        {"menu.help.shop", "mendelson online shop"},
        {"menu.help.helpsystem", "Syst�me d''aide"},
        {"menu.help.forum", "Forum"},
        {"details", "D�tails du message"},
        {"filter.showfinished", "Voir les termin�s"},
        {"filter.showpending", "Voir les en-cours"},
        {"filter.showstopped", "Voir les stopp�s"},
        {"filter.none", "-- Aucun --"},
        {"filter.partner", "Filtrer le partenaire:"},
        {"filter.localstation", "Filtrer le station locale:"},
        {"filter.direction", "Filtrer le direction:"},
        {"filter.direction.inbound", "Entrer"},
        {"filter.direction.outbound", "Sortant"},
        {"filter", "Filtrer"},
        {"keyrefresh", "Recharger les cl�s"},
        {"delete.msg", "Supprimer les messages s�lectionn�s"},
        {"stoprefresh.msg", "Figer le rafra�chissement"},
        {"dialog.msg.delete.message", "Voulez-vous vraiment supprimer de mani�re permanente les messages s�lectionn�s ?"},
        {"dialog.msg.delete.title", "Suppression de messages"},
        {"welcome", "Bienvenue, {0}"},
        {"warning.eval", "Ceci est une copie d''�valuation."},
        {"warning.refreshstopped", "Le rafra�chissement de l''interface a �t� arr�t�."},
        {"tab.welcome", "Nouveaut�s et mises � jour"},
        {"tab.transactions", "Transactions"},
        {"new.version", "Une nouvelle version est disponible. Cliquez ici pour la t�l�charger."},
        {"filechooser.export", "Veuillez choisir un dossier d''exportation."},
        {"filechooser.import", "Veuillez choisir un dossier d''importation."},
        {"export.success", "La configuration a �t� export�e avec succ�s vers \"{0}\"."},
        {"dbconnection.failed.message", "Incapable d''�tablir une connexion DB au serveur AS2: {0}"},
        {"dbconnection.failed.title", "Impossible de se connecter"},
        {"login.failed.client.incompatible.message", "Le serveur de rapports que ce client est incompatible. Veuillez utiliser la version du client appropri�."},
        {"login.failed.client.incompatible.title", "Login rejet�"},
        {"uploading.to.server", "T�l�chargement sur le serveur"},
        {"refresh.overview", "Rafra�chissant"},
        {"resend.performed", "Cette transaction a �t� renvoyer manuellement comme une nouvelle transaction ([{0}])." },
        {"dialog.resend.message", "Voulez-vous vraiment de renvoyer les donn�es de la transaction s�lectionn�e?"},
        {"dialog.resend.message.multiple", "Voulez-vous vraiment de renvoyer les donn�es des {0} transactions s�lectionn�es?"},
        {"dialog.resend.title", "Transaction renvoyer"},        
    };
}