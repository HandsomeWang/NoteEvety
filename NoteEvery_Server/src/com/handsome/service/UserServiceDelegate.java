package com.handsome.service;

import com.handsome.dao.UserDao;

@javax.jws.WebService(targetNamespace = "http://service.handsome.com/", serviceName = "UserServiceService", portName = "UserServicePort", wsdlLocation = "WEB-INF/wsdl/UserServiceService.wsdl")
public class UserServiceDelegate {

	com.handsome.service.UserService userService = new com.handsome.service.UserService();

	public int addUser(String uname, String pwd) {
		return userService.addUser(uname, pwd);
	}

	public int checkIfLogin(String uname, String pwd) {
		return userService.checkIfLogin(uname, pwd);
	}

}