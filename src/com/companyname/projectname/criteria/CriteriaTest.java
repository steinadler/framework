
package com.companyname.projectname.criteria;

import static com.companyname.projectname.criteria.SqlOperator.and;
import static com.companyname.projectname.criteria.SqlOperator.asc;
import static com.companyname.projectname.criteria.SqlOperator.desc;
import static com.companyname.projectname.criteria.SqlOperator.eq;
import static com.companyname.projectname.criteria.SqlOperator.gt;
import static com.companyname.projectname.criteria.SqlOperator.gte;
import static com.companyname.projectname.criteria.SqlOperator.in;
import static com.companyname.projectname.criteria.SqlOperator.like;
import static com.companyname.projectname.criteria.SqlOperator.likeLower;
import static com.companyname.projectname.criteria.SqlOperator.lt;
import static com.companyname.projectname.criteria.SqlOperator.lte;
import static com.companyname.projectname.criteria.SqlOperator.ne;
import static com.companyname.projectname.criteria.SqlOperator.nnul;
import static com.companyname.projectname.criteria.SqlOperator.nul;
import static com.companyname.projectname.criteria.SqlOperator.or;



public class CriteriaTest {

	
	public static void main(String[] args){
		
		try {
			// improve group by,limit
			
			
			//~ ------- [mixed test] ----------------------------------------------------------
			
			
			Statement criteria = new Statement("USERS", "U");
			criteria.setFields("U.ID, U.NAME");
			criteria.addFields("count(*) as cn");
			criteria.ljoin("LESSON", "L", "U.ID", "L.ID");
			criteria.rjoin("ROOM", "R", eq("U.NAME", "R.ROOMNAME"));
			criteria.ijoin("ADDRESS", "A", new And(gte("U.ID", 5), eq("U.ID", 2)));
			criteria.ljoin("BOOK", "B", and(gte("U.ID", 5), eq("U.NAME", "'HT'")));
			criteria.where(and(gte("U.ID", 5), eq("U.ID", 2), nul("U.NAME"), in("U.ID", "('111', '222')"), nnul("U.NAME")));
			criteria.asc("A.ID");
			
			System.out.println(criteria.toString());
			
			
			//~ ------- [load the jdbc Driver then remove the comment] ----------------------------------------------------------
			
//			java.sql.PreparedStatement 		stm 		= null;
//			java.sql.Connection			 	con 		= null;
//			
//			try {
//				
//				Class.forName("com.mysql.jdbc.Driver");
//				con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/wnj?user=root&password=root");			
//				
//				Statement criteria2 = new Statement("USER", "U");
//				criteria2.ljoin("HOUSE", "H", "U.ID", "H.UID");
//				
//				criteria2.where(and(gte("U.ID", "?"), eq("U.USERNAME", "?")));
//				criteria2.setInt(2);
//				criteria2.setString("SHEEP");
//				
//				
//				criteria2.where(like("U.USERNAME", "?"));
//				criteria2.setString("SHEE%");				
//				
//				System.out.println(criteria2.toString());
//				
//				stm = criteria2.getRealStatement(con);
//				java.sql.ResultSet rs =	stm.executeQuery();
//				
//				while(rs.next()){
//					System.out.println("USERNAME:" + rs.getString("USERNAME"));
//				}
//				
//			} catch (Exception e) {
//				try {
//					stm.close();
//					con.close();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				e.printStackTrace();
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public CriteriaTest() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------


    
    public void testJoinQuery() {

        try {
            Criteria criteria = new Criteria("USERS").ljoin("USER_PERMISSIONS", "UP", "S.ID", "UP.ID");
            String   expected = "SELECT S.* FROM USERS S LEFT JOIN USER_PERMISSIONS UP ON S.ID = UP.ID";

            if (!expected.equals(criteria.toString())) {
                assert false;
            }

            criteria = new Criteria("USERS").rjoin("USER_PERMISSIONS", "UP", "UP.USER_ID", "U.ID");
            expected = "SELECT S.* FROM USERS S RIGHT JOIN USER_PERMISSIONS UP ON UP.USER_ID = U.ID";

            if (!expected.equals(criteria.toString())) {
                assert false;
            }

            criteria = new Criteria("USERS", "U");
            criteria = criteria.ljoin("USER_PERMISSIONS", "UP", "UP.USER_ID", "U.ID");
            criteria = criteria.rjoin("PERMISSIONS", "P", "P.ID", "UP.PERMISSION_ID");
            criteria = criteria.ijoin("ROLE_PERMISSIONS", "RP", "RP.PERMISSION_ID", "P.ID");
            criteria = criteria.ljoin("ROLES", "R", "R.ID", "RP.ROLE_ID");
            criteria = criteria.ljoin("USER_ROLES", "UR", "UR.USER_ID", "U.ID");
            
            expected =  "SELECT U.* FROM USERS U";
            expected += " LEFT JOIN USER_ROLES UR ON UR.USER_ID = U.ID";
            expected += " RIGHT JOIN PERMISSIONS P ON P.ID = UP.PERMISSION_ID";
            expected += " LEFT JOIN ROLES R ON R.ID = RP.ROLE_ID";
            expected += " LEFT JOIN USER_PERMISSIONS UP ON UP.USER_ID = U.ID";
            expected += " INNER JOIN ROLE_PERMISSIONS RP ON RP.PERMISSION_ID = P.ID";
            if (!expected.equals(criteria.toString())) {
                assert false;
            }
        } catch (Exception e) {
            assert false;
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    
    public void testJoinWithMultiWhereQuery() {

        try {
            Criteria criteria = new Criteria("USERS", "U");
            criteria = criteria.ljoin("USER_PERMISSIONS", "UP", "UP.USER_ID", "U.ID");
            criteria = criteria.ljoin("PERMISSIONS", "P", "P.ID", "UP.PERMISSION_ID");
            criteria = (Criteria) criteria.where(or(eq("U.NAME", "U.USERNAME"), and(gte("U.ID", 5), eq("U.ID", 2))));

            String expected = "SELECT U.* FROM USERS U";
            expected += " LEFT JOIN PERMISSIONS P ON P.ID = UP.PERMISSION_ID";
            expected += " LEFT JOIN USER_PERMISSIONS UP ON UP.USER_ID = U.ID";
            expected += " WHERE (U.NAME = U.USERNAME OR (U.ID >= 5 AND U.ID = 2))";

            if (!expected.equals(criteria.toString())) {
                assert false;
            }
        } catch (Exception e) {
            assert false;
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    
    public void testMultiWhereQuery() {

        Criteria criteria = new Criteria("USERS", "U").where("U.NAME", "U.USERNAME").where("U.ID", 3);
        String   expected = "SELECT U.* FROM USERS U WHERE (U.NAME = U.USERNAME AND U.ID = 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        And andTree = new And();
        andTree.add(new Constraint("U.NAME", "U.USERNAME"));
        andTree.add(new Constraint("U.ID", 3));
        criteria = new Criteria("USERS", "U").where(andTree);

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        andTree  = new And(new Constraint("U.NAME", "U.USERNAME"), new Constraint("U.ID", 3));
        criteria = new Criteria("USERS", "U").where(andTree);

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        andTree  = new And(eq("U.NAME", "U.USERNAME"), eq("U.ID", 3));
        criteria = new Criteria("USERS", "U").where(andTree);

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(and());
        criteria = criteria.where(eq("U.NAME", "U.USERNAME"));
        criteria = criteria.where(eq("U.ID", 3));

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(new Or(eq("U.NAME", "U.USERNAME"), eq("U.ID", 3)));
        expected = "SELECT U.* FROM USERS U WHERE (U.NAME = U.USERNAME OR U.ID = 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(or(eq("U.NAME", "U.USERNAME"), eq("U.ID", 3)));

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(or(eq("U.NAME", "U.USERNAME"), and(gte("U.ID", 5), eq("U.ID", 2))));
        expected = "SELECT U.* FROM USERS U WHERE (U.NAME = U.USERNAME OR (U.ID >= 5 AND U.ID = 2))";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    
    public void testOrder() {

        Criteria criteria = new Criteria("USERS").asc("S.ID");
        String   expected = "SELECT S.* FROM USERS S ORDER BY S.ID ASC";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS").asc("S.ID").desc("S.USERNAME");
        expected = "SELECT S.* FROM USERS S ORDER BY S.ID ASC, S.USERNAME DESC";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS").order(asc("S.ID"), desc("S.USERNAME"));

        if (!expected.equals(criteria.toString())) {
            assert false;
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    
    public void testSelectQuery() {

        Criteria criteria = new Criteria("USERS");
        String   expected = "SELECT S.* FROM USERS S";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        // Use another alias for table
        criteria = new Criteria("USERS", "A");
        expected = "SELECT A.* FROM USERS A";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    
    public void testWhereQuery() {

        Criteria criteria = new Criteria("USERS", "U").where("U.NAME", "U.USERNAME");
        String   expected = "SELECT U.* FROM USERS U WHERE (U.NAME = U.USERNAME)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where("U.USER_ID", 3);
        expected = "SELECT U.* FROM USERS U WHERE (U.USER_ID = 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where("U.USERNAME", "'admin'");
        expected = "SELECT U.* FROM USERS U WHERE (U.USERNAME = 'admin')";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(eq("U.USER_ID", 3));
        expected = "SELECT U.* FROM USERS U WHERE (U.USER_ID = 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(ne("U.USER_ID", 3));
        expected = "SELECT U.* FROM USERS U WHERE (U.USER_ID != 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(like("U.NAME", "'ahmet'"));
        expected = "SELECT U.* FROM USERS U WHERE (U.NAME LIKE 'ahmet')";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        criteria = new Criteria("USERS", "U").where(likeLower("U.NAME", "'ahmet'"));
        expected = "SELECT U.* FROM USERS U WHERE (LOWER(U.NAME) LIKE LOWER('ahmet'))";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        // less than
        criteria = new Criteria("USERS", "U").where(lt("U.USER_ID", 3));
        expected = "SELECT U.* FROM USERS U WHERE (U.USER_ID < 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        // less than or equal to
        criteria = new Criteria("USERS", "U").where(lte("U.USER_ID", 3));
        expected = "SELECT U.* FROM USERS U WHERE (U.USER_ID <= 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        // greater than
        criteria = new Criteria("USERS", "U").where(gt("U.USER_ID", 3));
        expected = "SELECT U.* FROM USERS U WHERE (U.USER_ID > 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }

        // greater than or equal to
        criteria = new Criteria("USERS", "U").where(gte("U.USER_ID", 3));
        expected = "SELECT U.* FROM USERS U WHERE (U.USER_ID >= 3)";

        if (!expected.equals(criteria.toString())) {
            assert false;
        }
    }
}
