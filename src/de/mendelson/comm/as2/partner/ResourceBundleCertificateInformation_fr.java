//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/partner/ResourceBundleCertificateInformation_fr.java,v 1.1 2015/01/06 11:07:43 heller Exp $
package de.mendelson.comm.as2.partner;

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
public class ResourceBundleCertificateInformation_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"localstation.decrypt.prio1", "Les messages entrants pour la station locale \"{0}\" vont �tre d�chiffr�es en utilisant le certificat \"{1}\"."},
        {"localstation.decrypt.prio2", "\"{0}\" est �galement dispos� � d�crypter les messages entrants en utilisant le certificat \"{1}\"."},
        {"localstation.sign.prio1", "Messages sortants de la station locale \"{0}\" seront sign�s � l'aide du certificat \"{1}\"."},
        {"partner.encrypt.prio1", "Les messages sortants au partenaire \"{0}\" seront chiffr�es � l'aide du certificat \"{1}\"."},
        {"partner.sign.prio1", "Signatures de message entrant provenant du partenaire \"{0}\" seront v�rifi�es � l'aide du certificat \"{1}\"."},
        {"partner.sign.prio2", "Le syst�me est �galement dispos� � v�rifier les signatures de message entrant de \"{0}\" � l'aide du certificat \"{1}\"."},};
}
