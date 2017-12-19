package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP知识表对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppKnowledge extends BaseSerializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 文章ID
     */
    private long id;
    
    /**
     * 文章所属类型
     */
    private long typeId;
    
    /**
     * 文章标题
     */
    private String title;
    
    /**
     * 文章图片，保存图片所在url
     */
    private String titleImg;
    
    /**
     * 文章创建时间
     */
    private Date createTime;
    
    /**
     * 文章排序
     */
    private int sortIndex;
    
    /**
     * 是否显示 1显示 0不显示
     */
    private String isShow;
    
    /**
     * 文章内容
     */
    private String content;
    
    /**
     * 点赞数
     */
    private int niceTotal;
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public long getTypeId()
    {
        return typeId;
    }
    
    public void setTypeId(long typeId)
    {
        this.typeId = typeId;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getTitleImg()
    {
        return titleImg;
    }
    
    public void setTitleImg(String titleImg)
    {
        this.titleImg = titleImg;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public int getSortIndex()
    {
        return sortIndex;
    }
    
    public void setSortIndex(int sortIndex)
    {
        this.sortIndex = sortIndex;
    }
    
    public String getIsShow()
    {
        return isShow;
    }
    
    public void setIsShow(String isShow)
    {
        this.isShow = isShow;
    }
    
    public int getNiceTotal()
    {
        return niceTotal;
    }
    
    public void setNiceTotal(int niceTotal)
    {
        this.niceTotal = niceTotal;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
    
}
