//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/manualsend/ResourceBundleManualSend_de.java,v 1.1 2015/01/06 11:07:39 heller Exp $ 
package de.mendelson.comm.as2.client.manualsend;
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
public class ResourceBundleManualSend_de extends MecResourceBundle{

    @Override
  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Abbrechen" },
    {"button.browse", "Durchsuchen" },
    {"label.filename", "Dateiname:" },
    {"label.partner", "Empf�nger:" },
    {"label.localstation", "Lokale Station:" }, 
    {"label.selectfile", "Bitte w�hlen Sie die zu versendene Datei" },
    {"title", "Manueller Dateiversand" },
    {"send.success", "Die Datei wurde erfolgreich an den Versandprozess �bergeben." },
    {"send.failed", "Wegen eines Fehlers konnte die Datei nicht an den Versandprozess �bergeben werden." },
  };		
  
}