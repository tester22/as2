//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/database/DebuggablePreparedStatement.java,v 1.1 2015/01/06 11:07:55 heller Exp $
package de.mendelson.util.database;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Database statement that could be debugged
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class DebuggablePreparedStatement extends DebuggableStatement implements PreparedStatement {

    private PreparedStatement statement;
    private String query;
    /**Map that contains parameter for the prepared statement*/
    private Map<Integer, String> map = new HashMap<Integer, String>();

    public DebuggablePreparedStatement(String query, PreparedStatement statement) {
        super(statement);
        this.statement = statement;
        this.query = query;
    }

    public String getQueryWithParameter() throws SQLException {        
        StringBuilder builder = new StringBuilder();
        builder.append("Query:\n");
        builder.append(query);
        builder.append("\n");
        builder.append("Parameter:\n");
        ParameterMetaData parameterMeta = this.statement.getParameterMetaData();
        for (int i = 0; i < parameterMeta.getParameterCount(); i++) {
            Integer key = Integer.valueOf(i + 1);
            if (this.map.containsKey(key)) {
                builder.append("(").append(key).append(")");
                builder.append(" [").append(parameterMeta.getParameterTypeName(key.intValue())).append("]");
                builder.append(":");
                builder.append(this.map.get(key));
                builder.append("\n");
            }
        }
        return (builder.toString());
    }

    @Override
    public void addBatch() throws SQLException {
        this.statement.addBatch();
    }

    @Override
    public void clearParameters() throws SQLException {
        this.statement.clearParameters();
    }

    @Override
    public boolean execute() throws SQLException {
        boolean result = this.statement.execute();
        //SystemActivityHandler.logSQLQueryParameter(this);
        return (result);
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        ResultSet result = this.statement.executeQuery();
        //SystemActivityHandler.logSQLQueryParameter(this);
        return (result);
    }

    @Override
    public int executeUpdate() throws SQLException {
        int updatedRows = this.statement.executeUpdate();
        //SystemActivityHandler.logSQLQueryParameter(this);
        return (updatedRows);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return (this.statement.getMetaData());
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return (this.statement.getParameterMetaData());
    }

    @Override
    public void setArray(int param, Array array) throws SQLException {
        this.statement.setArray(param, array);
    }

    @Override
    public void setAsciiStream(int param, java.io.InputStream inputStream, int param2) throws SQLException {
        this.statement.setAsciiStream(param, inputStream, param2);
    }

    @Override
    public void setBigDecimal(int param, BigDecimal bigDecimal) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(bigDecimal));
        this.statement.setBigDecimal(param, bigDecimal);
    }

    @Override
    public void setBinaryStream(int param, InputStream inputStream, int param2) throws SQLException {
        this.map.put(new Integer(param), "<stream>");
        this.statement.setBinaryStream(param, inputStream, param2);
    }

    @Override
    public void setBlob(int param, Blob blob) throws SQLException {
        this.statement.setBlob(param, blob);
    }

    @Override
    public void setBoolean(int param, boolean param1) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(param1));
        this.statement.setBoolean(param, param1);
    }

    @Override
    public void setByte(int param, byte param1) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(param1));
        this.statement.setByte(param, param1);
    }

    @Override
    public void setBytes(int param, byte[] values) throws SQLException {
        this.map.put(new Integer(param), "<bytes>");
        this.statement.setBytes(param, values);
    }

    @Override
    public void setCharacterStream(int param, Reader reader, int param2) throws SQLException {
        this.map.put(new Integer(param), "<charstream>");
        this.statement.setCharacterStream(param, reader, param2);
    }

    @Override
    public void setClob(int param, Clob clob) throws SQLException {
        this.map.put(new Integer(param), "<clob>");
        this.statement.setClob(param, clob);
    }

    @Override
    public void setDate(int param, Date date) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(date));
        this.statement.setDate(param, date);
    }

    @Override
    public void setDate(int param, Date date, Calendar calendar) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(date));
        this.statement.setDate(param, date, calendar);
    }

    @Override
    public void setDouble(int param, double param1) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(param1));
        this.statement.setDouble(param, param1);
    }

    @Override
    public void setFloat(int param, float param1) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(param1));
        this.statement.setFloat(param, param1);
    }

    @Override
    public void setInt(int param, int param1) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(param1));
        this.statement.setInt(param, param1);
    }

    @Override
    public void setLong(int param, long param1) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(param1));
        this.statement.setLong(param, param1);
    }

    @Override
    public void setNull(int param, int param1) throws SQLException {
        this.map.put(new Integer(param), "null");
        this.statement.setNull(param, param1);
    }

    @Override
    public void setNull(int param, int param1, String str) throws SQLException {
        this.map.put(new Integer(param), "null");
        this.statement.setNull(param, param1, str);
    }

    @Override
    public void setObject(int param, Object obj) throws SQLException {
        String className = "null";
        if( obj != null ){
            className = obj.getClass().getName();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<obj> (" );
        builder.append( className );        
        if( obj instanceof List){
            builder.append( " size=");
            builder.append(((List)obj).size());
        }
        builder.append( ")");
        this.map.put(new Integer(param), builder.toString());
        this.statement.setObject(param, obj);
    }

    @Override
    public void setObject(int param, Object obj, int param2) throws SQLException {
        String className = "null";
        if( obj != null ){
            className = obj.getClass().getName();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<obj> (" );
        builder.append( className );        
        if( obj instanceof List){
            builder.append( " size=");
            builder.append(((List)obj).size());
        }
        builder.append( ")");
        this.map.put(new Integer(param), builder.toString());
        this.statement.setObject(param, obj, param2);
    }

    @Override
    public void setObject(int param, Object obj, int param2, int param3) throws SQLException {
        String className = "null";
        if( obj != null ){
            className = obj.getClass().getName();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<obj> (" );
        builder.append( className );        
        if( obj instanceof List){
            builder.append( " size=");
            builder.append(((List)obj).size());
        }
        builder.append( ")");
        this.map.put(new Integer(param), builder.toString());
        this.statement.setObject(param, obj, param2, param3);
    }

    @Override
    public void setRef(int param, Ref ref) throws SQLException {
        this.map.put(new Integer(param), "<ref>");
        this.statement.setRef(param, ref);
    }

    @Override
    public void setShort(int param, short param1) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(param1));
        this.statement.setShort(param, param1);
    }

    @Override
    public void setString(int param, String str) throws SQLException {
        this.map.put(new Integer(param), str);
        this.statement.setString(param, str);
    }

    @Override
    public void setTime(int param, Time time) throws SQLException {
        this.map.put(new Integer(param), time.toString());
        this.statement.setTime(param, time);
    }

    @Override
    public void setTime(int param, Time time, Calendar calendar) throws SQLException {
        this.map.put(new Integer(param), String.valueOf(time));
        this.statement.setTime(param, time, calendar);
    }

    @Override
    public void setTimestamp(int param, Timestamp timestamp) throws SQLException {
        this.map.put(new Integer(param), timestamp.toString());
        this.statement.setTimestamp(param, timestamp);
    }

    @Override
    public void setTimestamp(int param, Timestamp timestamp, Calendar calendar) throws SQLException {
        this.map.put(new Integer(param), timestamp.toString());
        this.statement.setTimestamp(param, timestamp, calendar);
    }

    @Override
    public void setURL(int param, URL uRL) throws SQLException {
        this.map.put(new Integer(param), uRL.toString());
        this.statement.setURL(param, uRL);
    }

    @Override
    public void setUnicodeStream(int param, InputStream inputStream, int param2) throws SQLException {
        this.map.put(new Integer(param), "<unicode_stream>");
        this.statement.setUnicodeStream(param, inputStream, param2);
    }

    @Override
    public boolean isClosed() throws SQLException {
        return (this.statement.isClosed());
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        this.statement.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return (this.statement.isPoolable());
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return (this.statement.unwrap(iface));
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (this.statement.isWrapperFor(iface));
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.statement.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<NString>");
        this.statement.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<NCharacterStream>");
        this.statement.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<NClob>");
        this.statement.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<Clob>");
        this.statement.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<Blob>");
        this.statement.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<NClob>");
        this.statement.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<SQLXML>");
        this.statement.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<AsciiStream>");
        this.statement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<BinaryStream>");
        this.statement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<CharacterStream>");
        this.statement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<AsciiStream>");
        this.statement.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<BinaryStream>");
        this.statement.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<CharacterStream>");
        this.statement.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<NCharacterStream>");
        this.statement.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<Clob>");
        this.statement.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<Blob>");
        this.statement.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.map.put(new Integer(parameterIndex), "<NClob>");
        this.statement.setNClob(parameterIndex, reader);
    }

    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
