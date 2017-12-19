package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP回答表对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppAnswer extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 对应的问题id
	 */
	private String questionId;

	/**
	 * 回答内容
	 */
	private String answerContent;

	/**
	 * 回答时间
	 */
	private Date createTime;

	/**
	 * 回答人名称
	 */
	private String userName;

	/**
	 * 回答标识
	 * 
	 * <pre>
	 * 0:匿名;1:注册用户;2:医生
	 * </pre>
	 */
	private String answerFlag;

	/**
	 * 回答用户ID
	 */
	private String personId;
	/**
	 * 提问内容
	 */
	private String questionContent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAnswerFlag() {
		return answerFlag;
	}

	public void setAnswerFlag(String answerFlag) {
		this.answerFlag = answerFlag;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

}
