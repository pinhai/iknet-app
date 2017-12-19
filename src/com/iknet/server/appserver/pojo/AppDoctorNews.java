package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP名医文章对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppDoctorNews extends BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private long id;

	/**
	 * 所属医生
	 */
	private String doctorId;

	/**
	 * 文章标题
	 */
	private String title;

	/**
	 * 文章图片
	 */
	private String img;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 排序
	 */
	private int sortIndex;
	/**
	 * 文章内容
	 */
	private String newsContent;

	/**
	 * 点赞数
	 */
	private int niceTotal;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public int getNiceTotal() {
		return niceTotal;
	}

	public void setNiceTotal(int niceTotal) {
		this.niceTotal = niceTotal;
	}

}
