package com.alibaba.jan.jay;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DbConnect {
	  /**
	   * 构造方法.
	   */
	  protected DbConnect() {
	  }

	  /** 这是 main 方法.
	   *  它演示 SQL 中 sum() 方法的用法
	   *  @param args 被传递至 main 方法
	   */
	   public static void main(String[] args) {

	     try {
	       Class.forName("oracle.jdbc.driver.OracleDriver");
	     }
	     catch (ClassNotFoundException ce) {
	       System.out.println(ce);
	     }
	     try {
	       String url = "jdbc:oracle:thin:@10.20.36.21:1521:ointest";
	       String user = "alibaba";
	       String password = "deYcR7facWSJtCuDpm2r";
	      // jdbc:oracle:thin:@MyDbComputerNameOrIP:1521:ORCL", sUsr, sPwd
	       /*
	        # orcale 语法:   
# Class.forName("oracle.jdbc.driver.OracleDriver");  
# Connection con = DriverManager.getConnection("jdbc:oracle:thin:@host:port:databse","user","password");  
	        */
	       Connection con = DriverManager.getConnection(url,user,password);
	       Statement s = con.createStatement();
	       ResultSet rs = s.executeQuery(
	           "SELECT id,login_id FROM member WHERE company_id='1000049320'");
	       while (rs.next()) {
	         System.out.print("id="+rs.getInt(1) + "\t");
	         System.out.print("login_id="+rs.getString(2) + "\n");
//	         System.out.println(" ");
	       }
	      rs.close();
	      s.close();
	      con.close();
	     }
	     catch (SQLException ce) {
	       System.out.println(ce);
	     }
	   }
}
