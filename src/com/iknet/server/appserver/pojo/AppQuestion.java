package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP问答表对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppQuestion extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 问题内容
	 */
	private String questionContent;

	/**
	 * 所属分类
	 */
	private long typeId;

	/**
	 * 回答总数
	 */
	private int answerTotal;

	/**
	 * 是否显示 1显示 0不显示
	 */
	private String isShow;

	/**
	 * 提问时间
	 */
	private Date createTime;

	/**
	 * 用户 Id
	 */
	private String personId;

	/**
	 * 提问人姓名
	 */
	private String userName;
	/**
	 * 医生回答 标识 N:未被回答 Y:已被回答
	 */
	private String doctorAnswerFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public int getAnswerTotal() {
		return answerTotal;
	}

	public void setAnswerTotal(int answerTotal) {
		this.answerTotal = answerTotal;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDoctorAnswerFlag() {
		return doctorAnswerFlag;
	}

	public void setDoctorAnswerFlag(String doctorAnswerFlag) {
		this.doctorAnswerFlag = doctorAnswerFlag;
	}

}
