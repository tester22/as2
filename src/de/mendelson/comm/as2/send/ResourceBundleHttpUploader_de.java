//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_de.java,v 1.1 2015/01/06 11:07:46 heller Exp $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ResourceBundleHttpUploader_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"returncode.ok", "{0}: Nachricht erfolgreich versandt (HTTP {1}); {2} übertragen in {3} [{4} KB/s]." },
        {"returncode.accepted", "{0}: Nachricht erfolgreich versandt (HTTP {1}); {2} übertragen in {3} [{4} KB/s]." },
        {"sending.msg.sync", "{0}: Sende AS2 Nachricht an {1}, erwarte synchrone MDN zur Empfangsbestätigung." },
        {"sending.cem.sync", "{0}: Sende CEM Nachricht an {1}, erwarte synchrone MDN zur Empfangsbestätigung." },
        {"sending.msg.async", "{0}: Sende AS2 Nachricht an {1}, erwarte asynchrone MDN zur Empfangsbestätigung auf {2}." },
        {"sending.cem.async", "{0}: Sende CEM Nachricht an {1}, erwarte asynchrone MDN zur Empfangsbestätigung auf {2}." },
        {"sending.mdn.async", "{0}: Sende asynchrone Empfangsbestätigung (MDN) an {1}." },
        {"error.httpupload", "{0}: Übertragung fehlgeschlagen, entfernter AS2 Server meldet \"{1}\"." },
        {"error.noconnection", "{0}: Verbindungsproblem, es konnten keine Daten übertragen werden." },
        {"error.http502", "{0}: Verbindungsproblem, es konnten keine Daten übertragen werden. (HTTP 502 - BAD GATEWAY)" },
        {"error.http503", "{0}: Verbindungsproblem, es konnten keine Daten übertragen werden. (HTTP 503 - SERVICE UNAVAILABLE)" },
        {"error.http504", "{0}: Verbindungsproblem, es konnten keine Daten übertragen werden. (HTTP 504 - GATEWAY TIMEOUT)" },
        {"using.proxy", "{0}: Benutze Proxy {1}:{2}." },  
        {"answer.no.sync.mdn", "{0}: Die empfangene synchrone Empfangsbestätigung ist nicht im richtigen Format. Der Header \"{1}\" konnte nicht gefunden werden." },
        {"hint.SSLPeerUnverifiedException", "Hinweis:\nDieses Problem passierte während des SSL Handshake. Das System konnte somit keine sichere Verbindung zu Ihrem Partner aufbauen, das Problem hat nichts mit dem AS2 Protokoll zu tun.\nBitte prüfen Sie folgendes:\n*Haben Sie alle Zertifikate Ihres Partners in Ihren SSL Keystore importiert (für SSL, inkl Intermediate/Root Zertifikate)?\n*Hat Ihr Partner alle Zertifiakte von Ihnen importiert (für SSL, inkl Intermediate/Root Zertifikate)?" }, 
    };
    
}