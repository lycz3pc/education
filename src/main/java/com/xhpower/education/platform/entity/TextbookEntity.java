package com.xhpower.education.platform.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@TableName(value = "np_textbook")
public class TextbookEntity extends Model<TextbookEntity> implements java.io.Serializable{

    private static final long serialVersionUID = -3204371223050107573L;

    private Integer id;
    private String name;
    //private String topic;
    private String type;
    @TableField("type_text")
    private String typeText;
   /* @TableField(exist = false)
    private String[] flag;*/
    private String section;
    private String grade;
    private String give;
  //  private String feature;
    private Integer state;
    @TableField("cover_img")
    private String coverimg;//封面图片
   // private Integer sequence;//排序
    //private String hour;
   // private String target;
   // private BigDecimal price;
    private String url;
    @TableField("create_time")
    private Date createtime;
    @TableField("update_time")
    private Date updatetime;
    @TableField("is_top")
    private Integer isTop;//是否置顶 1：置顶 0：不置顶
    private String recommend;
    private String author;
    private Integer browse;

    @TableField(exist=false)
    private String imgpath;

    public Integer getId() {
        return id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGive() {
        return give;
    }

    public void setGive(String give) {
        this.give = give;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }


 /*   public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

/*	public String[] getFlag() {
		return flag;
	}

	public void setFlag(String[] flag) {
		this.flag = flag;
	}*/

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

	public Integer getBrowse() {
		return browse;
	}

	public void setBrowse(Integer browse) {
		this.browse = browse;
	}
	
	public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}