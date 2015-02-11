//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/message/loggui/ResourceBundleFileDisplay_fr.java,v 1.1 2015/01/06 11:07:41 heller Exp $
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
public class ResourceBundleFileDisplay_fr extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        
        {"no.file", "** AUCUNE DONNEE DISPONIBLE **" },
        {"file.notfound", "** LE FICHIER {0} N''EST PLUS DISPONIBLE **" },
        {"file.tolarge", "** {0}: LA TAILLE DES DONNEES EST TROP GRANDE POUR ETRE AFFICHEE **" },
    };
    
}
