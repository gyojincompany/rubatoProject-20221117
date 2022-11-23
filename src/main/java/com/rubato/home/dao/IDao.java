package com.rubato.home.dao;

import java.util.ArrayList;

import com.rubato.home.dto.RFBoardDto;
import com.rubato.home.dto.RReplyDto;

public interface IDao {
	
	//멤버 관련
	public void joinMember(String mid, String mpw, String mname, String memail);//insert
	public int checkUserId(String mid);//select
	public int checkUserIdAndPw(String mid, String mpw);//select
	
	//게시판관련
	public int rfbwrite(String rfbname, String rfbtitle, String rfbcontent, String rfbid);//insert
	public ArrayList<RFBoardDto> rfblist();//게시판 리스트 select
	public int rfboardAllCount();//총 게시물 개수 select
	public RFBoardDto rfboardView(String rfbnum);//클릭한 글의 게시물 내용 보기 select
	public void delete(String rfbnum);//글삭제 delete
	public void rfbhit(String rfbnum);//조회수
	
	//댓글관련
	public void rrwrite(String rrorinum, String rrid, String rrcontent);//새 댓글 입력 insert
	public ArrayList<RReplyDto> rrlist(String rrorinum);//해당글의 댓글 리스트 select
	public void rrcount(String rrorinum);//댓글 등록시 해당글의 댓글갯수 1증가 
	public void rrdelete(String rrnum);//댓글 삭제
	public void rrcountMinus(String rrorinum);//댓글 삭제시 해당글의 댓글갯수 1감소
	
	//게시판 검색 관련
	public ArrayList<RFBoardDto> rfbSearchTitleList(String searchKey);
	public ArrayList<RFBoardDto> rfbSearchContentList(String searchKey);
	public ArrayList<RFBoardDto> rfbSearchWriterList(String searchKey);
	
	//파일 업로드 관련
	public void fileInfoInsert(int boardnum, String fileoriname, String filename, String fileextension, String fileurl);
	public ArrayList<RFBoardDto> boardLatestInfo(String rfbuserid);
	//현재 파일이 첨부된 글을 쓴 아이디로 검색된 글 목록
}
