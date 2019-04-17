package com.second.service.BaseDao;

import java.util.Date;

public class IrrigationPlanWorking {
    private Long blockid;

    private Long deviceid;

    private Long id;

    private String pname;

    private Long preid;

    private Integer pinterval;

    private Long fertilization;

    private String pumpset;

    private String closingdown;

    private Float temperature;

    private Integer waittime;

    private Integer fertilizertime;

    private Byte timeflg;

    private Byte runhour;

    private Byte runminite;

    private Byte mon;

    private Byte tuesflg;

    private Byte wedflg;

    private Byte thurflg;

    private Byte friflg;

    private Byte satflg;

    private Byte sunflg;

    private Byte loopflg;

    private Integer intermission;

    private Byte repeatflg;

    private Byte status;

    private Byte continues;

    private Byte flg;

    private Long userid;

    private Date firststarttime;

    private Date nextstarttime;

    private String planjson;

    private String startvaliddate;

    private String endvaliddate;

    private Integer excutecount;

    private Integer taskstatus;

    private String planschedule;

    private Date createdAt;

    private String createdUser;

    private Date updatedAt;

    private String updatedUser;

    public Long getBlockid() {
        return blockid;
    }

    public void setBlockid(Long blockid) {
        this.blockid = blockid;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public Long getPreid() {
        return preid;
    }

    public void setPreid(Long preid) {
        this.preid = preid;
    }

    public Integer getPinterval() {
        return pinterval;
    }

    public void setPinterval(Integer pinterval) {
        this.pinterval = pinterval;
    }

    public Long getFertilization() {
        return fertilization;
    }

    public void setFertilization(Long fertilization) {
        this.fertilization = fertilization;
    }

    public String getPumpset() {
        return pumpset;
    }

    public void setPumpset(String pumpset) {
        this.pumpset = pumpset == null ? null : pumpset.trim();
    }

    public String getClosingdown() {
        return closingdown;
    }

    public void setClosingdown(String closingdown) {
        this.closingdown = closingdown == null ? null : closingdown.trim();
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getWaittime() {
        return waittime;
    }

    public void setWaittime(Integer waittime) {
        this.waittime = waittime;
    }

    public Integer getFertilizertime() {
        return fertilizertime;
    }

    public void setFertilizertime(Integer fertilizertime) {
        this.fertilizertime = fertilizertime;
    }

    public Byte getTimeflg() {
        return timeflg;
    }

    public void setTimeflg(Byte timeflg) {
        this.timeflg = timeflg;
    }

    public Byte getRunhour() {
        return runhour;
    }

    public void setRunhour(Byte runhour) {
        this.runhour = runhour;
    }

    public Byte getRunminite() {
        return runminite;
    }

    public void setRunminite(Byte runminite) {
        this.runminite = runminite;
    }

    public Byte getMon() {
        return mon;
    }

    public void setMon(Byte mon) {
        this.mon = mon;
    }

    public Byte getTuesflg() {
        return tuesflg;
    }

    public void setTuesflg(Byte tuesflg) {
        this.tuesflg = tuesflg;
    }

    public Byte getWedflg() {
        return wedflg;
    }

    public void setWedflg(Byte wedflg) {
        this.wedflg = wedflg;
    }

    public Byte getThurflg() {
        return thurflg;
    }

    public void setThurflg(Byte thurflg) {
        this.thurflg = thurflg;
    }

    public Byte getFriflg() {
        return friflg;
    }

    public void setFriflg(Byte friflg) {
        this.friflg = friflg;
    }

    public Byte getSatflg() {
        return satflg;
    }

    public void setSatflg(Byte satflg) {
        this.satflg = satflg;
    }

    public Byte getSunflg() {
        return sunflg;
    }

    public void setSunflg(Byte sunflg) {
        this.sunflg = sunflg;
    }

    public Byte getLoopflg() {
        return loopflg;
    }

    public void setLoopflg(Byte loopflg) {
        this.loopflg = loopflg;
    }

    public Integer getIntermission() {
        return intermission;
    }

    public void setIntermission(Integer intermission) {
        this.intermission = intermission;
    }

    public Byte getRepeatflg() {
        return repeatflg;
    }

    public void setRepeatflg(Byte repeatflg) {
        this.repeatflg = repeatflg;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getContinues() {
        return continues;
    }

    public void setContinues(Byte continues) {
        this.continues = continues;
    }

    public Byte getFlg() {
        return flg;
    }

    public void setFlg(Byte flg) {
        this.flg = flg;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getFirststarttime() {
        return firststarttime;
    }

    public void setFirststarttime(Date firststarttime) {
        this.firststarttime = firststarttime;
    }

    public Date getNextstarttime() {
        return nextstarttime;
    }

    public void setNextstarttime(Date nextstarttime) {
        this.nextstarttime = nextstarttime;
    }

    public String getPlanjson() {
        return planjson;
    }

    public void setPlanjson(String planjson) {
        this.planjson = planjson == null ? null : planjson.trim();
    }

    public String getStartvaliddate() {
        return startvaliddate;
    }

    public void setStartvaliddate(String startvaliddate) {
        this.startvaliddate = startvaliddate == null ? null : startvaliddate.trim();
    }

    public String getEndvaliddate() {
        return endvaliddate;
    }

    public void setEndvaliddate(String endvaliddate) {
        this.endvaliddate = endvaliddate == null ? null : endvaliddate.trim();
    }

    public Integer getExcutecount() {
        return excutecount;
    }

    public void setExcutecount(Integer excutecount) {
        this.excutecount = excutecount;
    }

    public Integer getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(Integer taskstatus) {
        this.taskstatus = taskstatus;
    }

    public String getPlanschedule() {
        return planschedule;
    }

    public void setPlanschedule(String planschedule) {
        this.planschedule = planschedule == null ? null : planschedule.trim();
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