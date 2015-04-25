package com.handsome.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.handsome.pojo.Note;



public class NoteDao {
	public List<Note> listNoteByUid(int uid){ //根据用户id查询该用户的所有note
		List<Note> notelist = new ArrayList<Note>();
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		String sql="select * from note where uid=? order by ndate desc";
		try {
			con=DBUtil.getConnection();
			pst=con.prepareStatement(sql);
			pst.setInt(1, uid);
			rs=pst.executeQuery();
			while(rs.next()){
				Note n=new Note();
				n.setNid(rs.getInt(1));
				n.setUid(rs.getInt(2));
				java.util.Date d =rs.getTimestamp(3);
				n.setNdate(d);
				n.setNcontent(rs.getString(4));
				notelist.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DBUtil.close(con, pst, rs);
		}
		return notelist;
	} 
	public String findNoteByNid(int nid){ //根据note的id查询note内容
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		String content = null;
		String sql="select ncontent from note where nid=?";
		try {
			con=DBUtil.getConnection();
			pst=con.prepareStatement(sql);
			pst.setInt(1, nid);
			rs=pst.executeQuery();
			if(rs.next()){
				content = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DBUtil.close(con, pst, rs);
		}
		return content;
	} 
	
	public int addNote(int uid,String ncontent) {//添加新note
		int rs=0;
		Connection con=null;
		PreparedStatement pst=null;
		String sql="insert into note(uid,ndate,ncontent) values (?,Now(),?)";
		try {
			con=DBUtil.getConnection();
			pst=con.prepareStatement(sql);
			pst.setInt(1,uid);
			pst.setString(2, ncontent);
			rs=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally{
			DBUtil.close(con, pst, null);
		}
		return rs;
	}
	
	public int deleteBook(int nid) {//删除某note
		int rs=0;
		Connection con=null;
		PreparedStatement pst=null;
		String sql="delete from note where nid=?";
		try {
			con=DBUtil.getConnection();
			pst=con.prepareStatement(sql);
			pst.setInt(1, nid);
			rs=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally{
			DBUtil.close(con, pst, null);
		}
		return rs;
	}
	
	public int updateBook(int nid,String ncontent){//修改note
		Connection con=null;
		PreparedStatement pst=null;
		int rs=0;
		String sql="update note set ncontent = ? where nid=? ";
		try {
			con=DBUtil.getConnection();
			pst=con.prepareStatement(sql);
			
			pst.setString(1, ncontent);
			pst.setInt(2, nid);
			rs=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DBUtil.close(con, pst,null);
		}
		return rs;
	}
}
