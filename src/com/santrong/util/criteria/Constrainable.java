
package com.santrong.util.criteria;

import java.util.Iterator;



public interface Constrainable {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public void add(Constrainable constrainable);



    //~ ----------------------------------------------------------------------------------------------------------------

    public Iterator<Constrainable> getIterator();



    //~ ----------------------------------------------------------------------------------------------------------------

    public int size();
}
