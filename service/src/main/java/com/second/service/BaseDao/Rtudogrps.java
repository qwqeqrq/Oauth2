package com.second.service.BaseDao;

import java.util.Date;

public class Rtudogrps {
    private Long id;

    private Long did;

    private Long blockid;

    private String name;

    private Integer maxdos;

    private Integer mm;

    private Integer mmm;

    private Integer hh;

    private Integer mhh;

    private Integer dd;

    private Integer mdd;

    private Integer mon;

    private Integer mmon;

    private Integer keepmins;

    private String kml;

    private Integer dripgrpidx;

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

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public Long getBlockid() {
        return blockid;
    }

    public void setBlockid(Long blockid) {
        this.blockid = blockid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getMaxdos() {
        return maxdos;
    }

    public void setMaxdos(Integer maxdos) {
        this.maxdos = maxdos;
    }

    public Integer getMm() {
        return mm;
    }

    public void setMm(Integer mm) {
        this.mm = mm;
    }

    public Integer getMmm() {
        return mmm;
    }

    public void setMmm(Integer mmm) {
        this.mmm = mmm;
    }

    public Integer getHh() {
        return hh;
    }

    public void setHh(Integer hh) {
        this.hh = hh;
    }

    public Integer getMhh() {
        return mhh;
    }

    public void setMhh(Integer mhh) {
        this.mhh = mhh;
    }

    public Integer getDd() {
        return dd;
    }

    public void setDd(Integer dd) {
        this.dd = dd;
    }

    public Integer getMdd() {
        return mdd;
    }

    public void setMdd(Integer mdd) {
        this.mdd = mdd;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public Integer getMmon() {
        return mmon;
    }

    public void setMmon(Integer mmon) {
        this.mmon = mmon;
    }

    public Integer getKeepmins() {
        return keepmins;
    }

    public void setKeepmins(Integer keepmins) {
        this.keepmins = keepmins;
    }

    public String getKml() {
        return kml;
    }

    public void setKml(String kml) {
        this.kml = kml == null ? null : kml.trim();
    }

    public Integer getDripgrpidx() {
        return dripgrpidx;
    }

    public void setDripgrpidx(Integer dripgrpidx) {
        this.dripgrpidx = dripgrpidx;
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