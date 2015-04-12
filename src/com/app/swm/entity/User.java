package com.app.swm.entity;

import java.util.Date;

public class User implements java.io.Serializable {

	// Fields

	private Integer studentID;
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private String email;
	//private Date DOB;
	private String headUrl;
	private String intro;
	/*private Integer followNum;
	private Integer fansNum;
	
	
	private short status;
	private Integer newsNum;
	private Integer newReply;*/
	
	//private boolean focus=false;// 是否已经关注   不做数据库映射
	public static final short STATUS_STUDENT = 0;// 学生
	public static final short STATUS_TREACHER = 1;//老师
	public static final short STATUS_PUBLIC = 2;//公众号
	public Integer getStudentID() {
		return studentID;
	}
	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/*public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}*/
	public static short getStatusStudent() {
		return STATUS_STUDENT;
	}
	public static short getStatusTreacher() {
		return STATUS_TREACHER;
	}
	public static short getStatusPublic() {
		return STATUS_PUBLIC;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	

}