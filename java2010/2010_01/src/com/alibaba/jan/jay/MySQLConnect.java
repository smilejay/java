package com.alibaba.jan.jay;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class MySQLConnect {
	 /**
	   * 构造方法.
	   */
	  protected MySQLConnect() {
		// TODO Auto-generated constructor stub
	}

	  /** 这是 main 方法.
	   *  它演示 SQL 中 统计某个company的Product个数
	   *  @param args 被传递至 main 方法
	   */
	   public static void main(String[] args) {

	     try {
	       Class.forName("org.gjt.mm.mysql.Driver");
	     }
	     catch (ClassNotFoundException ce) {
	       System.out.println(ce);
	     }
	     try {
	       final String url = "jdbc:mysql://localhost/test";
	       final String user = "master";
	       final String password = "renyongjie";
	      // jdbc:oracle:thin:@MyDbComputerNameOrIP:1521:ORCL", sUsr, sPwd
	       /*
	        # orcale 语法:   
# Class.forName("oracle.jdbc.driver.OracleDriver");  
# Connection con = DriverManager.getConnection("jdbc:oracle:thin:@host:port:databse","user","password");  
	        */
	       Connection con = DriverManager.getConnection(url,user,password);
	       Statement s = con.createStatement();
	       ResultSet rs = s.executeQuery(
	           "SELECT company_name,email FROM company WHERE id='1000062450'");
	       while (rs.next()) {
	         System.out.print("company_name="+rs.getString(1) + "\t");
	         System.out.print("email="+rs.getString(2) + "\n");
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
