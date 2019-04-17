package com.second.service.BaseDao;

import java.util.Date;

public class SfDgggscM {
    private Long id;

    private Long fieldId;

    private String fieldSite;

    private Long crpVrtId;

    private Integer cropGds;

    private Integer fertTotalTimes;

    private String irriGroupNum;

    private Date crpEtarealDate;

    private Date irriGroupStart;

    private Date irriGroupStop;

    private String irriValveName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldSite() {
        return fieldSite;
    }

    public void setFieldSite(String fieldSite) {
        this.fieldSite = fieldSite == null ? null : fieldSite.trim();
    }

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public Integer getCropGds() {
        return cropGds;
    }

    public void setCropGds(Integer cropGds) {
        this.cropGds = cropGds;
    }

    public Integer getFertTotalTimes() {
        return fertTotalTimes;
    }

    public void setFertTotalTimes(Integer fertTotalTimes) {
        this.fertTotalTimes = fertTotalTimes;
    }

    public String getIrriGroupNum() {
        return irriGroupNum;
    }

    public void setIrriGroupNum(String irriGroupNum) {
        this.irriGroupNum = irriGroupNum == null ? null : irriGroupNum.trim();
    }

    public Date getCrpEtarealDate() {
        return crpEtarealDate;
    }

    public void setCrpEtarealDate(Date crpEtarealDate) {
        this.crpEtarealDate = crpEtarealDate;
    }

    public Date getIrriGroupStart() {
        return irriGroupStart;
    }

    public void setIrriGroupStart(Date irriGroupStart) {
        this.irriGroupStart = irriGroupStart;
    }

    public Date getIrriGroupStop() {
        return irriGroupStop;
    }

    public void setIrriGroupStop(Date irriGroupStop) {
        this.irriGroupStop = irriGroupStop;
    }

    public String getIrriValveName() {
        return irriValveName;
    }

    public void setIrriValveName(String irriValveName) {
        this.irriValveName = irriValveName == null ? null : irriValveName.trim();
    }
}