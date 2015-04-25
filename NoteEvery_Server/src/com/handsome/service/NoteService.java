package com.handsome.service;

import java.util.List;

import com.handsome.dao.NoteDao;
import com.handsome.pojo.Note;

public class NoteService {
	private NoteDao nd = new NoteDao();
	public List<Note> listNoteByUid(int uid){ //根据用户id查询该用户的所有note
		System.out.println("查询所有note是否成功："+nd.listNoteByUid(uid));
		return nd.listNoteByUid(uid);
	}
	public int addNote(int uid,String ncontent) {//添加新note
		int key = nd.addNote(uid, ncontent);
		System.out.println("添加note是否成功："+key);
		return key;
	}
	public int deleteBook(int nid) {//删除某note
		int key = nd.deleteBook(nid);
		System.out.println("删除note是否成功："+key);
		return key;
	}
	public int updateBook(int nid,String ncontent){//修改note
		int key = nd.updateBook(nid, ncontent);
		System.out.println("修改note是否成功："+key);
		return key;
	}
	public String findNoteByNid(int nid){ //根据note的id查询note内容
		String key = nd.findNoteByNid(nid);
		System.out.println("将要修改的note内容是："+key);
		return key;
	}
}
