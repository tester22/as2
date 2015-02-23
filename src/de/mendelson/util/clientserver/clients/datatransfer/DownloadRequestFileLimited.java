//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/clients/datatransfer/DownloadRequestFileLimited.java,v 1.1 2015/01/06 11:07:53 heller Exp $
package de.mendelson.util.clientserver.clients.datatransfer;

import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Msg for the client server protocol
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class DownloadRequestFileLimited extends DownloadRequestFile implements Serializable{

    private long maxSize = 0;

    @Override
    public String toString(){
        return( "Download request file limited" );
    }

    /**
     * @return the maxSize
     */
    public long getMaxSize() {
        return maxSize;
    }

    /**
     * @param maxSize the maxSize to set
     */
    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

}
