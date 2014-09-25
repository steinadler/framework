
package com.companyname.projectname.criteria;



public class Order {

    public static final int ORDER_ASCENDING  = 0;
    public static final int ORDER_DESCENDING = 1;

    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private String column;
    private int    method;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Order(String column, int method) {

        this.column = column;
        this.method = method;
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public String getColumn() {

        return column;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public int getMethod() {

        return method;
    }
}
