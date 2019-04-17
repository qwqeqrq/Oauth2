package com.second.service.BaseDao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public class SfTrgfcsM {
    private Integer id;

    private Long fieldId;

    private Date plantYear;

    private Long crpVrtId;

    private Float soilccNs;

    private Float soilccPs;

    private Float soilccKs;

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

    public Date getPlantYear() {
        return plantYear;
    }

    public void setPlantYear(Date plantYear) {
        this.plantYear = plantYear;
    }

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public Float getSoilccNs() {
        return soilccNs;
    }

    public void setSoilccNs(Float soilccNs) {
        this.soilccNs = soilccNs;
    }

    public Float getSoilccPs() {
        return soilccPs;
    }

    public void setSoilccPs(Float soilccPs) {
        this.soilccPs = soilccPs;
    }

    public Float getSoilccKs() {
        return soilccKs;
    }

    public void setSoilccKs(Float soilccKs) {
        this.soilccKs = soilccKs;
    }
}