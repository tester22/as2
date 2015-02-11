//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/server/AnonymousProcessingAS2.java,v 1.1 2015/01/06 11:07:49 heller Exp $
package de.mendelson.comm.as2.server;

import org.apache.mina.core.session.IoSession;

import de.mendelson.comm.as2.clientserver.message.IncomingMessageRequest;
import de.mendelson.comm.as2.clientserver.message.ServerInfoRequest;
import de.mendelson.util.clientserver.AnonymousProcessing;
import de.mendelson.util.clientserver.messages.ClientServerMessage;

/**
 * Contains all request messages that could be processed without a login
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 * @since build 68
 */
public class AnonymousProcessingAS2 implements AnonymousProcessing {

    @Override
    public boolean processMessageWithoutLogin(IoSession session, ClientServerMessage message) {
        if (message instanceof IncomingMessageRequest ) {
            return (true);
        }
        if (message instanceof ServerInfoRequest) {
            return (true);
        }        
        return (false);
    }
}
