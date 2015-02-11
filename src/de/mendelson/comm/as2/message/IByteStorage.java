//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/message/IByteStorage.java,v 1.1 2015/01/06 11:07:40 heller Exp $
package de.mendelson.comm.as2.message;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Interface for the byte storage implementations
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public interface IByteStorage extends Serializable {

    /**Returns the actual stored data size*/
    public int getSize();

    /**store a byte array*/
    public void put(byte[] data) throws Exception;

    /**Returns the stored data*/
    public byte[] get() throws Exception;

    /**Releases the storage*/
    public void release();

    /**Returns an input stream to read directly from the underlaying storage*/
    public InputStream getInputStream() throws Exception;
}
