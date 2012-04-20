package com.alibaba.jan.jay;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class MySQLConnect {
	 /**
	   * ���췽��.
	   */
	  protected MySQLConnect() {
		// TODO Auto-generated constructor stub
	}

	  /** ���� main ����.
	   *  ����ʾ SQL �� ͳ��ĳ��company��Product����
	   *  @param args �������� main ����
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
	        # orcale �﷨:   
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
