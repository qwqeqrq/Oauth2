package com.second.service.BaseDao;

import java.util.Date;

public class OptIrrigationGroup {
    private Long id;

    private Long planid;

    private Long irrigationid;

    private Long ruleid;

    private Integer optindex;

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

    public Long getPlanid() {
        return planid;
    }

    public void setPlanid(Long planid) {
        this.planid = planid;
    }

    public Long getIrrigationid() {
        return irrigationid;
    }

    public void setIrrigationid(Long irrigationid) {
        this.irrigationid = irrigationid;
    }

    public Long getRuleid() {
        return ruleid;
    }

    public void setRuleid(Long ruleid) {
        this.ruleid = ruleid;
    }

    public Integer getOptindex() {
        return optindex;
    }

    public void setOptindex(Integer optindex) {
        this.optindex = optindex;
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