package sdm.hsmp.jdbc;

import java.sql.*;

public class DriverConnection {
	static String  username="root";
	static String password="tiger";
	 static final String DB_URL = "jdbc:mysql://localhost:3307/societymangement";
	
	private static Connection con;
    static{
        try{
        	   //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      con = DriverManager.getConnection(DB_URL,username,password);
		     }catch(Exception e)
        {
            System.out.println("CONNECTION ERROR : "+e.getMessage());
        }
    }    
    public static Connection get_connection(){
    	return con;
    }
       //FUNCTIONAL UTILITIES
       public static int updateOperation(String dmlQuery)
       {
    	   //insert into tbllogin('id','usrname','passwd','utype','status') values ('115','222111','222111','member',1)
        try{
          Statement st = ((java.sql.Connection) con).createStatement();
          int result = st.executeUpdate(dmlQuery);
          return result;
        }catch(Exception e){
            System.out.println("UPDATE ERROR : "+e.getMessage());
            return -1;
        }
       }
       
       public static ResultSet selectOperation(String projQuery)
       {
        try{
        	//System.out.println(con);
          Statement st = ((java.sql.Connection) con).createStatement();
          ResultSet rs = st.executeQuery(projQuery);
          return rs;
        }catch(Exception e){
            System.out.println("SELECT ERROR : "+e.getMessage());
            return null;
        }
       }


}
