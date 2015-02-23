//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_fr.java,v 1.1 2015/01/06 11:08:02 heller Exp $
package de.mendelson.util.security.cert.gui.keygeneration;

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
 * @version $Revision: 1.1 $
 */
public class ResourceBundleGenerateKey_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"title", "Générer la clé"},
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"label.keytype", "Type de clé:"},
        {"label.signature", "Signature:"},
        {"label.size", "Taille:"},
        {"label.commonname", "Common name:"},
        {"label.commonname.hint", "(Le nom de domaine)" },
        {"label.organisationunit", "Unité d''organisation:"},
        {"label.organisationname", "Nom de l''organisation:"},
        {"label.locality", "Localité:"},
        {"label.locality.hint", "(City)" },
        {"label.state", "État:"},
        {"label.countrycode", "Code pays (2 chiffres):"},
        {"label.mailaddress", "EMail:"},
        {"label.validity", "Validité en jours:"},
        {"label.purpose", "But principal:"},
        {"label.purpose.encsign", "Chiffrage et signature"},
        {"label.purpose.ssl", "TSL/SSL"},
        {"warning.mail.in.domain", "L''adresse e-mail ne fait pas partie du domaine \"{0}\" (e.g. myname@{0}).\nCela pourrait être un problème si vous souhaitez faire confiance à la clé plus tard."},
        {"warning.nonexisting.domain", "Le nom de domaine \"{0}\" ne semble pas exister." },
        {"warning.invalid.mail", "L''adresse mail \"{0}\" est invalide." },
        {"button.reedit", "Modifier les paramètres" },
        {"button.ignore", "Ignorer les avertissements" },
        {"warning.title", "Possible problème de paramètres" },
        {"view.expert", "Vue d''experts" },
        {"view.basic", "Vue de base" },
    };
}
