package com.handsome.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	public static Connection getConnection(){
		Connection con=null;
		String url="jdbc:mysql://localhost:3306/note";
		String usr="root";
		String psd="wang";
		String driverClass="com.mysql.jdbc.Driver";
		try {
			Class.forName(driverClass);
			con=DriverManager.getConnection(url, usr,psd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	public static  void close(Connection con,PreparedStatement pst,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			if(pst!=null){
				pst.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Connection con=getConnection();
		System.out.println(con);
	}
	
	
}
