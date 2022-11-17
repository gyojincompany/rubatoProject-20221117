package com.rubato.home.dao;

public interface IDao {
	
	public void joinMember(String mid, String mpw, String mname, String memail);//insert
	public int checkUserId(String mid);//select
	public int checkUserIdAndPw(String mid, String mpw);//select
}
