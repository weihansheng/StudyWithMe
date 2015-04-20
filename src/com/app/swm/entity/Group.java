package com.app.swm.entity;

import java.util.Date;

public class Group {
	private Integer groupID;
	private Integer moduleID;
	private Integer adminID;
	private String type;
	private String admin;
	private String title;
	private String description;
	private Date postTime;
	private Date startTime;
	private Integer peopleNum;
	private Integer loveNum;
	private Integer applicant;
	private String where;
	private ClassRoom classRoom;
	
	
	public Integer getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}
	public Integer getApplicant() {
		return applicant;
	}
	public void setApplicant(Integer applicant) {
		this.applicant = applicant;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	public Integer getModuleID() {
		return moduleID;
	}
	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}
	public Integer getAdminID() {
		return adminID;
	}
	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public ClassRoom getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	@Override
	public String toString() {
		return "Group [groupID=" + groupID + ", moduleID=" + moduleID
				+ ", adminID=" + adminID + ", type=" + type + ", admin="
				+ admin + ", title=" + title + ", description=" + description
				+ ", postTime=" + postTime + ", startTime=" + startTime
				+ ", peopleNum=" + peopleNum + ", loveNum=" + loveNum
				+ ", applicant=" + applicant + ", where=" + where
				+ ", classRoom=" + classRoom + "]";
	}
	
	

}
