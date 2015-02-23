//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/AnonymousProcessing.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import org.apache.mina.core.session.IoSession;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Interface that defines a list of client server messages that must be processed without a login, e.g.
 * info requests, alive requests, internal message transmissions between 2 instances etc
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public interface AnonymousProcessing {

    public boolean processMessageWithoutLogin( IoSession session, ClientServerMessage message);
    
}
