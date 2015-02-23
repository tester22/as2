//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/security/csr/ResourceBundleCSRUtil_fr.java,v 1.1 2015/01/06 11:08:02 heller Exp $
package de.mendelson.util.security.csr;
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
public class ResourceBundleCSRUtil_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {                
        {"verification.failed", "L''op�ration a �chou� - V�rification de la CSR created a �chou�" },
        {"no.certificates.in.reply", "L''op�ration a �chou� - Aucun certificat de la r�ponse de la CSR, incapable de patcher la cl�" },
        {"missing.cert.in.trustchain", "L''op�ration a �chou� - Le syst�me n''a pas �tabli de la cha�ne de confiance de la r�ponse.\nVeuillez importer le certificat avec le \nissuer\n {0} keystore du premier." },
        {"response.chain.incomplete", "L''op�ration a �chou� - La cha�ne de certificats de la r�ponse est incompl�te" },
        {"response.verification.failed", "L''op�ration a �chou� - Probl�me de v�rification de la cha�ne de certificats de la r�ponse: {0}" },
        {"response.public.key.does.not.match", "L''op�ration a �chou� - Ce n''est pas la solution CA de cette cl�." },
    };

}