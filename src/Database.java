/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zain
 */
public class Database {
    
    
    
    // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/chat";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "zain";
   static PreparedStatement addmsg;

    private Database() {
        //
    }
    
    /**
     *
     */
    public synchronized static void addMessage(String text,String by) throws SQLException
    {
        
         Connection conn = null;
        try {
           
            

            Class.forName("com.mysql.jdbc.Driver");
            
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            addmsg=conn.prepareStatement("Insert INTO rmichat (text,textBy) VALUES (?,?);");
            addmsg.setString(1, text);
            addmsg.setString(2, by);
            
            addmsg.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if(conn !=null)
                 conn.close();
        }

    
    }
  
    
}
