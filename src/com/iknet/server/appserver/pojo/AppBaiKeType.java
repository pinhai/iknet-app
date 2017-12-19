package com.iknet.server.appserver.pojo;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * 百科类型对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppBaiKeType extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private long id;

	/**
	 * 分类名称
	 */
	private String typeName;
	/**
	 * 分类名称(英文)
	 */
	private String typeName_En;

	/**
	 * 分类
	 */
	private String typeDesc;

	/**
	 * 分类(英文)
	 */
	private String typeDesc_En;

	/**
	 * 显示顺序
	 */
	private int sortIndex;

	/**
	 * 是否显示 1显示，0不显示
	 */
	private String isShow;

	/**
	 * 百科图标
	 * 
	 * <pre>
	 * A:糖尿病;B:高血压;C:冠心病;D:中风;E:恶性肿瘤;F:其他;
	 * </pre>
	 */
	private String baiKeType;

	/**
	 * 图标URL
	 */
	private String baiKeTypeImgUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName_En() {
		return typeName_En;
	}

	public void setTypeName_En(String typeName_En) {
		this.typeName_En = typeName_En;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getTypeDesc_En() {
		return typeDesc_En;
	}

	public void setTypeDesc_En(String typeDesc_En) {
		this.typeDesc_En = typeDesc_En;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getBaiKeType() {
		return baiKeType;
	}

	public void setBaiKeType(String baiKeType) {
		this.baiKeType = baiKeType;
	}

	public String getBaiKeTypeImgUrl() {
		return baiKeTypeImgUrl;
	}

	public void setBaiKeTypeImgUrl(String baiKeTypeImgUrl) {
		this.baiKeTypeImgUrl = baiKeTypeImgUrl;
	}

}
