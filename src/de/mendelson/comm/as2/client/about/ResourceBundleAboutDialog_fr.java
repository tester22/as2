//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/about/ResourceBundleAboutDialog_fr.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.client.about;
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
public class ResourceBundleAboutDialog_fr extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"title", "A propos" },
    {"builddate", "Build date: {0}" },
    {"button.ok", "Valider" },
    {"tab.about", "Version" },
    {"tab.license", "Licence" },
  };		
  
}
