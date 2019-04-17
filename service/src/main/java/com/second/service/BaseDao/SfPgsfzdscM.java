package com.second.service.BaseDao;

import java.util.Date;

public class SfPgsfzdscM {
    private Long id;

    private Long fieldId;

    private String fieldSite;

    private String crpStgName;

    private Integer fertITime;

    private Integer crpStgId;

    private Long crpVrtId;

    private Date crpEtarealDate;

    private Float fanI;

    private Float fapI;

    private Float fakI;

    private Float fanSprayBasic;

    private Float fapSprayBasic;

    private Float fakSprayBasic;

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

    public String getCrpStgName() {
        return crpStgName;
    }

    public void setCrpStgName(String crpStgName) {
        this.crpStgName = crpStgName == null ? null : crpStgName.trim();
    }

    public Integer getFertITime() {
        return fertITime;
    }

    public void setFertITime(Integer fertITime) {
        this.fertITime = fertITime;
    }

    public Integer getCrpStgId() {
        return crpStgId;
    }

    public void setCrpStgId(Integer crpStgId) {
        this.crpStgId = crpStgId;
    }

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public Date getCrpEtarealDate() {
        return crpEtarealDate;
    }

    public void setCrpEtarealDate(Date crpEtarealDate) {
        this.crpEtarealDate = crpEtarealDate;
    }

    public Float getFanI() {
        return fanI;
    }

    public void setFanI(Float fanI) {
        this.fanI = fanI;
    }

    public Float getFapI() {
        return fapI;
    }

    public void setFapI(Float fapI) {
        this.fapI = fapI;
    }

    public Float getFakI() {
        return fakI;
    }

    public void setFakI(Float fakI) {
        this.fakI = fakI;
    }

    public Float getFanSprayBasic() {
        return fanSprayBasic;
    }

    public void setFanSprayBasic(Float fanSprayBasic) {
        this.fanSprayBasic = fanSprayBasic;
    }

    public Float getFapSprayBasic() {
        return fapSprayBasic;
    }

    public void setFapSprayBasic(Float fapSprayBasic) {
        this.fapSprayBasic = fapSprayBasic;
    }

    public Float getFakSprayBasic() {
        return fakSprayBasic;
    }

    public void setFakSprayBasic(Float fakSprayBasic) {
        this.fakSprayBasic = fakSprayBasic;
    }
}