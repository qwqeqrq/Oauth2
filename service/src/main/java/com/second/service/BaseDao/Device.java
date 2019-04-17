package com.second.service.BaseDao;

import java.util.Date;

public class Device {
    private Long id;

    private String serialno;

    private String iscadaid;

    private Long provinceid;

    private Long cityid;

    private Long districtid;

    private String town;

    private String village;

    private Long blockid;

    private Long basinid;

    private Long irrigationid;

    private Long projectid;

    private String maker;

    private String model;

    private String dclass;

    private Long fdeviceid;

    private String dname;

    private String latitude;

    private String longitude;

    private String status;

    private String photourl;

    private String extension;

    private String points;

    private Integer radius;

    private Byte delflg;

    private Byte validflag;

    private Long pid;

    private Integer dripidx;

    private Long gid;

    private Integer ordernum;

    private String initialization;

    private String kml;

    private Date createdAt;

    private String createdUser;

    private Date updatedAt;

    private String updatedUser;

    private String iswatermeter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno == null ? null : serialno.trim();
    }

    public String getIscadaid() {
        return iscadaid;
    }

    public void setIscadaid(String iscadaid) {
        this.iscadaid = iscadaid == null ? null : iscadaid.trim();
    }

    public Long getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Long provinceid) {
        this.provinceid = provinceid;
    }

    public Long getCityid() {
        return cityid;
    }

    public void setCityid(Long cityid) {
        this.cityid = cityid;
    }

    public Long getDistrictid() {
        return districtid;
    }

    public void setDistrictid(Long districtid) {
        this.districtid = districtid;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town == null ? null : town.trim();
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village == null ? null : village.trim();
    }

    public Long getBlockid() {
        return blockid;
    }

    public void setBlockid(Long blockid) {
        this.blockid = blockid;
    }

    public Long getBasinid() {
        return basinid;
    }

    public void setBasinid(Long basinid) {
        this.basinid = basinid;
    }

    public Long getIrrigationid() {
        return irrigationid;
    }

    public void setIrrigationid(Long irrigationid) {
        this.irrigationid = irrigationid;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker == null ? null : maker.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getDclass() {
        return dclass;
    }

    public void setDclass(String dclass) {
        this.dclass = dclass == null ? null : dclass.trim();
    }

    public Long getFdeviceid() {
        return fdeviceid;
    }

    public void setFdeviceid(Long fdeviceid) {
        this.fdeviceid = fdeviceid;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname == null ? null : dname.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl == null ? null : photourl.trim();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points == null ? null : points.trim();
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Byte getDelflg() {
        return delflg;
    }

    public void setDelflg(Byte delflg) {
        this.delflg = delflg;
    }

    public Byte getValidflag() {
        return validflag;
    }

    public void setValidflag(Byte validflag) {
        this.validflag = validflag;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getDripidx() {
        return dripidx;
    }

    public void setDripidx(Integer dripidx) {
        this.dripidx = dripidx;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public String getInitialization() {
        return initialization;
    }

    public void setInitialization(String initialization) {
        this.initialization = initialization == null ? null : initialization.trim();
    }

    public String getKml() {
        return kml;
    }

    public void setKml(String kml) {
        this.kml = kml == null ? null : kml.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public String getIswatermeter() {
        return iswatermeter;
    }

    public void setIswatermeter(String iswatermeter) {
        this.iswatermeter = iswatermeter == null ? null : iswatermeter.trim();
    }
}