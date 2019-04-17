package com.second.service.BaseDao;

import java.util.Date;

public class IrBcropM {
    private Long id;

    private Long crpVrtId;

    private String pname;

    private Long pid;

    private String dname;

    private Long did;

    private Integer cid;

    private String cname;

    private Float crpBcrop;

    private Float crpBscrop;

    private String crpvrtseeddatem;

    private String crpvrtseeddated;

    private String crpvrthvsdatem;

    private String crpvrthvsdated;

    private Date crpJyzGx;

    private Long fieid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCrpVrtId() {
        return crpVrtId;
    }

    public void setCrpVrtId(Long crpVrtId) {
        this.crpVrtId = crpVrtId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname == null ? null : dname.trim();
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public Float getCrpBcrop() {
        return crpBcrop;
    }

    public void setCrpBcrop(Float crpBcrop) {
        this.crpBcrop = crpBcrop;
    }

    public Float getCrpBscrop() {
        return crpBscrop;
    }

    public void setCrpBscrop(Float crpBscrop) {
        this.crpBscrop = crpBscrop;
    }

    public String getCrpvrtseeddatem() {
        return crpvrtseeddatem;
    }

    public void setCrpvrtseeddatem(String crpvrtseeddatem) {
        this.crpvrtseeddatem = crpvrtseeddatem == null ? null : crpvrtseeddatem.trim();
    }

    public String getCrpvrtseeddated() {
        return crpvrtseeddated;
    }

    public void setCrpvrtseeddated(String crpvrtseeddated) {
        this.crpvrtseeddated = crpvrtseeddated == null ? null : crpvrtseeddated.trim();
    }

    public String getCrpvrthvsdatem() {
        return crpvrthvsdatem;
    }

    public void setCrpvrthvsdatem(String crpvrthvsdatem) {
        this.crpvrthvsdatem = crpvrthvsdatem == null ? null : crpvrthvsdatem.trim();
    }

    public String getCrpvrthvsdated() {
        return crpvrthvsdated;
    }

    public void setCrpvrthvsdated(String crpvrthvsdated) {
        this.crpvrthvsdated = crpvrthvsdated == null ? null : crpvrthvsdated.trim();
    }

    public Date getCrpJyzGx() {
        return crpJyzGx;
    }

    public void setCrpJyzGx(Date crpJyzGx) {
        this.crpJyzGx = crpJyzGx;
    }

    public Long getFieid() {
        return fieid;
    }

    public void setFieid(Long fieid) {
        this.fieid = fieid;
    }
}