package com.rubato.home.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rubato.home.dao.IDao;

@Controller
public class HomeController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "board_list")
	public String board_list() {
		
		
		return "board_list";
	}
	
	@RequestMapping(value = "board_view")
	public String board_view() {
		return "board_view";
	}
	
	@RequestMapping(value = "board_write")
	public String board_write() {
		return "board_write";
	}
	
	@RequestMapping(value = "member_join")
	public String member_join() {
		return "member_join";
	}
	
	@RequestMapping(value = "joinOk")
	public String joinOk(HttpServletRequest request) {
		
		String memberId = request.getParameter("mid");
		String memberPw = request.getParameter("mpw");
		String memberName = request.getParameter("mname");
		String memberEmail = request.getParameter("memail");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.joinMember(memberId, memberPw, memberName, memberEmail);
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "loginOk")
	public String loginOk(HttpServletRequest request, HttpSession session) {
		
		String memberId = request.getParameter("mid");
		String memberPw = request.getParameter("mpw");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		//int checkIdFlag = dao.checkUserId(memberId);
		int checkIdFlag = dao.checkUserIdAndPw(memberId, memberPw);//1이면 로그인ok, 0이면 로그인x
		
		if(checkIdFlag == 1) {
			session.setAttribute("memberId", memberId);
		}
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:index";
	}
}
