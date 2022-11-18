package com.rubato.home.dao;

import java.util.ArrayList;

import com.rubato.home.dto.RFBoardDto;

public interface IDao {
	
	//멤버 관련
	public void joinMember(String mid, String mpw, String mname, String memail);//insert
	public int checkUserId(String mid);//select
	public int checkUserIdAndPw(String mid, String mpw);//select
	
	//게시판관련
	public void rfbwrite(String rfbname, String rfbtitle, String rfbcontent, String rfbid);//insert
	public ArrayList<RFBoardDto> rfblist();//게시판 리스트 select
	public int rfboardAllCount();//총 게시물 개수 select
	public RFBoardDto rfboardView(String rfbnum);//클릭한 글의 게시물 내용 보기 select
	public void delete(String rfbnum);//글삭제 delete
	public void rfbhit(String rfbnum);//조회수
	
}
