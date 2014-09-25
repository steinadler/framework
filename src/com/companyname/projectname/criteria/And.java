
package com.companyname.projectname.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;




public class And implements Constrainable {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private List<Constrainable> constrainables;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public And(Constrainable... constrainables) {

        this.constrainables = new ArrayList<Constrainable>();
        this.constrainables.addAll(Arrays.asList(constrainables));
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public void add(Constrainable constrainable) {

        this.constrainables.add(constrainable);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public Iterator<Constrainable> getIterator() {

        return constrainables.iterator();
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public int size() {

        return this.constrainables.size();
    }
}
