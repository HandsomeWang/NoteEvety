package com.handsome.action;

import java.util.List;

import com.handsome.pojo.Note;
import com.handsome.service.NoteService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NoteAction extends ActionSupport{
	private NoteService ns = new NoteService();
	private String newnote;
	private String newcontent;
	private int nid;
	
	
	public String getNewcontent() {
		return newcontent;
	}

	public void setNewcontent(String newcontent) {
		this.newcontent = newcontent;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public String getNewnote() {
		return newnote;
	}

	public void setNewnote(String newnote) {
		this.newnote = newnote;
	}

	public String addnote(){
		int uid = (Integer) ActionContext.getContext().getSession().get("uid");
		if(ns.addNote(uid, newnote)==0)
			return ERROR;
		List<Note> ln = ns.listNoteByUid(uid);
		ActionContext.getContext().getSession().put("ln", ln);
		return SUCCESS;
	}
	public String delete(){
		int uid = (Integer) ActionContext.getContext().getSession().get("uid");
		if(ns.deleteBook(nid)==0)
			return ERROR;
		List<Note> ln = ns.listNoteByUid(uid);
		ActionContext.getContext().getSession().put("ln", ln);
		return SUCCESS;
	}
	public String goedit(){
		ActionContext.getContext().getSession().put("editnid", nid);
		String content = ns.findNoteByNid(nid);
		ActionContext.getContext().getSession().put("content", content);
		return SUCCESS;
	}
	public String edit(){
		int nid = (Integer) ActionContext.getContext().getSession().get("editnid");
		int uid = (Integer) ActionContext.getContext().getSession().get("uid");
		if(ns.updateBook(nid, newcontent)==0)
			return ERROR;
		List<Note> ln = ns.listNoteByUid(uid);
		ActionContext.getContext().getSession().put("ln", ln);
		return SUCCESS;
	}
}
