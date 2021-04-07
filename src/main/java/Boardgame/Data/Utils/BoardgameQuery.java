/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data.Utils;

import Boardgame.Data.Enum.SqlComparisonOperator;
import Boardgame.Data.Models.Boardgame;
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
    
    private static final String SQL_BOARDGAME_SELECT = "SELECT id, name, release_date, designer, price FROM boardgame_table";
        
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
    
    private static ArrayList<Boardgame> convertQueryResultToList(ResultSet queryResult) throws SQLException {
        ArrayList<Boardgame> boardgames = new ArrayList<>();
        
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
    
    // TO DO 2021/04/07 TomasiV Move next 2 functions to a DBUtils class
    private static String createSelectSqlQuery(final String selectQuery, final List<String> whereConditions) {        
        final String PIECE_SEPARATOR = " ";
        
        StringBuilder sqlQueryBuilder = new StringBuilder(selectQuery);
        Stack<String> queryConditionJoinOperators = new Stack<>();        
        queryConditionJoinOperators.push("WHERE");
                
        whereConditions.forEach((String where) -> {
            String joinOperator;

            if (queryConditionJoinOperators.empty()) {
                queryConditionJoinOperators.push("AND");
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
            
    public static ArrayList<Boardgame> getByFilter(Boardgame filterValues) throws SQLException {        
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
        
        String filteredQuertSql = BoardgameQuery.createSelectSqlQuery(BoardgameQuery.SQL_BOARDGAME_SELECT, whereConditions);        
        ResultSet rs = statement.executeQuery(filteredQuertSql);
        
        return BoardgameQuery.convertQueryResultToList(rs);        
    }    
}
