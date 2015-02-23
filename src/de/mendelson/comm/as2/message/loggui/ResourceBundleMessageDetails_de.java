//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/loggui/ResourceBundleMessageDetails_de.java,v 1.1 2015/01/06 11:07:41 heller Exp $
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
 * @version $Revision: 1.1 $
 */
public class ResourceBundleMessageDetails_de extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        
        {"title", "Nachrichtendetails" },
        {"button.ok", "Ok" },
        {"header.timestamp", "Datum" },
        {"header.messageid", "Referenznummer" },
        {"message.raw.decrypted", "�bertragungsdaten (unverschl�sselt)" },         
        {"message.header", "Kopfdaten" },
        {"message.payload", "Nutzdaten" },
        {"message.payload.multiple", "Nutzdaten ({0})" },
        {"tab.log", "Log dieser Nachrichteninstanz" },
        {"header.encryption", "Verschl�sselung" },
        {"header.signature", "Digitale Signatur" },
        {"header.senderhost", "Sender" },
        {"header.useragent", "AS2 Server" },
    };
    
}