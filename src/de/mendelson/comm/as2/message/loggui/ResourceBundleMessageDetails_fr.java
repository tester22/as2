//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/loggui/ResourceBundleMessageDetails_fr.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.message.loggui;
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
public class ResourceBundleMessageDetails_fr extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        
        {"title", "D�tails du message" },
        {"button.ok", "Valider" },
        {"header.timestamp", "Date" },
        {"header.messageid", "R�f No" },
        {"message.raw.decrypted", "Donn�es brutes (non d�crypt�)" },
        {"message.header", "Ent�te message" },
        {"message.payload", "Contenu transf�r�" },
        {"message.payload.multiple", "Contenu ({0})" },
        {"tab.log", "Log de l''instance de ce message" },
        {"header.encryption", "Cryptage" },
        {"header.signature", "Signature" },
        {"header.senderhost", "Emetteur" },
        {"header.useragent", "Serveur AS2" },
    };
    
}
