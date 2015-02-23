//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/ResourceBundleAS2StatusBar_de.java,v 1.1 2015/01/06 11:07:37 heller Exp $ 
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
public class ResourceBundleAS2StatusBar_de extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {        
    {"count.ok", "Fehlerlose Transaktionen" },
    {"count.all", "Anzahl der Transaktionen" },
    {"count.pending", "Wartende Transaktionen" },
    {"count.failure", "Fehlerhafte Transaktionen" },
    {"count.selected", "Selektierte Transaktionen" },
    {"configuration.issue.single", "{0} Konfigurationsproblem" },
    {"configuration.issue.multiple", "{0} Konfigurationsprobleme" },
  };		
  
}