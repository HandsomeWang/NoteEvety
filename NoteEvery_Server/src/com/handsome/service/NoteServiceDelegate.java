package com.handsome.service;

import java.util.List;
import com.handsome.dao.NoteDao;
import com.handsome.pojo.Note;

@javax.jws.WebService(targetNamespace = "http://service.handsome.com/", serviceName = "NoteServiceService", portName = "NoteServicePort", wsdlLocation = "WEB-INF/wsdl/NoteServiceService.wsdl")
public class NoteServiceDelegate {

	com.handsome.service.NoteService noteService = new com.handsome.service.NoteService();

	public List<Note> listNoteByUid(int uid) {
		return noteService.listNoteByUid(uid);
	}

	public int addNote(int uid, String ncontent) {
		return noteService.addNote(uid, ncontent);
	}

	public int deleteBook(int nid) {
		return noteService.deleteBook(nid);
	}

	public int updateBook(int nid, String ncontent) {
		return noteService.updateBook(nid, ncontent);
	}

	public String findNoteByNid(int nid) {
		return noteService.findNoteByNid(nid);
	}

}