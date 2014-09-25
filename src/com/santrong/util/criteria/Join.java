
package com.santrong.util.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class Join {

    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    public static final int LEFT             = 0;
    public static final int RIGHT            = 1;
    public static final int INNER            = 2;



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private List<Constrainable> constrainables;
    private String				joinTable;
    private String				joinAlias;
    private int					joinType;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Join(Object table, Object alias, Constrainable constrainable) {

        this(table, alias, LEFT, constrainable);
    }



    public Join(Object table, Object alias, int type, Constrainable constrainable) {

        this.joinTable       = table.toString();
        this.joinAlias       = alias.toString();
        this.constrainables  = new ArrayList<Constrainable>();
        this.joinType        = type;
        
        this.constrainables.add(constrainable);
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public void addConstrain(Constrainable constrainable) {

    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public Iterator<Constrainable> getIterator() {

        return constrainables.iterator();
    }    
    
    

    //~ ----------------------------------------------------------------------------------------------------------------

    public String getJoinTable() {

        return joinTable;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public String getJoinAlias() {

        return joinAlias;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public int getJoinType() {

        return joinType;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public int size() {

        return constrainables.size();
    }
}
