package com.second.service.BaseDao;

import java.util.Date;

public class IrField {
    private Long id;

    private Long fieldId;

    private Date crpYymm;

    private Long crpVrtId;

    private Date crpHvsd;

    private Float fieldCrpY;

    private Float fieldCrpEt;

    private Date crpJyzGx;

    private Integer cropRow;

    private Integer cropLine;

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

    public Date getCrpYymm() {
        return crpYymm;
    }

    public void setCrpYymm(Date crpYymm) {
        this.crpYymm = crpYymm;
    }

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public Date getCrpHvsd() {
        return crpHvsd;
    }

    public void setCrpHvsd(Date crpHvsd) {
        this.crpHvsd = crpHvsd;
    }

    public Float getFieldCrpY() {
        return fieldCrpY;
    }

    public void setFieldCrpY(Float fieldCrpY) {
        this.fieldCrpY = fieldCrpY;
    }

    public Float getFieldCrpEt() {
        return fieldCrpEt;
    }

    public void setFieldCrpEt(Float fieldCrpEt) {
        this.fieldCrpEt = fieldCrpEt;
    }

    public Date getCrpJyzGx() {
        return crpJyzGx;
    }

    public void setCrpJyzGx(Date crpJyzGx) {
        this.crpJyzGx = crpJyzGx;
    }

    public Integer getCropRow() {
        return cropRow;
    }

    public void setCropRow(Integer cropRow) {
        this.cropRow = cropRow;
    }

    public Integer getCropLine() {
        return cropLine;
    }

    public void setCropLine(Integer cropLine) {
        this.cropLine = cropLine;
    }
}