package com.rubato.home.dao;

public interface IDao {
	
	//멤버 관련
	public void joinMember(String mid, String mpw, String mname, String memail);//insert
	public int checkUserId(String mid);//select
	public int checkUserIdAndPw(String mid, String mpw);//select
	
	//게시판관련
	public void rfbwrite(String rfbname, String rfbtitle, String rfbcontent, String rfbid);//insert
	
	
}
