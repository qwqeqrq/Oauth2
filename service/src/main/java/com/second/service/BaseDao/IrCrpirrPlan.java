package com.second.service.BaseDao;

import java.util.Date;

public class IrCrpirrPlan {
    private Integer id;

    private Long fieldId;

    private Long crpVrtId;

    private String crpirrPlanId;

    private String crpirrPlanNt;

    private String crpirrPlanType;

    private Date crpirrPlanTm;

    private Date crpirrDate;

    private Float crpirrWdfc;

    private Float crpirrIrrData;

    private Float crpirrPlanData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public String getCrpirrPlanId() {
        return crpirrPlanId;
    }

    public void setCrpirrPlanId(String crpirrPlanId) {
        this.crpirrPlanId = crpirrPlanId == null ? null : crpirrPlanId.trim();
    }

    public String getCrpirrPlanNt() {
        return crpirrPlanNt;
    }

    public void setCrpirrPlanNt(String crpirrPlanNt) {
        this.crpirrPlanNt = crpirrPlanNt == null ? null : crpirrPlanNt.trim();
    }

    public String getCrpirrPlanType() {
        return crpirrPlanType;
    }

    public void setCrpirrPlanType(String crpirrPlanType) {
        this.crpirrPlanType = crpirrPlanType == null ? null : crpirrPlanType.trim();
    }

    public Date getCrpirrPlanTm() {
        return crpirrPlanTm;
    }

    public void setCrpirrPlanTm(Date crpirrPlanTm) {
        this.crpirrPlanTm = crpirrPlanTm;
    }

    public Date getCrpirrDate() {
        return crpirrDate;
    }

    public void setCrpirrDate(Date crpirrDate) {
        this.crpirrDate = crpirrDate;
    }

    public Float getCrpirrWdfc() {
        return crpirrWdfc;
    }

    public void setCrpirrWdfc(Float crpirrWdfc) {
        this.crpirrWdfc = crpirrWdfc;
    }

    public Float getCrpirrIrrData() {
        return crpirrIrrData;
    }

    public void setCrpirrIrrData(Float crpirrIrrData) {
        this.crpirrIrrData = crpirrIrrData;
    }

    public Float getCrpirrPlanData() {
        return crpirrPlanData;
    }

    public void setCrpirrPlanData(Float crpirrPlanData) {
        this.crpirrPlanData = crpirrPlanData;
    }
}