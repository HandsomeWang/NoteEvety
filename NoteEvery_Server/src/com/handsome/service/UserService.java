package com.handsome.service;

import com.handsome.dao.UserDao;

public class UserService{
	private UserDao ud= new UserDao();
	public int addUser(String uname,String pwd){//添加新用户
		int key = ud.addUser(uname, pwd);
		System.out.println("添加新用户是否成功："+key);
		return key;
	}
	
	public int checkIfLogin(String uname,String pwd){//查询是否登录成功
		System.out.println("登录是否成功："+ud.checkIfLogin(uname, pwd));
		return ud.checkIfLogin(uname, pwd);
	}

}
