package com.second.service.BaseDao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public class SfCtpfM {
    private Long id;

    private Long fieldId;

    private String fieldSite;

    private String plantYear;

    private Long crpVrtId;

    private String sampleNm;

    private Float soiltestAn;

    private Float soiltestAp;

    private Float soiltestAk;

    private String testingTime;

    private Date establishTime;

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

    public String getPlantYear() {
        return plantYear;
    }

    public void setPlantYear(String plantYear) {
        this.plantYear = plantYear == null ? null : plantYear.trim();
    }

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public String getSampleNm() {
        return sampleNm;
    }

    public void setSampleNm(String sampleNm) {
        this.sampleNm = sampleNm == null ? null : sampleNm.trim();
    }

    public Float getSoiltestAn() {
        return soiltestAn;
    }

    public void setSoiltestAn(Float soiltestAn) {
        this.soiltestAn = soiltestAn;
    }

    public Float getSoiltestAp() {
        return soiltestAp;
    }

    public void setSoiltestAp(Float soiltestAp) {
        this.soiltestAp = soiltestAp;
    }

    public Float getSoiltestAk() {
        return soiltestAk;
    }

    public void setSoiltestAk(Float soiltestAk) {
        this.soiltestAk = soiltestAk;
    }

    public String getTestingTime() {
        return testingTime;
    }

    public void setTestingTime(String testingTime) {
        this.testingTime = testingTime == null ? null : testingTime.trim();
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }
}