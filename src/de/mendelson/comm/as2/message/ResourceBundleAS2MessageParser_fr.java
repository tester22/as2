//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/message/ResourceBundleAS2MessageParser_fr.java,v 1.1 2015/01/06 11:07:40 heller Exp $
package de.mendelson.comm.as2.message;
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
 * @author E.Pailleau
 * @version $Revision: 1.1 $
 */
public class ResourceBundleAS2MessageParser_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"mdn.incoming", "{0}: La transmission entrante est un MDN." },
        {"mdn.answerto", "{0}: Le MDN est la r�ponse au message AS2 \"{1}\"." },
        {"mdn.state", "{0}: L''�tat du MDN est [{1}]." },
        {"mdn.details", "{0}: D�tails du MDN re�u du serveur AS2 distant: {1}" },
        {"msg.incoming", "{0}: La transmission entrante est un message AS2, taille du message brut: {1}." },
        {"mdn.signed", "{0}: Le MDN iest sign�." },
        {"mdn.unsigned.error", "{0}: Le MDN n''est pas sign�. La configuration stipule que le MDN du partenaire \"{1}\" doit �tre sign�." },
        {"mdn.signed.error", "{0}: Le MDN est sign�. La configuration stipule que le MDN du partenaire \"{1}\" ne doit pas �tre sign�." },
        {"msg.signed", "{0}: Le message AS2 est sign�." },
        {"msg.encrypted", "{0}: Le message AS2 est crypt�." },
        {"msg.notencrypted", "{0}: Le message AS2 n''est pas crypt�." },
        {"msg.notsigned", "{0}: Le message AS2 n''est pas sign�." },
        {"mdn.notsigned", "{0}: Le MDN n''est pas sign�." },
        {"signature.ok", "{0}: La signature num�rique a �t� v�rifi�e avec succ�s." },
        {"signature.failure", "{0}: V�rification de signature digitale �chou�e - {1}" },
        {"signature.using.alias", "{0}: Utilisation du certificat \"{1}\" pour v�rifier la signature." },
        {"decryption.done.alias", "{0}: Les donn�es ont �t� d�crypt�es avec la clef \"{1}\"." },
        {"mdn.unexpected.messageid", "{0}: Le MDN r�f�rence un message AS2 avec l''identifiant \"{1}\" qui est inconnu." },
        {"mdn.unexpected.state", "{0}: Le MDN r�f�rence le message AS2 avec l''identification \"{1}\", cela n''attend pas un MDN" },
        {"data.compressed.expanded", "{0}: Le contenu compress� a vu sa taille passer de {1} � {2}." },
        {"found.attachments", "{0}: Trouv� {1} contenus en pi�ces attach�es dans le message." },
        {"decryption.inforequired", "{0}: Afin de d�crypter les donn�es une clef avec les param�tres suivants est requise:\n{1}" },
        {"decryption.infoassigned", "{0}: Une clef avec les param�tres suivants est utilis� pour d�crypter les donn�es (alias \"{1}\"):\n{2}" },
        {"signature.analyzed.digest", "{0}: L''�metteur a utilis� l''algorithme {1} pour signer le message." },
        {"filename.extraction.error", "{0}: Extraire noms de fichier originaux n''est pas possible: \"{1}\", ignor�." },
        {"contentmic.match", "{0}: Le Message Integrity Code (MIC) assortit le message AS2 envoy�." },
        {"contentmic.failure", "{0}: Le Message Integrity Code (MIC) n'assortit pas le message AS2 envoy� (requis: {1}, re�u: {2})." },
        {"found.cem", "{0}: Le message est un message d'�change de certificat (CEM)." },
                {"data.unable.to.process.content.transfer.encoding", "Les donn�es ont �t� re�ues qui n''ont pas pu �tre trait�es. Le codage de transfert de contenu \"{0}\" est inconnue."},
    };
    
}
