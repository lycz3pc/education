package com.xhpower.education.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * X贝壳网数字课程表
 *
 * @author Lian YouJie
 * @since 2018-01-05
 */
@TableName(value = "np_digit_course_status")
public class DigitCourse {

	/**
     * 课程id
     */
	@TableId(value = "digit_course_id")
    private Integer digitCourseId;

    /**
     * 数字课程名称
     */
    @TableField(exist = false)
    private String digitCourseName;

    /**
     * 置顶状态
     */
    private String status;
    
    /**
     * 1已标识为每日听书
     */
    @TableField(value = "is_book_audio")
    private String isBookAudio;
    
    /**
     * 1视频课程 2音频课程
     */
    @TableField(exist = false)
    private String digitCourseType;


	public String getDigitCourseType() {
		return digitCourseType;
	}

	public void setDigitCourseType(String digitCourseType) {
		this.digitCourseType = digitCourseType;
	}

	public String getIsBookAudio() {
		return isBookAudio;
	}

	public void setIsBookAudio(String isBookAudio) {
		this.isBookAudio = isBookAudio;
	}

	public Integer getDigitCourseId() {
        return digitCourseId;
    }

    public void setDigitCourseId(Integer digitCourseId) {
        this.digitCourseId = digitCourseId;
    }

    public String getDigitCourseName() {
        return digitCourseName;
    }

    public void setDigitCourseName(String digitCourseName) {
        this.digitCourseName = digitCourseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
