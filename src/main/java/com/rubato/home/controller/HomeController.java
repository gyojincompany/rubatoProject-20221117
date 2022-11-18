package com.rubato.home.controller;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rubato.home.dao.IDao;
import com.rubato.home.dto.RFBoardDto;

@Controller
public class HomeController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "board_list")
	public String board_list(Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		ArrayList<RFBoardDto> boardDtos = dao.rfblist();
		int boardCount = dao.rfboardAllCount();
		
		model.addAttribute("boardList", boardDtos);
		model.addAttribute("boardCount", boardCount);
		
		return "board_list";
	}
	
	@RequestMapping(value = "board_view")
	public String board_view(HttpServletRequest request, Model model) {
		
		String rfbnum = request.getParameter("rfbnum");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		RFBoardDto rfboardDto = dao.rfboardView(rfbnum);
		
		model.addAttribute("rfbView", rfboardDto);
		
		return "board_view";
	}
	
	@RequestMapping(value = "board_write")
	public String board_write(HttpSession session, HttpServletResponse response) {
		String sessionId = (String) session.getAttribute("memberId");
		if(sessionId == null) {//참이면 로그인이 안된 상태
			PrintWriter out;
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('로그인하지 않으면 글을 쓰실수 없습니다!');history.go(-1);</script>");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
		
		return "board_write";
	}
	
	@RequestMapping(value = "member_join")
	public String member_join() {
		return "member_join";
	}
	
	@RequestMapping(value = "joinOk")
	public String joinOk(HttpServletRequest request, HttpSession session) {
		
		String memberId = request.getParameter("mid");
		String memberPw = request.getParameter("mpw");
		String memberName = request.getParameter("mname");
		String memberEmail = request.getParameter("memail");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.joinMember(memberId, memberPw, memberName, memberEmail);
		
		session.setAttribute("memberId", memberId);//가입과 동시에 로그인
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "loginOk")
	public String loginOk(HttpServletRequest request, HttpSession session) {
		
		String memberId = request.getParameter("mid");
		String memberPw = request.getParameter("mpw");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		//int checkIdFlag = dao.checkUserId(memberId);
		int checkIdFlag = dao.checkUserIdAndPw(memberId, memberPw);//1이면 로그인ok, 0이면 로그인x
		
		if(checkIdFlag == 1) {//참이면 로그인 성공
			session.setAttribute("memberId", memberId);
			
		}
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "writeOk")
	public String writeOk(HttpServletRequest request, HttpSession session) {
		
		String boardName = request.getParameter("rfbname");
		String boardTitle = request.getParameter("rfbtitle");
		String boardContent = request.getParameter("rfbcontent");
		
		String sessionId = (String) session.getAttribute("memberId");
		//글쓴이의 아이디는 현재 로그인된 유저의 아이디이므로 세션에서 가져와서 전달 
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.rfbwrite(boardName, boardTitle, boardContent, sessionId);
		
		return "redirect:board_list";
	}
	
}
