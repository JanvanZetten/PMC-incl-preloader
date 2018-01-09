/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

/**
 *
 * @author janvanzetten
 */
public class DBConnecter {
    private SQLServerDataSource dataSource;

    /**
     * Constructor saves connection information.
     */
    public DBConnecter() {
        dataSource = new SQLServerDataSource();

        dataSource.setServerName("EASV-DB2");
        dataSource.setPortNumber(1433);
        dataSource.setDatabaseName("name"); //enter database name
        dataSource.setUser("CS2017A_x_java");//enter number of user
        dataSource.setPassword("javajava");
    }

    /**
     * Gets connection.
     *
     * @return SQLServerDataSource.
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
