package com.xhpower.education.platform.entity;

/**
 * X贝壳网数字课程表
 *
 * @author Lian YouJie
 * @since 2018-01-05
 */
public class DigitCourseDto extends DigitCourse {

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
