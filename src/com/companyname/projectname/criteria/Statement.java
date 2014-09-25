
package com.companyname.projectname.criteria;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;



public class Statement extends Criteria{
	
	//~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------
	
		
	
	private static final int TYPE_STRING		= 0;
	private static final int TYPE_INTEGER		= 1;
	private static final int TYPE_DATE			= 2;
	
	

    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------
	
	
	
	private List<Entry<Integer, Object>> 			params;
	

	
    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

	
	
    public Statement(String table) {
    	super(table, "S");
    	
    	params = new ArrayList<Entry<Integer, Object>>();    	
    }



    public Statement(String table, String alias) {
    	
    	super(table, alias);
    	
    	params = new ArrayList<Entry<Integer, Object>>();
    }    
    
    public Statement(Statement stm, String alias) {
    	super(stm.toString(), alias);
    }


    
    //~ ------- [METHODS] ----------------------------------------------------------------------------------------------

    
    
    public Statement setStringParam(String s) {
    	
    	Entry<Integer, Object> param = new AbstractMap.SimpleEntry<Integer, Object>(TYPE_STRING, s);    	
    	params.add(param);
    	
    	return this;
    }
    
    
    
    //~ -----------------------------------------------------------------------------------------------------------------
    
    
    
    public Statement setIntParam(int i) {
    	
    	Entry<Integer, Object> param = new AbstractMap.SimpleEntry<Integer, Object>(TYPE_INTEGER, i);    	
    	params.add(param);    	
    	
    	return this;
    }
    
    
    
    //~ -----------------------------------------------------------------------------------------------------------------
    
    
    
//    public CriteriaStatement setDate(Date d) {
//    	return this;
//    }
    
    
    
    //~ -----------------------------------------------------------------------------------------------------------------
    
	public List<Entry<Integer, Object>> getParams() {
		return params;
	}



	public void setParams(List<Entry<Integer, Object>> params) {
		this.params = params;
	}
	
	
	
	public void addParams(List<Entry<Integer, Object>> params) {
		this.params.addAll(params);
	}
    
    //~ ------- [load the jdbc Driver then remove the comment] ----------------------------------------------------------
    public java.sql.PreparedStatement getRealStatement(java.sql.Connection con) throws java.sql.SQLException{
    	
    	java.sql.PreparedStatement stm = null;
    	int pIndex = 0;
    	
    	if (con != null){
    		
    		stm = con.prepareStatement(this.toString());
    		
    		for(Entry<Integer, Object> entry : params){
    			
    			if (entry.getKey() == TYPE_STRING) {
    				stm.setString(++pIndex, entry.getValue().toString());
    			} else if(entry.getKey() == TYPE_INTEGER) {
    				stm.setInt(++pIndex, Integer.parseInt(entry.getValue().toString()));
    			} else if(entry.getKey() == TYPE_DATE) {
    				//stm.setDate(++pIndex, new Date(entry.getValue().toString()));
    			}
    		}
    	}
    	
    	return stm;
    }
}
