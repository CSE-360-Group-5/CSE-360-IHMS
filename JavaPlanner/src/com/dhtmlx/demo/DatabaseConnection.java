package com.dhtmlx.demo;
 
import java.sql.DriverManager; 
import java.sql.SQLException;
 
public class DatabaseConnection 
{
 
               public static java.sql.Connection getConnection() 
               {
                             java.sql.Connection conn = null;

                              try {
                            	  Class.forName("com.mysql.jdbc.Driver");
                            	  String url = "jdbc:mysql://localhost:3306/javaplanner";
                            	  String user = "root";
                            	  String password = "";
                            	  conn = DriverManager.getConnection(url, user, password);
                              } catch (ClassNotFoundException e1) {
                                            e1.printStackTrace();
                              } catch (SQLException e1) {
                                            e1.printStackTrace();
                              }
                              return conn;
               }
 
}