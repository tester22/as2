//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/cert/ResourceBundleTableModelCertificates_de.java,v 1.1 2015/01/06 11:07:58 heller Exp $
package de.mendelson.util.security.cert;
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
public class ResourceBundleTableModelCertificates_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {        
        {"header.alias", "Alias" },
        {"header.expire", "G�ltig bis" },
        {"header.length", "L�nge" },
        {"header.organization", "Organisation" },
        {"header.ca", "CA" },
    };
    
}