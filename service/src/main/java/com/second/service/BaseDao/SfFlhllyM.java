package com.second.service.BaseDao;

public class SfFlhllyM {
    private Long id;

    private Integer fertType;

    private String fertName;

    private String fieldSite;

    private Float fertAn;

    private Float fertAp;

    private Float fertAk;

    private Float useeffiAn;

    private Float useeffiAp;

    private Float useeffiAk;

    private Integer crpStgD;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFertType() {
        return fertType;
    }

    public void setFertType(Integer fertType) {
        this.fertType = fertType;
    }

    public String getFertName() {
        return fertName;
    }

    public void setFertName(String fertName) {
        this.fertName = fertName == null ? null : fertName.trim();
    }

    public String getFieldSite() {
        return fieldSite;
    }

    public void setFieldSite(String fieldSite) {
        this.fieldSite = fieldSite == null ? null : fieldSite.trim();
    }

    public Float getFertAn() {
        return fertAn;
    }

    public void setFertAn(Float fertAn) {
        this.fertAn = fertAn;
    }

    public Float getFertAp() {
        return fertAp;
    }

    public void setFertAp(Float fertAp) {
        this.fertAp = fertAp;
    }

    public Float getFertAk() {
        return fertAk;
    }

    public void setFertAk(Float fertAk) {
        this.fertAk = fertAk;
    }

    public Float getUseeffiAn() {
        return useeffiAn;
    }

    public void setUseeffiAn(Float useeffiAn) {
        this.useeffiAn = useeffiAn;
    }

    public Float getUseeffiAp() {
        return useeffiAp;
    }

    public void setUseeffiAp(Float useeffiAp) {
        this.useeffiAp = useeffiAp;
    }

    public Float getUseeffiAk() {
        return useeffiAk;
    }

    public void setUseeffiAk(Float useeffiAk) {
        this.useeffiAk = useeffiAk;
    }

    public Integer getCrpStgD() {
        return crpStgD;
    }

    public void setCrpStgD(Integer crpStgD) {
        this.crpStgD = crpStgD;
    }
}