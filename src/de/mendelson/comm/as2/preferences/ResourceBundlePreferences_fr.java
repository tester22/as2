//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_fr.java,v 1.1 2015/01/06 11:07:45 heller Exp $
package de.mendelson.comm.as2.preferences;
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
public class ResourceBundlePreferences_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        //preferences localized
        { PreferencesAS2.SERVER_HOST, "H�te serveur" },
        { PreferencesAS2.DIR_MSG, "Archivage message" },
                
        {"button.ok", "Valider" },
        {"button.cancel", "Annuler" },
        {"button.modify", "Modifier" },
        {"button.browse", "Parcourir..."},
                
        {"filechooser.selectdir", "S�lectionner un r�pertoire" },
                
        {"title", "Pr�f�rences" },
        {"tab.language", "Langage" },
        {"tab.dir", "R�pertoires" },
        {"tab.security", "S�curit�" },
        {"tab.proxy", "Proxy" },
        {"tab.misc", "Divers" },
        {"tab.maintenance", "Maintenance" },
        {"tab.notification", "Notification" },
        {"tab.interface", "Modules" },

        {"header.dirname", "Type" },
        {"header.dirvalue", "R�p." },

        {"label.keystore.https.pass", "Mot de passe du porte-clef (envoi https):" },
        {"label.keystore.pass", "Mot de passe du porte-clef (encryption/signature):" },
        {"label.keystore.https", "Porte-clef (envoi https):" },
        {"label.keystore.encryptionsign", "Porte-clef (enc, sign):" },
        {"label.proxy.url", "URL du proxy:" },
        {"label.proxy.user", "Login utilisateur du proxy:" },
        {"label.proxy.pass", "Mot de passe utilisateur du proxy:" },
        {"label.proxy.use", "Utiliser un proxy pour les connexions sortante HTTP/HTTPs" },
        {"label.proxy.useauthentification", "Utiliser l''authentification aupr�s du proxy" },
                
        {"filechooser.keystore", "Merci de s�lectionner le fichier porte-clef (format jks)." },
                
        {"label.days", "jours" },
        {"label.deletemsgolderthan", "Supprimer automatiquement les messages plus vieux que" },
        {"label.deletemsglog", "Tenir informer dans le log � propos des messages automatiquement supprim�s" },
        {"label.deletestatsolderthan", "Supprimer automatiquement les statistiques qui sont plus vieux que"},
        {"label.asyncmdn.timeout", "Temps d''attente maximal pour un MDN asynchrone:" },
        {"label.httpsend.timeout", "Timeout sur envoi HTTP(s):" },
        {"label.min", "minutes" },
        {"receipt.subdir", "Cr�er des sous-r�pertoires par partenaires pour les messages re�us" },
        
        //notification
        {"checkbox.notifycertexpire", "Notifier l''expiration de certificats" },
        {"checkbox.notifytransactionerror", "Notifier les erreurs de transaction" },
        {"checkbox.notifycem", "Notifier des �v�nements d'�change certificats (CEM)" },
        {"checkbox.notifyfailure", "Notifier les problems syst�me"},
        {"checkbox.notifyresend", "Notifier renvoie rejet�s"},
        {"button.testmail","Envoyer un e-mail de test"},
        {"label.mailhost", "H�te du serveur de mail (SMTP):" },
        {"label.mailport", "Port:" },
        {"label.mailaccount", "Compte sur le serveur de mail:" },
        {"label.mailpass", "Mot de passe sur le serveur de mail:" },
        {"label.notificationmail", "Adresse de notification du destinataire:" },
        {"label.replyto", "Adresse de r�ponse (Replyto):" },
        {"label.smtpauthentication", "Authentification d''utilisation SMTP" },
        {"label.smtpauthentication.user", "Nom d'utilisateur:" },
        {"label.smtpauthentication.pass", "Mot de passe:" },
        {"label.security", "S�curit� de connexion:" },
        {"testmail.message.success", "E-mail de test envoy� avec succ�s." },
        {"testmail.message.error", "Erreur lors de l''envoi de l''e-mail de test:\n{0}" },
        {"testmail.title", "R�sultat de l''envoi de l''email de test" },
        {"testmail", "L''email de test"},
        //interface
        {"label.showhttpheader", "Laissez configurer les en-t�tes de HTTP dans la configuration d''associ�" },
        {"label.showquota", "Laissez configurer l''avis de quote-part dans la configuration d''associ�" },
        {"label.cem", "Permettre l''�change de certificat (CEM)"},
        {"label.outboundstatusfiles", "�crire des fichiers de statut de transaction sortante"},
        {"info.restart.client", "Un red�marrage du client est requise pour effectuer ces modifications valide!" },
        {"remotedir.select", "S�lectionnez le r�pertoire sur le serveur" },
        //retry
        {"label.retry.max", "Le nombre maximum de tentatives de connexion" },
        {"label.retry.waittime", "Le temps d''attente entre deux tentatives de connexion" },
        {"label.sec", "seconds" },
        {"keystore.hint", "Attention:\nVoulez pas modifier ces param�tres, sauf si vous avez utilis� un outil tiers pour modifier vos mots de passe du fichier de cl�s (qui n''est pas recommand�). Mise en place des mots de passe ici ne sera pas modifier les mots de passe du fichier de cl�s sous-jacente - ces options seulement permettre d''acc�der � des fichiers de cl�s externes."},
    };
    
}
