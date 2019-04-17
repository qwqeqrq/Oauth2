package com.second.service.BaseDao;

import java.util.Date;

public class Area {
    private Long id;

    private String number;

    private Integer type;

    private Integer gradetype;

    private Long fatherid;

    private String aname;

    private Integer atype;

    private Double lat;

    private Double lng;

    private String points;

    private Integer radius;

    private Integer color;

    private String projinfo;

    private String abbreviation;

    private Float area;

    private Date createdAt;

    private String createdUser;

    private Date updatedAt;

    private String updatedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGradetype() {
        return gradetype;
    }

    public void setGradetype(Integer gradetype) {
        this.gradetype = gradetype;
    }

    public Long getFatherid() {
        return fatherid;
    }

    public void setFatherid(Long fatherid) {
        this.fatherid = fatherid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname == null ? null : aname.trim();
    }

    public Integer getAtype() {
        return atype;
    }

    public void setAtype(Integer atype) {
        this.atype = atype;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getProjinfo() {
        return projinfo;
    }

    public void setProjinfo(String projinfo) {
        this.projinfo = projinfo == null ? null : projinfo.trim();
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation == null ? null : abbreviation.trim();
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
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
}