//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/modulelock/ClientInformation.java,v 1.1 2015/01/06 11:07:41 heller Exp $
package de.mendelson.comm.as2.modulelock;

import java.io.Serializable;
import java.util.Objects;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores information about the client that locks a module or requests a module lock
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ClientInformation implements Serializable{

    private String username;
    private String clientIP;
    private String uniqueid;
        
    public ClientInformation( String username, String clientIP, String uniqueid ){        
        this.username = username;
        this.clientIP = clientIP;
        this.uniqueid = uniqueid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the clientIP
     */
    public String getClientIP() {
        return clientIP;
    }

    /**
     * @return the uniqueid
     */
    public String getUniqueid() {
        return uniqueid;
    }
    
    /**Overwrite the equal method of object
     *@param anObject object ot compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof ClientInformation) {
            ClientInformation entry = (ClientInformation) anObject;
            return ( this.uniqueid.equals(entry.uniqueid));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.uniqueid);
        return hash;
    }
    
}
