//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/send/ResourceBundleDirPollManager_de.java,v 1.1 2015/01/06 11:07:46 heller Exp $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleDirPollManager_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"manager.started", "Manager zur Verzeichnis�berwachung gestartet." },
        {"poll.stopped", "Verzeichnis�berwachung f�r die Beziehung \"{0}/{1}\" wurde gestoppt." },
        {"poll.started", "Verzeichnis�berwachung f�r die Beziehung \"{0}/{1}\" wurde gestartet. Ignoriere: \"{2}\". Intervall: {3}s" },
        {"warning.ro", "Ausgangsdatei {0} ist schreibgesch�tzt, Datei wird ignoriert." },
        {"warning.notcomplete", "Ausgangsdatei {0} ist noch nicht vollst�ndig vorhanden, Datei wird ignoriert." },
        {"messagefile.deleted", "{0}: Die Datei \"{1}\" wurde gel�scht und der Verarbeitungswarteschlange des Servers �bergeben." },
        {"processing.file", "Verarbeite die Datei \"{0}\" f�r die Beziehung \"{1}/{2}\"." }, 
        {"processing.file.error", "Verarbeitungsfehler der Datei \"{0}\" f�r die Beziehung \"{1}/{2}\": \"{3}\"." },
    };
    
}