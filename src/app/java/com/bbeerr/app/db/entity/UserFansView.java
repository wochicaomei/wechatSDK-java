package com.bbeerr.app.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "v_app_user_fans")
public class UserFansView implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer userId;
	private Integer fansId;

	private String usercode;
	private String nickname;
	private String url;
	private String picSize;

	private String fans_usercode;
	private String fans_nickname;
	private String fans_url;
	private String fans_picSize;

	private Date createdatetime;
	private Date updatedatetime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFansId() {
		return fansId;
	}

	public void setFansId(Integer fansId) {
		this.fansId = fansId;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicSize() {
		return picSize;
	}

	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}

	public String getFans_usercode() {
		return fans_usercode;
	}

	public void setFans_usercode(String fans_usercode) {
		this.fans_usercode = fans_usercode;
	}

	public String getFans_nickname() {
		return fans_nickname;
	}

	public void setFans_nickname(String fans_nickname) {
		this.fans_nickname = fans_nickname;
	}

	public String getFans_url() {
		return fans_url;
	}

	public void setFans_url(String fans_url) {
		this.fans_url = fans_url;
	}

	public String getFans_picSize() {
		return fans_picSize;
	}

	public void setFans_picSize(String fans_picSize) {
		this.fans_picSize = fans_picSize;
	}

	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Date getUpdatedatetime() {
		return updatedatetime;
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

}