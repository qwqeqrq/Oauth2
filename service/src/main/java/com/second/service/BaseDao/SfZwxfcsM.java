package com.second.service.BaseDao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class SfZwxfcsM {
    private Integer id;

    private Long fieldId;

    private Long plantSpecies;

    private Integer crpPrdId;

    private Float hyieldAn;

    private Float hyieldAp;

    private Float hyieldAk;

    private Float fertanSiR;

    private Float fertapSiR;

    private Float fertakSiR;

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

    public Long getPlantSpecies() {
        return plantSpecies;
    }

    public void setPlantSpecies(Long plantSpecies) {
        this.plantSpecies = plantSpecies;
    }

    public Integer getCrpPrdId() {
        return crpPrdId;
    }

    public void setCrpPrdId(Integer crpPrdId) {
        this.crpPrdId = crpPrdId;
    }

    public Float getHyieldAn() {
        return hyieldAn;
    }

    public void setHyieldAn(Float hyieldAn) {
        this.hyieldAn = hyieldAn;
    }

    public Float getHyieldAp() {
        return hyieldAp;
    }

    public void setHyieldAp(Float hyieldAp) {
        this.hyieldAp = hyieldAp;
    }

    public Float getHyieldAk() {
        return hyieldAk;
    }

    public void setHyieldAk(Float hyieldAk) {
        this.hyieldAk = hyieldAk;
    }

    public Float getFertanSiR() {
        return fertanSiR;
    }

    public void setFertanSiR(Float fertanSiR) {
        this.fertanSiR = fertanSiR;
    }

    public Float getFertapSiR() {
        return fertapSiR;
    }

    public void setFertapSiR(Float fertapSiR) {
        this.fertapSiR = fertapSiR;
    }

    public Float getFertakSiR() {
        return fertakSiR;
    }

    public void setFertakSiR(Float fertakSiR) {
        this.fertakSiR = fertakSiR;
    }
}