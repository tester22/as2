//$Header: /cvsroot/mec-as2/b47/de/mendelson/comm/as2/message/ByteStorageImplMemory.java,v 1.1 2015/01/06 11:07:40 heller Exp $
package de.mendelson.comm.as2.message;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Container that stores byte arrays in memory
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ByteStorageImplMemory implements IByteStorage {

    private byte[] buffer = null;

    public ByteStorageImplMemory() {
    }

    @Override
    /**Returns the actual stored data size*/
    public int getSize() {
        if (this.buffer == null) {
            return (0);
        }
        return (this.buffer.length);
    }

    @Override
    /**store a byte array*/
    public void put(byte[] data) {
        this.buffer = data;
    }

    @Override
    public byte[] get() {
        return (this.buffer);
    }

    @Override
    /**Returns an input stream to read directly from the underlaying buffer*/
    public InputStream getInputStream() {
        return (new ByteArrayInputStream(this.buffer));
    }

    @Override
    public void release() {
        //nop
    }
}
