/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data.Utils;

import Boardgame.Data.Enum.SqlComparisonOperator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author vanni
 */
public class DBUtils {
    
    public static String createSelectOrDeleteSqlQuery(final String selectQuery, final List<String> whereConditions) {        
        StringBuilder sqlQueryBuilder = new StringBuilder(selectQuery);
        Stack<String> queryConditionJoinOperators = new Stack<>();        
        queryConditionJoinOperators.push(PostgreSQLDBUtils.WHERE_CONDITION_START_KEYWORD);
                
        whereConditions.forEach((String where) -> {
            String joinOperator;

            if (queryConditionJoinOperators.empty()) {
                queryConditionJoinOperators.push(PostgreSQLDBUtils.AND_OPERATOR_KEYWORD);
            }
                        
            joinOperator = queryConditionJoinOperators.pop();
            
            sqlQueryBuilder
                    .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                    .append(joinOperator)
                    .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                    .append(where);
        });
        
        return sqlQueryBuilder.toString();
    }
    
    public static String createUpdateSqlQuery(final String updateQuery, final List<String> setAssignments, final List<String> whereConditions) {
        StringBuilder sqlQueryBuilder = new StringBuilder(updateQuery);
        sqlQueryBuilder.append(PostgreSQLDBUtils.PIECE_SEPARATOR);
        sqlQueryBuilder.append(PostgreSQLDBUtils.UPDATE_SET_START_KEYWORD);
        setAssignments.forEach((String set) -> {
            sqlQueryBuilder
                    .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                    .append(set)
                    .append(PostgreSQLDBUtils.SET_ASSIGNAMENT_SEPARATOR)
                    .append(PostgreSQLDBUtils.PIECE_SEPARATOR);
        });
        sqlQueryBuilder.deleteCharAt(sqlQueryBuilder.lastIndexOf(PostgreSQLDBUtils.SET_ASSIGNAMENT_SEPARATOR));
        Stack<String> queryConditionJoinOperators = new Stack<>();        
        queryConditionJoinOperators.push(PostgreSQLDBUtils.WHERE_CONDITION_START_KEYWORD);
                
        whereConditions.forEach((String where) -> {
            String joinOperator;

            if (queryConditionJoinOperators.empty()) {
                queryConditionJoinOperators.push(PostgreSQLDBUtils.AND_OPERATOR_KEYWORD);
            }
                        
            joinOperator = queryConditionJoinOperators.pop();
            
            sqlQueryBuilder
                    .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                    .append(joinOperator)
                    .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                    .append(where);
        });
        
        return sqlQueryBuilder.toString();
    }
    
    public static String createSetAssignments(final String fieldName, final String fieldValue) {
        StringBuilder sqlConditionBuilder = new StringBuilder(fieldName);
        sqlConditionBuilder
                .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                .append(SqlComparisonOperator.EQUALS.getOperatorString())
                .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                .append(PostgreSQLDBUtils.QUOTE_CHARACTER);
        sqlConditionBuilder.append(fieldValue);
        sqlConditionBuilder.append(PostgreSQLDBUtils.QUOTE_CHARACTER);
        
        return sqlConditionBuilder.toString();
    }
    
    public static String createSqlCondition(final String fieldName, final String fieldValue, final SqlComparisonOperator comparisonOperator) {
        StringBuilder sqlConditionBuilder = new StringBuilder(fieldName);        
        sqlConditionBuilder
                .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                .append(comparisonOperator.getOperatorString())
                .append(PostgreSQLDBUtils.PIECE_SEPARATOR)
                .append(PostgreSQLDBUtils.QUOTE_CHARACTER);
        if (comparisonOperator.hasStartWildcar()) {
            sqlConditionBuilder.append(PostgreSQLDBUtils.WILDCAR_CHARACTER);
        }
        
        sqlConditionBuilder.append(fieldValue);
        if (comparisonOperator.hasEndWildcar()) {
            sqlConditionBuilder.append(PostgreSQLDBUtils.WILDCAR_CHARACTER);
        }
        
        sqlConditionBuilder.append(PostgreSQLDBUtils.QUOTE_CHARACTER);
        
        return sqlConditionBuilder.toString();
    }
}
