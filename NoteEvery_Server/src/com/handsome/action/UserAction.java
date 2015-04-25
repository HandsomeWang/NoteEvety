package com.handsome.action;

import java.util.List;

import com.handsome.pojo.Note;
import com.handsome.pojo.User;
import com.handsome.service.NoteService;
import com.handsome.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
	private User user;
	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}
	private UserService us = new UserService();
	private NoteService ns = new NoteService();
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public String goregist() {
		if(us.addUser(user.getUname(), user.getPwd())==1)
			return SUCCESS;
		return ERROR;
	}
	public String iflogin() {
		int key = us.checkIfLogin(user.getUname(), user.getPwd());
		if(key!=0){
			ActionContext.getContext().getSession().put("uid", key);
			List<Note> ln = ns.listNoteByUid(key);
			ActionContext.getContext().getSession().put("ln", ln);
			return SUCCESS;
		}	
		return ERROR;
	}
}
