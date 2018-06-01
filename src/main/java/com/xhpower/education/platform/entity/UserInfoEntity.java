package com.xhpower.education.platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 用户基本信息表
 * </p>
 *
 * @author Lian YouJie
 * @since 2018-01-23
 */
@TableName(value = "user_info")
public class UserInfoEntity extends Model<UserInfoEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer user_id;

    /**
     * 用户id
     */
    private String user_id2;

    /**
     * 以email作为注册登录名
     */
    private String login_email;

    /**
     * 以手机号作为注册登录名
     */
    private String login_phone;

    /**
     * 用户姓名
     */
    private String real_name;

    /**
     * 登录密码
     */
    private String passwd;

    /**
     * 密码私钥
     */
    private String passwd_salt;

    /**
     * 是否被删除
     */
    private Boolean is_deleted;

    /**
     * 是否被禁用
     */
    private Boolean is_forbidden;

    /**
     * 是否被锁定
     */
    private Boolean is_locked;

    /**
     * 锁定人ID
     */
    private String locker_id;

    /**
     * 密码错误次数
     */
    private Integer wrong_pwd_times;

    /**
     * 最后锁定时间
     */
    private Date last_locked_time;

    /**
     * 用户类型id
     */
    private String user_type_id;

    private String image_id;

    /**
     * 性别
     */
    private String gender;

    private Date birthday;

    /**
     * 当前金贝
     */
    private Double current_points;

    /**
     * 累计使用积分
     */
    private Long total_points;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 最后修改人ID
     */
    private String last_modify_by;

    /**
     * 最后修改时间
     */
    private Date last_modify_time;

    /**
     * 是否激活
     */
    private Boolean is_activated;

    /**
     * 激活码
     */
    private String activate_code;

    /**
     * 发送邮件时间
     */
    private Date send_email_time;

    /**
     * 是否签到
     */
    private Boolean is_sign;

    /**
     * 连续签到数
     */
    private Integer sign_num;

    /**
     * 签到时间
     */
    private Date sign_time;

    /**
     * 用户手机唯一标识
     */
    private String device_token;

    private String third_account;

    private Integer third_account_type;

    /**
     * 注册来源 (批量新增,创新数字活动，再湘老书法大师)
     */
    private Integer resource_type;

    /**
     * 注册渠道 (PC,WEB,ANDRIOD,PAD)
     */
    private Integer device_type;

    /**
     * 隶属机构
     */
    private Integer vassalage_org;

    /**
     * 状态 销售单0代表认证通过,1代表认证未通过
     */
    private Integer sale_status;

    /**
     * 新手引导是否完成
     */
    private Boolean is_novice;

    /**
     * 资料完整度
     */
    private Integer integrity;

    /**
     * 当前贝壳
     */
    private Integer current_child_points;

    /**
     * 用户身份类(207001学生,207002教师,207003家长,207004普通用户 207005教研员 207006教研管理员)
     */
    private String role_type;

    /**
     * 籍贯
     */
    private String place;

    /**
     * 民族
     */
    private String nation;

    /**
     * 家庭住址
     */
    private String home_address;

    /**
     * 联系电话
     */
    private String link_phone;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 身份证号
     */
    private String id_card;

    /**
     * 证件照
     */
    private String id_photo;

    /**
     * 真实姓名
     */
    private String full_name;

    /**
     * 是否同步懂了么
     */
    private Integer dlm_if_synch;

    private String img_status;

    /**
     * 上级openId
     */
    private String parent_open_id;

    /**
     * 上级机构Id
     */
    private String parent_org_id;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_id2() {
        return user_id2;
    }

    public void setUser_id2(String user_id2) {
        this.user_id2 = user_id2;
    }

    public String getLogin_email() {
        return login_email;
    }

    public void setLogin_email(String login_email) {
        this.login_email = login_email;
    }

    public String getLogin_phone() {
        return login_phone;
    }

    public void setLogin_phone(String login_phone) {
        this.login_phone = login_phone;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswd_salt() {
        return passwd_salt;
    }

    public void setPasswd_salt(String passwd_salt) {
        this.passwd_salt = passwd_salt;
    }

    public Boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Boolean isIs_forbidden() {
        return is_forbidden;
    }

    public void setIs_forbidden(Boolean is_forbidden) {
        this.is_forbidden = is_forbidden;
    }

    public Boolean isIs_locked() {
        return is_locked;
    }

    public void setIs_locked(Boolean is_locked) {
        this.is_locked = is_locked;
    }

    public String getLocker_id() {
        return locker_id;
    }

    public void setLocker_id(String locker_id) {
        this.locker_id = locker_id;
    }

    public Integer getWrong_pwd_times() {
        return wrong_pwd_times;
    }

    public void setWrong_pwd_times(Integer wrong_pwd_times) {
        this.wrong_pwd_times = wrong_pwd_times;
    }

    public Date getLast_locked_time() {
        return last_locked_time;
    }

    public void setLast_locked_time(Date last_locked_time) {
        this.last_locked_time = last_locked_time;
    }

    public String getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(String user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getCurrent_points() {
        return current_points;
    }

    public void setCurrent_points(Double current_points) {
        this.current_points = current_points;
    }

    public Long getTotal_points() {
        return total_points;
    }

    public void setTotal_points(Long total_points) {
        this.total_points = total_points;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getLast_modify_by() {
        return last_modify_by;
    }

    public void setLast_modify_by(String last_modify_by) {
        this.last_modify_by = last_modify_by;
    }

    public Date getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(Date last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public Boolean isIs_activated() {
        return is_activated;
    }

    public void setIs_activated(Boolean is_activated) {
        this.is_activated = is_activated;
    }

    public String getActivate_code() {
        return activate_code;
    }

    public void setActivate_code(String activate_code) {
        this.activate_code = activate_code;
    }

    public Date getSend_email_time() {
        return send_email_time;
    }

    public void setSend_email_time(Date send_email_time) {
        this.send_email_time = send_email_time;
    }

    public Boolean isIs_sign() {
        return is_sign;
    }

    public void setIs_sign(Boolean is_sign) {
        this.is_sign = is_sign;
    }

    public Integer getSign_num() {
        return sign_num;
    }

    public void setSign_num(Integer sign_num) {
        this.sign_num = sign_num;
    }

    public Date getSign_time() {
        return sign_time;
    }

    public void setSign_time(Date sign_time) {
        this.sign_time = sign_time;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getThird_account() {
        return third_account;
    }

    public void setThird_account(String third_account) {
        this.third_account = third_account;
    }

    public Integer getThird_account_type() {
        return third_account_type;
    }

    public void setThird_account_type(Integer third_account_type) {
        this.third_account_type = third_account_type;
    }

    public Integer getResource_type() {
        return resource_type;
    }

    public void setResource_type(Integer resource_type) {
        this.resource_type = resource_type;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }

    public Integer getVassalage_org() {
        return vassalage_org;
    }

    public void setVassalage_org(Integer vassalage_org) {
        this.vassalage_org = vassalage_org;
    }

    public Integer getSale_status() {
        return sale_status;
    }

    public void setSale_status(Integer sale_status) {
        this.sale_status = sale_status;
    }

    public Boolean isIs_novice() {
        return is_novice;
    }

    public void setIs_novice(Boolean is_novice) {
        this.is_novice = is_novice;
    }

    public Integer getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Integer integrity) {
        this.integrity = integrity;
    }

    public Integer getCurrent_child_points() {
        return current_child_points;
    }

    public void setCurrent_child_points(Integer current_child_points) {
        this.current_child_points = current_child_points;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Integer getDlm_if_synch() {
        return dlm_if_synch;
    }

    public void setDlm_if_synch(Integer dlm_if_synch) {
        this.dlm_if_synch = dlm_if_synch;
    }

    public String getImg_status() {
        return img_status;
    }

    public void setImg_status(String img_status) {
        this.img_status = img_status;
    }

    public String getParent_open_id() {
        return parent_open_id;
    }

    public void setParent_open_id(String parent_open_id) {
        this.parent_open_id = parent_open_id;
    }

    public String getParent_org_id() {
        return parent_org_id;
    }

    public void setParent_org_id(String parent_org_id) {
        this.parent_org_id = parent_org_id;
    }

    @Override
    protected Serializable pkVal() {
        return this.user_id;
    }

}
