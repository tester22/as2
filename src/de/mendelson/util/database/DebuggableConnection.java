//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/database/DebuggableConnection.java,v 1.1 2015/01/06 11:07:55 heller Exp $
package de.mendelson.util.database;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Database connection that could be debugged
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class DebuggableConnection implements Connection {

    private Logger logger = Logger.getLogger("de.mendelson.mbi");
    private Connection connection = null;

    /** Creates a new instance of DebuggableConnection */
    public DebuggableConnection(Connection connection) {
        this.connection = connection;
        //debug purpose: find out who requested the database connection
//        long startTime = System.currentTimeMillis();
//        String methodData = "";
//        try {
//            throw new Exception();
//        } catch (Exception e) {
//            StackTraceElement[] ste = e.getStackTrace();
//            methodData = ste[2].getClassName() + "." + ste[2].getMethodName() + "(line:" + ste[2].getLineNumber() + ")";
//        }
//        this.logger.info("Connection created in "
//                + (System.currentTimeMillis() - startTime) + "ms: Requested by "
//                + methodData);
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.connection.clearWarnings();
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }

    @Override
    public void commit() throws SQLException {
        this.connection.commit();
    }

    @Override
    public Statement createStatement() throws SQLException {
        return (new DebuggableStatement(this.connection.createStatement()));
    }

    @Override
    public Statement createStatement(int param, int param1) throws SQLException {
        return (new DebuggableStatement(this.connection.createStatement(param, param1)));
    }

    @Override
    public Statement createStatement(int param, int param1, int param2) throws SQLException {
        return (new DebuggableStatement(this.connection.createStatement(param, param1, param2)));
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return (this.connection.getAutoCommit());
    }

    @Override
    public String getCatalog() throws SQLException {
        return (this.connection.getCatalog());
    }

    @Override
    public int getHoldability() throws SQLException {
        return (this.connection.getHoldability());
    }

    @Override
    public java.sql.DatabaseMetaData getMetaData() throws SQLException {
        return (this.connection.getMetaData());
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return (this.connection.getTransactionIsolation());
    }

    @Override
    public Map getTypeMap() throws SQLException {
        return (this.connection.getTypeMap());
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return (this.connection.getWarnings());
    }

    @Override
    public boolean isClosed() throws SQLException {
        return (this.connection.isClosed());
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return (this.connection.isReadOnly());
    }

    @Override
    public String nativeSQL(String str) throws SQLException {
        return (this.connection.nativeSQL(str));
    }

    @Override
    public CallableStatement prepareCall(String str) throws SQLException {
        return (this.connection.prepareCall(str));
    }

    @Override
    public CallableStatement prepareCall(String str, int param, int param2) throws SQLException {
        return (this.connection.prepareCall(str, param, param2));
    }

    @Override
    public CallableStatement prepareCall(String str, int param, int param2, int param3) throws SQLException {
        return (this.connection.prepareCall(str, param, param2, param3));
    }

    @Override
    public PreparedStatement prepareStatement(String str) throws SQLException {
        try{
            PreparedStatement statement = this.connection.prepareStatement(str);
            return (new DebuggablePreparedStatement(str, statement));
        }
        catch( SQLSyntaxErrorException e ){
            throw new SQLSyntaxErrorException(e.getMessage() + " [" + str  + "]");
        }
    }

    @Override
    public PreparedStatement prepareStatement(String str, int param) throws SQLException {
        try{
            PreparedStatement statement = this.connection.prepareStatement(str, param);
            return (new DebuggablePreparedStatement(str, statement));
        }
        catch( SQLSyntaxErrorException e ){
            throw new SQLSyntaxErrorException(e.getMessage() + " [" + str  + "]");
        }
    }

    @Override
    public PreparedStatement prepareStatement(String str, int[] values) throws SQLException {
        try{
            PreparedStatement statement = this.connection.prepareStatement(str, values);
            return (new DebuggablePreparedStatement(str, statement));
        }
        catch( SQLSyntaxErrorException e ){
            throw new SQLSyntaxErrorException(e.getMessage() + " [" + str  + "]");
        }
    }

    @Override
    public PreparedStatement prepareStatement(String str, String[] str1) throws SQLException {
        try{
            PreparedStatement statement = this.connection.prepareStatement(str, str1);
            return (new DebuggablePreparedStatement(str, statement));
        }
        catch( SQLSyntaxErrorException e ){
            throw new SQLSyntaxErrorException(e.getMessage() + " [" + str  + "]");
        }
    }

    @Override
    public PreparedStatement prepareStatement(String str, int param, int param2) throws SQLException {
        try{
            PreparedStatement statement = this.connection.prepareStatement(str, param, param2);
            return (new DebuggablePreparedStatement(str, statement));
        }
        catch( SQLSyntaxErrorException e ){
            throw new SQLSyntaxErrorException(e.getMessage() + " [" + str  + "]");
        }
    }

    @Override
    public PreparedStatement prepareStatement(String str, int param, int param2, int param3) throws SQLException {
        try{
            PreparedStatement statement = this.connection.prepareStatement(str, param, param2, param3);
            return (new DebuggablePreparedStatement(str, statement));
        }
        catch( SQLSyntaxErrorException e ){
            throw new SQLSyntaxErrorException(e.getMessage() + " [" + str  + "]");
        }
    }

    @Override
    public void releaseSavepoint(java.sql.Savepoint savepoint) throws SQLException {
        this.connection.releaseSavepoint(savepoint);
    }

    @Override
    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        this.connection.rollback(savepoint);
    }

    @Override
    public void setAutoCommit(boolean param) throws SQLException {
        this.connection.setAutoCommit(param);
    }

    @Override
    public void setCatalog(String str) throws SQLException {
        this.connection.setCatalog(str);
    }

    @Override
    public void setHoldability(int param) throws SQLException {
        this.connection.setHoldability(param);
    }

    @Override
    public void setReadOnly(boolean param) throws SQLException {
        this.connection.setReadOnly(param);
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return (this.connection.setSavepoint());
    }

    @Override
    public Savepoint setSavepoint(String str) throws SQLException {
        return (this.connection.setSavepoint(str));
    }

    @Override
    public void setTransactionIsolation(int param) throws SQLException {
        this.connection.setTransactionIsolation(param);
    }

    @Override
    public void setTypeMap(Map map) throws SQLException {
        this.connection.setTypeMap(map);
    }

    @Override
    public Clob createClob() throws SQLException {
        return (this.connection.createClob());
    }

    @Override
    public Blob createBlob() throws SQLException {
        return (this.connection.createBlob());
    }

    @Override
    public NClob createNClob() throws SQLException {
        return (this.connection.createNClob());
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return (this.connection.createSQLXML());
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return (this.connection.isValid(timeout));
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        this.connection.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this.connection.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return (this.connection.getClientInfo(name));
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return (this.connection.getClientInfo());
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return (this.connection.createArrayOf(typeName, elements));
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return (this.connection.createStruct(typeName, attributes));
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return (this.connection.unwrap(iface));
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (this.connection.isWrapperFor(iface));
    }

    public void setSchema(String schema) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSchema() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void abort(Executor executor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNetworkTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
