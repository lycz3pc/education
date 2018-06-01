package com.xhpower.education.platform.entity;

/**
 * 
 * @ClassName: CmsEntity
 * @Description: 内容发布查询实体类
 * @author Lian Youjie
 * @date 2017年8月29日 上午11:11:55
 *
 */
public class CustomerServiceQueryVO extends NpCustomerService {


    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = -5948454037452126188L;

    /**
     * 页码
     */
    private Integer current;

    /**
     * 每页行数
     */
    private Integer size;

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
