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
import java.util.Stack;

/**
 *
 * @author vanni
 */
public class BoardgameQuery {    
    
    private static final String SQL_BOARDGAME_CREATE = "INSERT INTO boardgame_table (name, release_date, designer, price) VALUES";
    private static final String SQL_BOARDGAME_DELETE = "DELETE FROM boardgame_table";
    private static final String SQL_BOARDGAME_READ = "SELECT id, name, release_date, designer, price FROM boardgame_table";
    private static final String SQL_BOARDGAME_UPDATE = "UPDATE boardgame_table";
        
    // TO DO 2021/05/04 TomasiV move to an extension class 
    private <T> List<T> convertQueryResultToListToEdit(ResultSet queryResult) throws SQLException {
        //https://stackoverflow.com/questions/21956042/mapping-a-jdbc-resultset-to-an-object/21956222
        List<T> boardgames = new ArrayList<T>();
        
        while (queryResult.next()) {
            Object listElementAsObject = queryResult.getObject(queryResult.getRow());
            T listElement = (T)listElementAsObject;
            boardgames.add(listElement);
        }       
        
        return boardgames;
    }
    
    private static ArrayList<IBoardgame> convertQueryResultToList(ResultSet queryResult) throws SQLException {
        ArrayList<IBoardgame> boardgames = new ArrayList<>();
        
        while (queryResult.next()) {
            Boardgame boardgame =  new Boardgame(
                    queryResult.getInt("id"),
                    queryResult.getString("name"),
                    queryResult.getObject("release_date", LocalDate.class),
                    queryResult.getString("designer"),
                    queryResult.getFloat("price")
            );
            boardgames.add(boardgame);
        }
        
        return boardgames;
    }
    
    public static boolean create(IBoardgame newValues) throws SQLException {
        final String PIECE_SEPARATOR = " ";
        final String POSTGRES_QUOTE_CHARACTER = "'";
        final String VALUES_START = PIECE_SEPARATOR + "(" + POSTGRES_QUOTE_CHARACTER;
        final String VALUES_END = POSTGRES_QUOTE_CHARACTER + ")";
        final String VALUES_SEPARATOR = POSTGRES_QUOTE_CHARACTER + "," + PIECE_SEPARATOR + POSTGRES_QUOTE_CHARACTER;
        
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> fieldValues = new ArrayList<>();
                
        fieldValues.add(newValues.getName());
        fieldValues.add(newValues.getReleaseDate().toString());
        fieldValues.add(newValues.getDesigner());
        fieldValues.add(((Float)newValues.getPrice()).toString());
        
        StringBuilder sqlQueryBuilder = new StringBuilder(BoardgameQuery.SQL_BOARDGAME_CREATE);
        sqlQueryBuilder
                .append(VALUES_START)
                .append(String.join(VALUES_SEPARATOR, fieldValues))
                .append(VALUES_END);
        int rs = statement.executeUpdate(sqlQueryBuilder.toString());
        
        return rs > 0;
    }
    
    // TO DO 2021/04/07 TomasiV Move next 3 functions to a DBUtils class
    private static String createSelectOrDeleteSqlQuery(final String selectQuery, final List<String> whereConditions) {        
        final String PIECE_SEPARATOR = " ";
        
        StringBuilder sqlQueryBuilder = new StringBuilder(selectQuery);
        Stack<String> queryConditionJoinOperators = new Stack<>();        
        queryConditionJoinOperators.push(SqlComparisonOperator.POSTGRESQL_WHERE_CONDITION_START_KEYWORD);
                
        whereConditions.forEach((String where) -> {
            String joinOperator;

            if (queryConditionJoinOperators.empty()) {
                queryConditionJoinOperators.push(SqlComparisonOperator.POSTGRESQL_AND_OPERATOR_KEYWORD);
            }
                        
            joinOperator = queryConditionJoinOperators.pop();
            
            sqlQueryBuilder
                    .append(PIECE_SEPARATOR)
                    .append(joinOperator)
                    .append(PIECE_SEPARATOR)
                    .append(where);
        });
        
        return sqlQueryBuilder.toString();
    }
    
    private static String createUpdateSqlQuery(final String updateQuery, final List<String> setAssignments, final List<String> whereConditions) {
        final String PIECE_SEPARATOR = " ";
        final String SET_ASSIGNAMENT_SEPARATOR = ",";
        
        StringBuilder sqlQueryBuilder = new StringBuilder(updateQuery);
        sqlQueryBuilder.append(PIECE_SEPARATOR);
        sqlQueryBuilder.append(SqlComparisonOperator.POSTGRESQL_UPDATE_SET_START_KEYWORD);
        setAssignments.forEach((String set) -> {
            sqlQueryBuilder
                    .append(PIECE_SEPARATOR)
                    .append(set)
                    .append(SET_ASSIGNAMENT_SEPARATOR)
                    .append(PIECE_SEPARATOR);
        });
        sqlQueryBuilder.deleteCharAt(sqlQueryBuilder.lastIndexOf(SET_ASSIGNAMENT_SEPARATOR));
        Stack<String> queryConditionJoinOperators = new Stack<>();        
        queryConditionJoinOperators.push(SqlComparisonOperator.POSTGRESQL_WHERE_CONDITION_START_KEYWORD);
                
        whereConditions.forEach((String where) -> {
            String joinOperator;

            if (queryConditionJoinOperators.empty()) {
                queryConditionJoinOperators.push(SqlComparisonOperator.POSTGRESQL_AND_OPERATOR_KEYWORD);
            }
                        
            joinOperator = queryConditionJoinOperators.pop();
            
            sqlQueryBuilder
                    .append(PIECE_SEPARATOR)
                    .append(joinOperator)
                    .append(PIECE_SEPARATOR)
                    .append(where);
        });
        
        return sqlQueryBuilder.toString();
    }
    
    private static String createSetAssignments(final String fieldName, final String fieldValue) {
        final String PIECE_SEPARATOR = " ";
        
        StringBuilder sqlConditionBuilder = new StringBuilder(fieldName);
        sqlConditionBuilder
                .append(PIECE_SEPARATOR)
                .append(SqlComparisonOperator.EQUALS.getOperatorString())
                .append(PIECE_SEPARATOR)
                .append(SqlComparisonOperator.POSTGRESQL_QUOTE);
        sqlConditionBuilder.append(fieldValue);
        sqlConditionBuilder.append(SqlComparisonOperator.POSTGRESQL_QUOTE);
        
        return sqlConditionBuilder.toString();
    }
    
    private static String createSqlCondition(final String fieldName, final String fieldValue, final SqlComparisonOperator comparisonOperator) {
        final String PIECE_SEPARATOR = " ";
        
        StringBuilder sqlConditionBuilder = new StringBuilder(fieldName);        
        sqlConditionBuilder
                .append(PIECE_SEPARATOR)
                .append(comparisonOperator.getOperatorString())
                .append(PIECE_SEPARATOR)
                .append(SqlComparisonOperator.POSTGRESQL_QUOTE);
        if (comparisonOperator.hasStartWildcar()) {
            sqlConditionBuilder.append(SqlComparisonOperator.POSTGRESQL_WILDCAR);
        }
        
        sqlConditionBuilder.append(fieldValue);
        if (comparisonOperator.hasEndWildcar()) {
            sqlConditionBuilder.append(SqlComparisonOperator.POSTGRESQL_WILDCAR);
        }
        
        sqlConditionBuilder.append(SqlComparisonOperator.POSTGRESQL_QUOTE);
        
        return sqlConditionBuilder.toString();
    }
    
    public static boolean delete(int boardgameId) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> whereConditions = new ArrayList<>();
        
        if (boardgameId > 0) {
            whereConditions.add(BoardgameQuery.createSqlCondition("id", ((Integer)boardgameId).toString(), SqlComparisonOperator.EQUALS));
        }
        
        String filteredQuerySql = BoardgameQuery.createSelectOrDeleteSqlQuery(BoardgameQuery.SQL_BOARDGAME_DELETE, whereConditions);
        int rs = statement.executeUpdate(filteredQuerySql);
        
        return rs > 0;
    }
    
    public static ArrayList<IBoardgame> getByFilter(IBoardgame filterValues) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> whereConditions = new ArrayList<>();
        
        if (!filterValues.getDesigner().isEmpty()) {
            whereConditions.add(BoardgameQuery.createSqlCondition("designer", filterValues.getDesigner(), SqlComparisonOperator.EQUALS));
        }
        
        if (filterValues.getId() > 0) {
            whereConditions.add(BoardgameQuery.createSqlCondition("id", ((Integer)filterValues.getId()).toString(), SqlComparisonOperator.EQUALS));
        }
        
        if (!filterValues.getName().isEmpty()) {
            whereConditions.add(BoardgameQuery.createSqlCondition("name", filterValues.getName(), SqlComparisonOperator.EQUALS));
        }
        
        if (filterValues.getPrice() > 0) {
            whereConditions.add(BoardgameQuery.createSqlCondition("price", ((Float)filterValues.getPrice()).toString(), SqlComparisonOperator.EQUALS));
        }
        
        if (filterValues.getReleaseDate() != LocalDate.MIN) {
            whereConditions.add(BoardgameQuery.createSqlCondition("release_date", filterValues.getReleaseDate().toString(), SqlComparisonOperator.EQUALS));
        }
        
        String filteredQuerySql = BoardgameQuery.createSelectOrDeleteSqlQuery(BoardgameQuery.SQL_BOARDGAME_READ, whereConditions);
        ResultSet rs = statement.executeQuery(filteredQuerySql);
        
        return BoardgameQuery.convertQueryResultToList(rs);
    }
    
    public static IBoardgame getById(int boardgameId) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> whereConditions = new ArrayList<>();
        
        if (boardgameId > 0) {
            whereConditions.add(BoardgameQuery.createSqlCondition("id", ((Integer)boardgameId).toString(), SqlComparisonOperator.EQUALS));
        }
        
        String filteredQuerySql = BoardgameQuery.createSelectOrDeleteSqlQuery(BoardgameQuery.SQL_BOARDGAME_READ, whereConditions);
        ResultSet queryResult = statement.executeQuery(filteredQuerySql);
        queryResult.next();
        Boardgame boardgame =  new Boardgame(
                    queryResult.getInt("id"),
                    queryResult.getString("name"),
                    queryResult.getObject("release_date", LocalDate.class),
                    queryResult.getString("designer"),
                    queryResult.getFloat("price")
        );
        
        return boardgame;
    }
    
    public static boolean update(IBoardgame newValues) throws SQLException {
        Connection conn = SingletonDatabaseContext.getInstance().getConnection();
        Statement statement = conn.createStatement();
        List<String> setAssignments = new ArrayList<>();
        List<String> whereConditions = new ArrayList<>();
        
        if (newValues.getId() > 0) {
            whereConditions.add(BoardgameQuery.createSqlCondition("id", ((Integer)newValues.getId()).toString(), SqlComparisonOperator.EQUALS));
        }
        
        if (!newValues.getDesigner().isEmpty()) {
            setAssignments.add(BoardgameQuery.createSetAssignments("designer", newValues.getDesigner()));
        }
        
        if (!newValues.getName().isEmpty()) {
            setAssignments.add(BoardgameQuery.createSetAssignments("name", newValues.getName()));
        }
        
        if (newValues.getPrice() > 0) {
            setAssignments.add(BoardgameQuery.createSetAssignments("price", ((Float)newValues.getPrice()).toString()));
        }
        
        if (newValues.getReleaseDate() != LocalDate.MIN) {
            setAssignments.add(BoardgameQuery.createSetAssignments("release_date", newValues.getReleaseDate().toString()));
        }
        
        String filteredQuerySql = BoardgameQuery.createUpdateSqlQuery(BoardgameQuery.SQL_BOARDGAME_UPDATE, setAssignments, whereConditions);
        int rs = statement.executeUpdate(filteredQuerySql);
        
        return rs > 0;
    }
}
