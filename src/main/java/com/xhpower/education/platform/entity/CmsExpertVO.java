package com.xhpower.education.platform.entity;

/**
 * <p>
 * 学术成果专家表
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-09
 */
public class CmsExpertVO extends CmsExpertEntity {

    private static final long serialVersionUID = 1L;

    private Integer current = 1;

    private Integer size = 10;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
