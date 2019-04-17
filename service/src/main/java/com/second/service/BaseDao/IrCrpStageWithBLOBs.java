package com.second.service.BaseDao;

public class IrCrpStageWithBLOBs extends IrCrpStage {
    private String crpStgCrt;

    private String crpStgMng;

    public String getCrpStgCrt() {
        return crpStgCrt;
    }

    public void setCrpStgCrt(String crpStgCrt) {
        this.crpStgCrt = crpStgCrt == null ? null : crpStgCrt.trim();
    }

    public String getCrpStgMng() {
        return crpStgMng;
    }

    public void setCrpStgMng(String crpStgMng) {
        this.crpStgMng = crpStgMng == null ? null : crpStgMng.trim();
    }
}