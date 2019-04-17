package com.second.service.BaseDao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class SfZddgsfzdscM {
    private Long id;

    private Long fieldId;

    private Long crpVrtId;

    private String crpStgName;

    private Integer crpStgId;

    private String crpEtarealDate;

    private Float fansI;

    private Float fapsI;

    private Float faksI;

    private Float fanDripBasic;

    private Float fapDripBasic;

    private Float fakDripBasic;

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

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public String getCrpStgName() {
        return crpStgName;
    }

    public void setCrpStgName(String crpStgName) {
        this.crpStgName = crpStgName == null ? null : crpStgName.trim();
    }

    public Integer getCrpStgId() {
        return crpStgId;
    }

    public void setCrpStgId(Integer crpStgId) {
        this.crpStgId = crpStgId;
    }

    public String getCrpEtarealDate() {
        return crpEtarealDate;
    }

    public void setCrpEtarealDate(String crpEtarealDate) {
        this.crpEtarealDate = crpEtarealDate;
    }

    public Float getFansI() {
        return fansI;
    }

    public void setFansI(Float fansI) {
        this.fansI = fansI;
    }

    public Float getFapsI() {
        return fapsI;
    }

    public void setFapsI(Float fapsI) {
        this.fapsI = fapsI;
    }

    public Float getFaksI() {
        return faksI;
    }

    public void setFaksI(Float faksI) {
        this.faksI = faksI;
    }

    public Float getFanDripBasic() {
        return fanDripBasic;
    }

    public void setFanDripBasic(Float fanDripBasic) {
        this.fanDripBasic = fanDripBasic;
    }

    public Float getFapDripBasic() {
        return fapDripBasic;
    }

    public void setFapDripBasic(Float fapDripBasic) {
        this.fapDripBasic = fapDripBasic;
    }

    public Float getFakDripBasic() {
        return fakDripBasic;
    }

    public void setFakDripBasic(Float fakDripBasic) {
        this.fakDripBasic = fakDripBasic;
    }
}