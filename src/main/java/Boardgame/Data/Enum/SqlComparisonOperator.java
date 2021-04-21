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
