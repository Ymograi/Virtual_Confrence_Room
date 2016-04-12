/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.DriverManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;
/**
 *
 * @author Abin
 */
public class User {
    public String username;
    public String name;
    public String email;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.0.101:3306/vcr";
    
    private static final String USER = "root";
    private static final String PASS = "root";
    Connection conn= null;
    Statement stmt= null;
    User(String usernam){
        this.username = usernam;
        try{
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
               Class.forName("com.mysql.jdbc.Driver");
               stmt = conn.createStatement();
               String sql;
               sql = "select email,name from user where username=\"" + this.username+"\";";
               //System.out.println(sql);
               ResultSet res=stmt.executeQuery(sql);
               res.next();
               email = res.getString("email");
               //System.out.println(name);
               name=res.getString("name");
        }
        catch(SQLException s){s.printStackTrace();s.getMessage();}
        catch(Exception e){e.getMessage();}
        
    }
    public String getUsername(){
        return username;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public static void main(String[] args )
        {
          User a=new User("YASH");
          System.out.println(a.getUsername());
          System.out.println(a.getName());
          System.out.println(a.getEmail());
        }
}
