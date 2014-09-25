
package com.santrong.util.criteria;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;



public class Criteria {
	

    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private List<String>						selectFields;
    private Entry<String, String>				basicTable;
    private LinkedHashMap<String, Join> 		joinTables;
    private Constrainable          				topConstrainable;
    private List<Order>         				orders;
    private Entry<Integer, Integer>				page;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------
    
    public Criteria(String table) {

        this(table, "S");
    }



    public Criteria(String table, String alias) {
    	
        selectFields	 = new ArrayList<String>();
        basicTable		 = new AbstractMap.SimpleEntry<String, String>(alias, table);
    	joinTables       = new LinkedHashMap<String, Join>();
        topConstrainable = null;
        orders           = new ArrayList<Order>();
    }


    
    //~ ------- [METHODS] ----------------------------------------------------------------------------------------------

    public String toString() {

        String selectPart = "";
        
        if (selectFields.size() > 0) {
        	
        	for (String s : selectFields) {
        		selectPart += ", " + s;
        	}
        	
            if (!selectPart.isEmpty()) {
            	selectPart = selectPart.substring(2);
            }        	
        	
        } else {
        	
        	selectPart = basicTable.getKey() + ".*";
        	
        }

        return "SELECT " + selectPart + " FROM " + generateSelectWoTable() + generateOrders() + generatePage();
    }
    

    //~ ----------------------------------------------------------------------------------------------------------------

    public Criteria asc(String column) {

        this.orders.add(new Order(column, Order.ORDER_ASCENDING));

        return this;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public Criteria desc(String column) {

        this.orders.add(new Order(column, Order.ORDER_DESCENDING));

        return this;
    }
    
    

    //~ ----------------------------------------------------------------------------------------------------------------
    
    public Criteria limit(int begin, int count) {
    	page		 = new AbstractMap.SimpleEntry<Integer, Integer>(begin, count);
    	return this;
    }
    
    
    
    //~ ----------------------------------------------------------------------------------------------------------------
    
    public Criteria limit(int count) {
    	page		 = new AbstractMap.SimpleEntry<Integer, Integer>(0, count);
    	return this;
    }
    
    
    
    //~ ----------------------------------------------------------------------------------------------------------------

    public Criteria ljoin(String table, String alias, Constrainable constraint) throws AliasAlreadyUsedException {

        if (basicTable.getKey().equalsIgnoreCase(alias) || joinTables.containsKey(alias)) {
            throw new AliasAlreadyUsedException();
        }

        Join join = new Join(table, alias, constraint);        
        joinTables.put(alias, join);

        return this;
    }


    
    //~ ----------------------------------------------------------------------------------------------------------------

    
    
    public Criteria ljoin(String table, String alias, Object leftSide, Object rightSide) throws AliasAlreadyUsedException {
    	
    	Constraint constraint = new Constraint(leftSide, rightSide);
    	return this.ljoin(table, alias, constraint);
    }


    
    //~ ----------------------------------------------------------------------------------------------------------------
    
    

    public Criteria rjoin(String table, String alias, Constrainable constraint) throws AliasAlreadyUsedException {

        if (basicTable.getKey().equalsIgnoreCase(alias) || joinTables.containsKey(alias)) {
            throw new AliasAlreadyUsedException();
        }

        Join join = new Join(table, alias, Join.RIGHT, constraint);        
        joinTables.put(alias, join);

        return this;
    }


    
    //~ ----------------------------------------------------------------------------------------------------------------

    public Criteria rjoin(String table, String alias, Object leftSide, Object rightSide) throws AliasAlreadyUsedException {
    	
        if (basicTable.getKey().equalsIgnoreCase(alias) || joinTables.containsKey(alias)) {
            throw new AliasAlreadyUsedException();
        }

        Constraint constraint = new Constraint(leftSide, rightSide);
        Join join = new Join(table, alias, Join.RIGHT, constraint);        
        joinTables.put(alias, join);

        return this;
    }


    
    //~ ----------------------------------------------------------------------------------------------------------------    
    
    

    public Criteria ijoin(String table, String alias, Constrainable constraint) throws AliasAlreadyUsedException {

        if (basicTable.getKey().equalsIgnoreCase(alias) || joinTables.containsKey(alias)) {
            throw new AliasAlreadyUsedException();
        }

        Join join = new Join(table, alias, Join.INNER, constraint);        
        joinTables.put(alias, join);

        return this;
    }


    
    //~ ----------------------------------------------------------------------------------------------------------------

    public Criteria ijoin(String table, String alias, Object leftSide, Object rightSide) throws AliasAlreadyUsedException {
    	
        if (basicTable.getKey().equalsIgnoreCase(alias) || joinTables.containsKey(alias)) {
            throw new AliasAlreadyUsedException();
        }

        Constraint constraint = new Constraint(leftSide, rightSide);
        Join join = new Join(table, alias, Join.INNER, constraint);        
        joinTables.put(alias, join);

        return this;
    }


    
    //~ ----------------------------------------------------------------------------------------------------------------   
    public Criteria order(Order... orders) {

        this.orders.addAll(Arrays.asList(orders));

        return this;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    
    public Criteria setFields(String fields) {
    	
    	if (!fields.trim().isEmpty()) {
	    	this.selectFields.clear();
	    	this.addFields(fields);
    	}
    	
        return this;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    
    public Criteria addFields(String fields) {
    	
    	if (!fields.trim().isEmpty()) {
    		this.selectFields.add(fields);
    	}
    	
        return this;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Criteria where(Constrainable constraint) {

        if (topConstrainable == null) {

        	//旧模式，第一次where使用And还是Or决定了接下去所有是And还是Or
//            if (constraint instanceof Constraint) {
//                topConstrainable = new And(constraint);
//            } else {
//                topConstrainable = constraint;
//            }
        	//新模式，顶级一律使用And
        	topConstrainable = new And(constraint);
        } else {
            topConstrainable.add(constraint);
        }

        return this;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public Criteria where(Object leftSide, Object rightSide) {

        Constraint constraint = new Constraint(leftSide, rightSide);

        if (topConstrainable == null) {
            topConstrainable = new And(constraint);
        } else {
            topConstrainable.add(constraint);
        }

        return this;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private String generateOrders() {

        String query = "";

        for (Order order : orders) {
            query += ", " + order.getColumn() + (order.getMethod() == Order.ORDER_ASCENDING ? " ASC" : " DESC");
        }

        if (!query.isEmpty()) {
            query = " ORDER BY " + query.substring(2);
        }

        return query;
    }
    
    

    //~ ----------------------------------------------------------------------------------------------------------------

    private String generatePage() {

        String query = "";

        if (page != null) {
        	query += page.getKey() + "," + page.getValue();
        }
        
        if (!query.isEmpty()) {
            query = " LIMIT " + query;
        }

        return query;
    }    



    //~ ----------------------------------------------------------------------------------------------------------------

    private String generateSelectWoTable() {

        String query = "";

        query += basicTable.getValue() + " " + basicTable.getKey();

        for (String alias : joinTables.keySet()) {
        
        	Join join = (Join) joinTables.get(alias);
        	
        	if (join.getJoinType() == Join.LEFT) {
        		query += " LEFT JOIN ";
        	} else if (join.getJoinType() == Join.RIGHT) {
        		query += " RIGHT JOIN ";
        	} else if (join.getJoinType() == Join.INNER) {
        		query += " INNER JOIN ";
        	}
        	
        	query += join.getJoinTable() + " " + join.getJoinAlias() + " ON ";
        	
        	Iterator<Constrainable> iterator = join.getIterator();
        	
        	while(iterator.hasNext()){
        		query += wh(iterator.next());
        	}
        }

        if (topConstrainable != null && topConstrainable.size() > 0) {
            query += " WHERE " + wh(topConstrainable);
        }

        return query;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private String wh(Constrainable constrainable) {

        String                  query      = "";
        Iterator<Constrainable> iterator   = constrainable.getIterator();
        String                  whereQuery = "";

        if (constrainable instanceof Constraint) {
            Constraint constraint = (Constraint) constrainable;

            if (constraint.getOperator() == Constraint.EQUAL) {
                query += constraint.getLeftSide() + " = " + constraint.getRightSide();
            } else if (constraint.getOperator() == Constraint.NOT_EQUAL) {
                query += constraint.getLeftSide() + " != " + constraint.getRightSide();
            } else if (constraint.getOperator() == Constraint.LIKE) {
                query += constraint.getLeftSide() + " LIKE " + constraint.getRightSide();
            } else if (constraint.getOperator() == Constraint.LIKE_LOWER) {
                query += "LOWER(" + constraint.getLeftSide() + ") LIKE LOWER(" + constraint.getRightSide() + ")";
            } else if (constraint.getOperator() == Constraint.LESS_THAN) {
                query += constraint.getLeftSide() + " < " + constraint.getRightSide();
            } else if (constraint.getOperator() == Constraint.LESS_THAN_OR_EQUAL) {
                query += constraint.getLeftSide() + " <= " + constraint.getRightSide();
            } else if (constraint.getOperator() == Constraint.GREATER_THAN) {
                query += constraint.getLeftSide() + " > " + constraint.getRightSide();
            } else if (constraint.getOperator() == Constraint.GREATER_THAN_OR_EQUAL) {
                query += constraint.getLeftSide() + " >= " + constraint.getRightSide();
            } else if (constraint.getOperator() == Constraint.IS_NULL) {
                query += constraint.getLeftSide() + " IS NULL";
            } else if (constraint.getOperator() == Constraint.IS_NOT_NULL) {
                query += constraint.getLeftSide() + " IS NOT NULL";                
            } else if (constraint.getOperator() == Constraint.IN) {
                query += constraint.getLeftSide() + " IN " + constraint.getRightSide();
            }
        } else if (constrainable instanceof And) {

            while (iterator.hasNext()) {
                whereQuery += " AND " + wh(iterator.next());
            }

            query += "(" + whereQuery.substring(5) + ")";
        } else if (constrainable instanceof Or) {

            while (iterator.hasNext()) {
                whereQuery += " OR " + wh(iterator.next());
            }

            query += "(" + whereQuery.substring(4) + ")";
        }

        return query;
    }
}
