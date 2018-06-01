package com.xhpower.education.platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * @ClassName: SchoolEntity
 * @Description: 学校实体类
 * @author Lian Youjie
 * @date 2017年9月9日 下午5:05:15
 *
 */

@TableName("np_school")
public class SchoolEntity extends Model<SchoolEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学校名字
     */
    @TableField("sch_name")
    private String schName;

    // 单位座机号
    @TableField("unit_sitphone")
    private String unitSitPhone;

    // 单位手机号
    @TableField("unit_telphone")
    private String unitTelPhone;

    // 详细地址
    private String address;

    // 信息技术对接人姓名
    @TableField("inf_recei_man")
    private String infreceiman;

    // 信息技术对接人座机
    @TableField("inf_recei_sitp")
    private String infreceiSitP;

    // 信息技术对接人电话
    @TableField("inf_recei_phone")
    private String infreceiphone;

    // 校长联系方式座机
    @TableField("pre_sit_pho")
    private String preSitPho;

    // 校长联系电话
    @TableField("pre_phone")
    private String prePhone;

    /**
     * 学校简介
     */
    @TableField("sch_descript")
    private String schDescript;

    /**
     * 校长名字
     */
    @TableField("pre_name")
    private String preName;

    /**
     * 校长语录
     */
    @TableField("pre_saying")
    private String preSaying;

    /**
     * 网站主体颜色
     */
    @TableField("theme_color")
    private String themeColor;

    /**
     * 省
     */
    private String province;

    @TableField("province_text")
    private String provinceText;

    /**
     * 市
     */
    private String city;

    @TableField("city_text")
    private String cityText;

    /**
     * 区
     */
    private String area;

    @TableField("area_text")
    private String areaText;

    /**
     * 是否置顶 0：否 1：是
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 家校共育链接
     */
    @TableField("school_url")
    private String schoolUrl;

    /**
     * 资源路径 多个资源用，分隔离
     */
    @TableField("resource_str")
    private String resourceStr;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 二维码
     */
    @TableField("qr_code_url")
    private String qrCodeUrl;

    @TableField(exist = false)
    private boolean stationStatus;

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public SchoolEntity() {}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public SchoolEntity(Integer id, String schoolUrl) {
        this.id = id;
        this.schoolUrl = schoolUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getSchDescript() {
        return schDescript;
    }

    public void setSchDescript(String schDescript) {
        this.schDescript = schDescript;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName;
    }

    public String getPreSaying() {
        return preSaying;
    }

    public void setPreSaying(String preSaying) {
        this.preSaying = preSaying;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSchoolUrl() {
        return schoolUrl;
    }

    public void setSchoolUrl(String schoolUrl) {
        this.schoolUrl = schoolUrl;
    }

    public String getResourceStr() {
        return resourceStr;
    }

    public void setResourceStr(String resourceStr) {
        this.resourceStr = resourceStr;
    }

    public String getUnitSitPhone() {
        return unitSitPhone;
    }

    public void setUnitSitPhone(String unitSitPhone) {
        this.unitSitPhone = unitSitPhone;
    }

    public String getUnitTelPhone() {
        return unitTelPhone;
    }

    public void setUnitTelPhone(String unitTelPhone) {
        this.unitTelPhone = unitTelPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfreceiman() {
        return infreceiman;
    }

    public void setInfreceiman(String infreceiman) {
        this.infreceiman = infreceiman;
    }

    public String getInfreceiphone() {
        return infreceiphone;
    }

    public void setInfreceiphone(String infreceiphone) {
        this.infreceiphone = infreceiphone;
    }

    public String getPreSitPho() {
        return preSitPho;
    }

    public void setPreSitPho(String preSitPho) {
        this.preSitPho = preSitPho;
    }

    public String getPrePhone() {
        return prePhone;
    }

    public void setPrePhone(String prePhone) {
        this.prePhone = prePhone;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getAreaText() {
        return areaText;
    }

    public void setAreaText(String areaText) {
        this.areaText = areaText;
    }

    public String getInfreceiSitP() {
        return infreceiSitP;
    }

    public void setInfreceiSitP(String infreceiSitP) {
        this.infreceiSitP = infreceiSitP;
    }

    public boolean isStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(boolean stationStatus) {
        this.stationStatus = stationStatus;
    }

}
