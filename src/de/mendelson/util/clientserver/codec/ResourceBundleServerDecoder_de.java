//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/codec/ResourceBundleServerDecoder_de.java,v 1.1 2015/01/06 11:07:54 heller Exp $
package de.mendelson.util.clientserver.codec;

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
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleServerDecoder_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        {"client.incompatible", "Eine Client-Server Verbindung ist nicht möglich, Client und Server haben einen unterschiedlichen Softwarestand. Bitte verwenden Sie die richtige Clientversion."},};
}