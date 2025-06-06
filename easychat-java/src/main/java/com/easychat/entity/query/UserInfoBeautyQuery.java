package com.easychat.entity.query;



/**
 * 用户靓号表参数
 */
public class UserInfoBeautyQuery extends BaseParam {


	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 
	 */
	private String userId;

	private String userIdFuzzy;

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

	public void setEmailFuzzy(String emailFuzzy){
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy(){
		return this.emailFuzzy;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserIdFuzzy(String userIdFuzzy){
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy(){
		return this.userIdFuzzy;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

}
