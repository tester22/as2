//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey.java,v 1.1 2015/01/06 11:08:02 heller Exp $
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
public class ResourceBundleGenerateKey extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {

        {"title", "Generate key" },
        {"button.ok", "Ok" },
        {"button.cancel", "Cancel" },
        {"label.keytype", "Key type:" },
        {"label.signature", "Signature:" },
        {"label.size", "Size:" },
        {"label.commonname", "Common name:" },
        {"label.commonname.hint", "(Domain name of the server)" },
        {"label.organisationunit", "Organisation unit:" },
        {"label.organisationname", "Organisation name:" },
        {"label.locality", "Locality:" },
        {"label.locality.hint", "(City)" },
        {"label.state", "State:" },
        {"label.countrycode", "Country code (2 digits):" },
        {"label.mailaddress", "Mail address:" },
        {"label.validity", "Validity in days:" },
        {"label.purpose", "Key purpose:" },
        {"label.purpose.encsign", "Encryption and signature" },
        {"label.purpose.ssl", "TSL/SSL" },        
        {"warning.mail.in.domain", "The mail address is not part of the domain \"{0}\" (e.g. myname@{0}).\nThis might be a problem if you would like to trust the key later."},
        {"warning.nonexisting.domain", "The domain \"{0}\" seems not to exist." },
        {"warning.invalid.mail", "The mail address \"{0}\" is invalid." },
        {"button.reedit", "Edit settings" },
        {"button.ignore", "Ignore warnings" },
        {"warning.title", "Possible key settings problem" },
        {"view.expert", "Expert view" },
        {"view.basic", "Basic view" },
    };
    
}