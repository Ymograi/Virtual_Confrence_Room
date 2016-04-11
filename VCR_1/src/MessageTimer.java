import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abin
 */
public class MessageTimer extends TimerTask{
    
     public User sender;
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.0.100:3306/vcr";
    
    private static final String USER = "root";
    private static final String PASS = "";
    
    private javax.swing.JList<String> jList1;
    private String time;
    MessageTimer(javax.swing.JList<String> jList,String time1){
        jList1 = jList;
        time = time1;
    }
    public void run(){
        Connection conn = null;
        Statement stmt = null;
        String[] msg;
        try
        {
               
               //JDBC
               Class.forName("com.mysql.jdbc.Driver");
               
               conn = DriverManager.getConnection(DB_URL,USER,PASS);
               
               
               stmt = conn.createStatement();
               String sql;
               sql = "select * from message where timestamp > \"" + time + "\" order by timestamp asc;";
               System.out.println(sql);
               ResultSet rs =  stmt.executeQuery(sql);
               
               //jList1.setModel(new DefaultListModel());
               DefaultListModel model = (DefaultListModel) jList1.getModel();
               jList1 = new JList(model);
               
               while(rs.next())
               {
//                   jList1.setModel(new DefaultListModel());
//                   DefaultListModel model = (DefaultListModel) jList1.getModel();
                   model.addElement(rs.getString("sender") + ": " + rs.getString("text"));
                   System.out.println(rs.getString("text"));
                   time = rs.getString("timestamp");
                   jList1 = new JList(model);                   
               }
               
               
                    
           }
        
        catch(SQLException se)
        {
            se.printStackTrace();
        }
        
        catch(Exception e)
        {
           e.printStackTrace();;
        }
        
        finally
        {
            try
            {
        //                socket.close();
                if(stmt!=null)
                    conn.close();
            }
            
            catch(SQLException se)
            {
                se.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        
       return ;
    }
//    public static void main(String args[]){
////        Timer timer = new Timer();
////        timer.schedule(new MessageTimer(),0,1000*5);
//    }
    
}
