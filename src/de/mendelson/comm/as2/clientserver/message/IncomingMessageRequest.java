//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/clientserver/message/IncomingMessageRequest.java,v 1.1 2015/01/06 11:07:39 heller Exp $
package de.mendelson.comm.as2.clientserver.message;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.Serializable;
import java.util.Properties;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class IncomingMessageRequest extends ClientServerMessage implements Serializable {

    private String contentType = null;
    private String remoteHost = null;
    private Properties header = new Properties();
    private String messageDataFilename = null;

    public IncomingMessageRequest() {
    }

    @Override
    public String toString() {
        return ("Incoming message response");
    }

    public void addHeader(String key, String value) {
        this.header.setProperty(key.toLowerCase(), value);
    }

    /**
     * Deletes the existing request header and sets new
     */
    public void setHeader(Properties header) {
        this.header = header;
    }

    public Properties getHeader() {
        return (this.header);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    /**
     * @return the messageDataFilename
     */
    public String getMessageDataFilename() {
        return messageDataFilename;
    }

    /**
     * @param messageDataFilename the messageDataFilename to set
     */
    public void setMessageDataFilename(String messageDataFilename) {
        this.messageDataFilename = messageDataFilename;
    }

}
