package com.easychat.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;


/**
 * 用户靓号表
 */
public class UserInfoBeauty implements Serializable {


	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 
	 */
	private String email;

	/**
	 * 
	 */
	private String userId;

	/**
	 * 0:未使用 1:已使用
	 */
	private Integer status;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	@Override
	public String toString (){
		return "自增ID:"+(id == null ? "空" : id)+"，email:"+(email == null ? "空" : email)+"，userId:"+(userId == null ? "空" : userId)+"，0:未使用 1:已使用:"+(status == null ? "空" : status);
	}
}
