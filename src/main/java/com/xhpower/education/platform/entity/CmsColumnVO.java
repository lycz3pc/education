package com.xhpower.education.platform.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: CmsColumnVO
 * @Description: 栏目返回实体类
 * @author Lian YouJie
 * @date 2017年8月28日 上午10:52:28
 */
public class CmsColumnVO extends CmsColumn {

    private static final long serialVersionUID = 1L;

    private String text;

    private List<CmsColumnVO> children = new ArrayList<CmsColumnVO>();

    public CmsColumnVO() {}

    // 构造方法，方便后续使用
    public CmsColumnVO(String id, String columnName, List<CmsColumnVO> list) {
        this.id = id;
        this.text = columnName;
        this.cloumnName = columnName;
        this.children = list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CmsColumnVO> getChildren() {
        return children;
    }

    public void setChildren(List<CmsColumnVO> children) {
        this.children = children;
    }

}
