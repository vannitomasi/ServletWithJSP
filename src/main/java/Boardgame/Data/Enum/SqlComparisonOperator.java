/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data.Enum;

/**
 *
 * @author vanni
 */
public enum SqlComparisonOperator {
    CONTAINS("LIKE", true, true),
    ENDSWITH("LIKE", false, true),
    EQUALS("=", false, false),
    STARTSWITH("LIKE", true, false);

    private final String operatorString;
    private final boolean hasStartWildcar;
    private final boolean hasEndWildcar;

    // TO DO 2021/04/08 TomasiV move to a costant class
    public static final String POSTGRESQL_QUOTE = "'";
    public static final String POSTGRESQL_AND_OPERATOR_KEYWORD = "AND";
    public static final String POSTGRESQL_WHERE_CONDITION_START_KEYWORD = "WHERE";
    public static final String POSTGRESQL_WILDCAR = "%";
    
    SqlComparisonOperator(final String operatorName, final boolean hasStartWildcar, final boolean hasEndWildcar) {
        this.operatorString = operatorName;
        this.hasStartWildcar = hasStartWildcar;
        this.hasEndWildcar = hasEndWildcar;
    }

    public String getOperatorString() {
        return operatorString;
    }

    public boolean hasStartWildcar() {
        return hasStartWildcar;
    }

    public boolean hasEndWildcar() {
        return hasEndWildcar;
    }
}
