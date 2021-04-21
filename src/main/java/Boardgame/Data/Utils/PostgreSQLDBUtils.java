/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data.Utils;

/**
 *
 * @author vanni
 */
public class PostgreSQLDBUtils {
    
    public static final String AND_OPERATOR_KEYWORD = "AND";
    public static final String PIECE_SEPARATOR = " ";
    public static final String QUOTE_CHARACTER = "'";
    public static final String SET_ASSIGNAMENT_SEPARATOR = ",";
    public static final String UPDATE_SET_START_KEYWORD = "SET";
    public static final String INSERT_INTO_VALUES_END = QUOTE_CHARACTER + ")";
    public static final String INSERT_INTO_VALUES_SEPARATOR = QUOTE_CHARACTER + "," + PIECE_SEPARATOR + QUOTE_CHARACTER;
    public static final String INSERT_INTO_VALUES_START = PIECE_SEPARATOR + "(" + QUOTE_CHARACTER;
    public static final String WILDCAR_CHARACTER = "%";
    public static final String WHERE_CONDITION_START_KEYWORD = "WHERE";
}
