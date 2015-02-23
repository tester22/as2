//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/clientserver/codec/ClientServerDecoder.java,v 1.1 2015/01/06 11:07:54 heller Exp $
package de.mendelson.util.clientserver.codec;

import de.mendelson.util.MecResourceBundle;
import java.io.ByteArrayInputStream;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Decodes a command from the line
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ClientServerDecoder extends CumulativeProtocolDecoder {

    private Logger logger;
    private MecResourceBundle rb;

    public ClientServerDecoder(Logger logger) {
        super();
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    de.mendelson.util.clientserver.codec.ResourceBundleServerDecoder.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        this.logger = logger;
    }

    private int decodeLengthHeader32Bit(byte[] header32Bit) {
        BigInteger lengthValue = new BigInteger(header32Bit);
        return (lengthValue.intValue());
    }

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput decoderOutput) throws Exception {
        //store tha current position to rewind later
        int position = in.position();
        byte[] headerData = new byte[4];
        if (in.remaining() >= 4) {
            in.get(headerData);
        } else {
            //buffer has not been consumed: return false
            return (false);
        }
        int contentLength = this.decodeLengthHeader32Bit(headerData);
        //bail out and write back the buffer if the buffer does not contain enough bytes
        if (in.remaining() < contentLength) {
            //rewind the input buffer, next attempt will read the data again
            in.position(position);
            //buffer has not been consumed: return false
            return (false);
        }
        //read the full object into a byte array
        byte[] objectBuffer = new byte[contentLength];
        in.get(objectBuffer);
        ByteArrayInputStream objectInStream = new ByteArrayInputStream(objectBuffer);
        ObjectInput objectInput = new ObjectInputStream(objectInStream);
        try {
            Object object = objectInput.readObject();
            objectInput.close();
            decoderOutput.write(object);
        } catch (InvalidClassException ex) {
            this.logger.severe(this.rb.getResourceString("client.incompatible"));
        }
        //buffer has been consumed: return true
        return (true);
    }
}
