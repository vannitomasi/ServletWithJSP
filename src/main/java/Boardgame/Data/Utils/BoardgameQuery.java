/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data.Utils;

import Boardgame.Data.Enum.SqlComparisonOperator;
import Boardgame.Data.Models.Boardgame;
import Boardgame.Data.Models.IBoardgame;
import Boardgame.Data.SingletonDatabaseContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vanni
 */
public class BoardgameQuery {
    
    public static final String BOARDGAME_TABLE_NAME = "boardgame_table";
    
    public static final String BOARDGAME_TABLE_DESIGNER_COLUMN_NAME = "designer";
    public static final String BOARDGAME_TABLE_ID_COLUMN_NAME = "id";
    public static final String BOARDGAME_TABLE_NAME_COLUMN_NAME = "name";
    public static final String BOARDGAME_TABLE_PRICE_COLUMN_NAME = "price";
    public static final String BOARDGAME_TABLE_RELEASE_DATE_COLUMN_NAME = "release_date";
    public static final String BOARDGAME_TABLE_SORTED_COLUMNS_NAME =
            BOARDGAME_TABLE_NAME_COLUMN_NAME + PostgreSQLDBUtils.SET_ASSIGNAMENT_SEPARATOR + PostgreSQLDBUtils.PIECE_SEPARATOR +
            BOARDGAME_TABLE_RELEASE_DATE_COLUMN_NAME + PostgreSQLDBUtils.SET_ASSIGNAMENT_SEPARATOR + PostgreSQLDBUtils.PIECE_SEPARATOR +
            BOARDGAME_TABLE_DESIGNER_COLUMN_NAME + PostgreSQLDBUtils.SET_ASSIGNAMENT_SEPARATOR + PostgreSQLDBUtils.PIECE_SEPARATOR +
            BOARDGAME_TABLE_PRICE_COLUMN_NAME;    
    
    private static final String SQL_BOARDGAME_CREATE = "INSERT INTO " + BOARDGAME_TABLE_NAME + " (" + BOARDGAME_TABLE_SORTED_COLUMNS_NAME + ") VALUES";
    private static final String SQL_BOARDGAME_DELETE = "DELETE FROM " + BOARDGAME_TABLE_NAME;
    private static final String SQL_BOARDGAME_READ = "SELECT " +
            BOARDGAME_TABLE_ID_COLUMN_NAME + PostgreSQLDBUtils.SET_ASSIGNAMENT_SEPARATOR + PostgreSQLDBUtils.PIECE_SEPARATOR +
            BOARDGAME_TABLE_SORTED_COLUMNS_NAME + " FROM " + BOARDGAME_TABLE_NAME;
    private static final String SQL_BOARDGAME_UPDATE = "UPDATE " + BOARDGAME_TABLE_NAME;
        
    // TO DO 2021/05/04 TomasiV if possible make this method generic (and move it to DBUtils)
    private static ArrayList<IBoardgame> convertQueryResultToList(ResultSet queryResult) throws SQLException {
        ArrayList<IBoardgame> boardgames = new ArrayList<>();
        
        while (queryResult.next()) {
            Boardgame boardgame =  new Boardgame(
                    queryResult.getInt(BOARDGAME_TABLE_ID_COLUMN_NAME),
                    queryResult.getString(BOARDGAME_TABLE_NAME_COLUMN_NAME),
                    queryResult.getObject(BOARDGAME_TABLE_RELEASE_DATE_COLUMN_NAME, LocalDate.class),
                    queryResult.getString(BOARDGAME_TABLE_DESIGNER_COLUMN_NAME),
                    queryResult.getFloat(BOARDGAME_TABLE_PRICE_COLUMN_NAME)
            );
            boardgames.add(boardgame);
        }
        
        return boardgames;
    }
    
    public static boolean create(IBoardgame newValues) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> fieldValues = new ArrayList<>();
                
        fieldValues.add(newValues.getName());
        fieldValues.add(newValues.getReleaseDate().toString());
        fieldValues.add(newValues.getDesigner());
        fieldValues.add(((Float)newValues.getPrice()).toString());
        
        StringBuilder sqlQueryBuilder = new StringBuilder(BoardgameQuery.SQL_BOARDGAME_CREATE);
        sqlQueryBuilder
                .append(PostgreSQLDBUtils.INSERT_INTO_VALUES_START)
                .append(String.join(PostgreSQLDBUtils.INSERT_INTO_VALUES_SEPARATOR, fieldValues))
                .append(PostgreSQLDBUtils.INSERT_INTO_VALUES_END);
        int rs = statement.executeUpdate(sqlQueryBuilder.toString());
        
        return rs > 0;
    }    
    
    public static boolean delete(int boardgameId) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> whereConditions = new ArrayList<>();
        
        if (boardgameId > 0) {
            whereConditions.add(DBUtils
                    .createSqlCondition(BOARDGAME_TABLE_ID_COLUMN_NAME,
                            ((Integer)boardgameId).toString(), SqlComparisonOperator.EQUALS));
        }
        
        String filteredQuerySql = DBUtils.createSelectOrDeleteSqlQuery(BoardgameQuery.SQL_BOARDGAME_DELETE, whereConditions);
        int rs = statement.executeUpdate(filteredQuerySql);
        
        return rs > 0;
    }
    
    public static ArrayList<IBoardgame> getByFilter(IBoardgame filterValues) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> whereConditions = new ArrayList<>();
        
        if (!filterValues.getDesigner().isEmpty()) {
            whereConditions.add(DBUtils
                    .createSqlCondition(BOARDGAME_TABLE_DESIGNER_COLUMN_NAME,
                            filterValues.getDesigner(), SqlComparisonOperator.EQUALS));
        }
        
        if (filterValues.getId() > 0) {
            whereConditions.add(DBUtils
                    .createSqlCondition(BOARDGAME_TABLE_ID_COLUMN_NAME,
                            ((Integer)filterValues.getId()).toString(), SqlComparisonOperator.EQUALS));
        }
        
        if (!filterValues.getName().isEmpty()) {
            whereConditions.add(DBUtils
                    .createSqlCondition(BOARDGAME_TABLE_NAME_COLUMN_NAME,
                            filterValues.getName(), SqlComparisonOperator.EQUALS));
        }
        
        if (filterValues.getPrice() > 0) {
            whereConditions.add(DBUtils
                    .createSqlCondition(BOARDGAME_TABLE_PRICE_COLUMN_NAME,
                            ((Float)filterValues.getPrice()).toString(), SqlComparisonOperator.EQUALS));
        }
        
        if (filterValues.getReleaseDate() != LocalDate.MIN) {
            whereConditions.add(DBUtils
                    .createSqlCondition(BOARDGAME_TABLE_RELEASE_DATE_COLUMN_NAME,
                            filterValues.getReleaseDate().toString(), SqlComparisonOperator.EQUALS));
        }
        
        String filteredQuerySql = DBUtils.createSelectOrDeleteSqlQuery(BoardgameQuery.SQL_BOARDGAME_READ, whereConditions);
        ResultSet rs = statement.executeQuery(filteredQuerySql);
        
        return BoardgameQuery.convertQueryResultToList(rs);
    }
    
    public static IBoardgame getById(int boardgameId) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> whereConditions = new ArrayList<>();
        
        if (boardgameId > 0) {
            whereConditions.add(DBUtils.createSqlCondition(BOARDGAME_TABLE_ID_COLUMN_NAME, ((Integer)boardgameId).toString(), SqlComparisonOperator.EQUALS));
        }
        
        String filteredQuerySql = DBUtils.createSelectOrDeleteSqlQuery(BoardgameQuery.SQL_BOARDGAME_READ, whereConditions);
        ResultSet queryResult = statement.executeQuery(filteredQuerySql);
        queryResult.next();
        Boardgame boardgame =  new Boardgame(
                    queryResult.getInt(BOARDGAME_TABLE_ID_COLUMN_NAME),
                    queryResult.getString(BOARDGAME_TABLE_NAME_COLUMN_NAME),
                    queryResult.getObject(BOARDGAME_TABLE_RELEASE_DATE_COLUMN_NAME, LocalDate.class),
                    queryResult.getString(BOARDGAME_TABLE_DESIGNER_COLUMN_NAME),
                    queryResult.getFloat(BOARDGAME_TABLE_PRICE_COLUMN_NAME)
        );
        
        return boardgame;
    }
    
    public static boolean update(IBoardgame newValues) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> setAssignments = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        
        if (newValues.getId() > 0) {
            whereConditions.add(DBUtils
                    .createSqlCondition(BOARDGAME_TABLE_ID_COLUMN_NAME,
                            ((Integer)newValues.getId()).toString(), SqlComparisonOperator.EQUALS));
        }
        
        if (!newValues.getDesigner().isEmpty()) {
            setAssignments.add(DBUtils
                    .createSetAssignments(BOARDGAME_TABLE_DESIGNER_COLUMN_NAME,
                            newValues.getDesigner()));
        }
        
        if (!newValues.getName().isEmpty()) {
            setAssignments.add(DBUtils
                    .createSetAssignments(BOARDGAME_TABLE_NAME_COLUMN_NAME,
                            newValues.getName()));
        }
        
        if (newValues.getPrice() > 0) {
            setAssignments.add(DBUtils
                    .createSetAssignments(BOARDGAME_TABLE_PRICE_COLUMN_NAME,
                            ((Float)newValues.getPrice()).toString()));
        }
        
        if (newValues.getReleaseDate() != LocalDate.MIN) {
            setAssignments.add(DBUtils
                    .createSetAssignments(BOARDGAME_TABLE_RELEASE_DATE_COLUMN_NAME,
                            newValues.getReleaseDate().toString()));
        }
        
        String filteredQuerySql = DBUtils.createUpdateSqlQuery(BoardgameQuery.SQL_BOARDGAME_UPDATE, setAssignments, whereConditions);
        int rs = statement.executeUpdate(filteredQuerySql);
        
        return rs > 0;
    }
}
