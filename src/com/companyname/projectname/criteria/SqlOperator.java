
package com.companyname.projectname.criteria;




public class SqlOperator {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static Constrainable and(Constrainable constrainable) {

        return new And(constrainable);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable and(Constrainable... constrainables) {

        return new And(constrainables);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable and(Constrainable constrainable1, Constrainable constrainable2) {

        return new And(constrainable1, constrainable2);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable and(Object leftSide, Object rightSide) {

        return new And(new Constraint(leftSide, rightSide));
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Order asc(String column) {

        return new Order(column, Order.ORDER_ASCENDING);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Order desc(String column) {

        return new Order(column, Order.ORDER_DESCENDING);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable eq(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable gt(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.GREATER_THAN);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable gte(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.GREATER_THAN_OR_EQUAL);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable like(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.LIKE);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable likeLower(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.LIKE_LOWER);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable lt(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.LESS_THAN);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable lte(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.LESS_THAN_OR_EQUAL);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable ne(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.NOT_EQUAL);
    }
    
    
    
    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable nul(Object leftSide) {

        return new Constraint(leftSide, Constraint.IS_NULL, Constraint.IS_NULL);
    }
    
    
    
    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable nnul(Object leftSide) {

        return new Constraint(leftSide, Constraint.IS_NOT_NULL, Constraint.IS_NOT_NULL);
    }    
    
    
    
    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable in(Object leftSide, Object rightSide) {

        return new Constraint(leftSide, rightSide, Constraint.IN);
    }        



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable or(Constrainable constrainable) {

        return new Or(constrainable);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable or(Constrainable... constrainables) {

        return new Or(constrainables);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable or(Constrainable constrainable1, Constrainable constrainable2) {

        return new Or(constrainable1, constrainable2);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable or(Object leftSide, Object rightSide) {

        return new Or(new Constraint(leftSide, rightSide));
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable where(Constrainable constrainable) {

        return new And(constrainable);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable where(Constrainable... constrainables) {

        return new And(constrainables);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Constrainable where(String leftSide, String rightSide) {

        return new And(new Constraint(leftSide, rightSide));
    }
}
