//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/client/ResourceBundleAS2StatusBar.java,v 1.1 2015/01/06 11:07:37 heller Exp $ 
package de.mendelson.comm.as2.client;
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
public class ResourceBundleAS2StatusBar extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {        
    {"count.ok", "Transaction without failure" },
    {"count.all", "All transactions" },
    {"count.pending", "Pending transactions" },
    {"count.failure", "Transactions with failure" },
    {"count.selected", "Selected transaction" },
    {"configuration.issue.single", "{0} configuration issue" },
    {"configuration.issue.multiple", "{0} configuration issues" },
  };		
  
}