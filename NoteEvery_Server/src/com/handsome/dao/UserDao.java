package com.handsome.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.handsome.pojo.User;


public class UserDao {
	public int addUser(String uname,String pwd){//添加新用户
		int rs=0;
		Connection con=null;
		PreparedStatement pst=null;
		String sql="insert into user(uname,pwd) values (?,?)";
		try {
			con=DBUtil.getConnection();
			pst=con.prepareStatement(sql);
			pst.setString(1, uname);
			pst.setString(2, pwd);
			rs=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally{
			DBUtil.close(con, pst, null);
		}
		return rs;
	}
	
	public int checkIfLogin(String uname,String pwd){
		//根据传入的用户名、密码判断是否登录成功
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="select uid from user where uname=? and pwd=?";
		int key = 0;		
			try {
				con=DBUtil.getConnection();
				stmt = con.prepareStatement(sql);
				stmt.setString(1, uname);
				stmt.setString(2, pwd);
				rs = stmt.executeQuery();	
				if (rs.next()) {
					key=rs.getInt(1);//匹配成功
					System.out.println(key);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DBUtil.close(con,stmt,rs);
			}	
			return key;
	}

}
