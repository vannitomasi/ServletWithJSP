/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author vanni
 */
public class SingletonDatabaseContext {
    private static final String CURRENT_COMPONENT_JNDI_TREE_NODE = "java:comp/env";
    private static final String BOARDGAME_DATABASE_JNDI_TREE_NODE = "jdbc/boardgame_database";
    
    private Connection databaseConnection;
    
    private static class SingletonDatabaseContextHelper {
        private static final SingletonDatabaseContext INSTANCE = new SingletonDatabaseContext();
    }
    
    private SingletonDatabaseContext() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(CURRENT_COMPONENT_JNDI_TREE_NODE);
            DataSource ds = (DataSource) envContext.lookup(BOARDGAME_DATABASE_JNDI_TREE_NODE);
            databaseConnection = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public static SingletonDatabaseContext getInstance() {
        return SingletonDatabaseContextHelper.INSTANCE;
    }
    
    public Connection getConnection() {
        return databaseConnection;
    }
}
