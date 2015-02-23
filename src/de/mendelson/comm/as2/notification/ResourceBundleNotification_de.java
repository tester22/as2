//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/notification/ResourceBundleNotification_de.java,v 1.1 2015/01/06 11:07:42 heller Exp $
package de.mendelson.comm.as2.notification;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/** 
 * ResourceBundle to localize the mendelson products - if you want to localize 
 * eagle to your language, please contact us: localize@mendelson.de
 * @author  S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleNotification_de extends MecResourceBundle {

    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        //dialog
        {"test.message.send", "Eine Testnachricht wurde geschickt an {0}."},
        {"test.message.debug", "\nDer Mailversandprozess schlug fehlt, die folgenden Zusatzinformationen k�nnten Ihnen helfen, das Problem einzugrenzen:\n" },
        {"transaction.message.send", "{0}: {1} wurde �ber den Fehler in der Transaktion per eMail benachrichtigt."},
        {"transaction.message.send.error", "{0}: Das Senden einer Benachrichtigungsmail �ber einen Transaktionfehler an {1} schlug fehl: {2}."},
        {"misc.message.send", "Eine Benachrichtigungsmail wurde an {0} geschickt."},
        {"cert.message.send", "{0} wurde �ber ein ablaufendes oder abgelaufenes Zertifikat per eMail informiert [{1}]."},
        {"quota.send.message.send", "{0} wurde �ber ein �berschrittenes Sendekontingent per eMail informiert."},
        {"quota.receive.message.send", "{0} wurde �ber ein �berschrittenes Empfangskontingent per eMail informiert."},
        {"quota.sendreceive.message.send", "{0} wurde �ber ein �berschrittenes Sende- und Empfangskontingent per eMail informiert."},};
}
