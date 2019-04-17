package com.second.service.Math;

import com.second.service.BaseDao.SfFlhllyM;
import com.second.service.Service.SfFlhllyMService;
import com.second.service.Service.SfSddgsfzdscMService;
import com.second.service.Service.SfZddgsfzdscMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisFertUseful {
    @Autowired
    SfFlhllyMService sfFlhllyMService;
    @Autowired
    SfZddgsfzdscMService sfZddgsfzdscMService;
    @Autowired
    SfSddgsfzdscMService sfSddgsfzdscMService;

    // 滴灌判断
    public Map<String, String> SprinkBottomFertAnalysisDi(float Fn, float Fk, float Fp, String fid1, String fid2, String fid3, String fieldid, String vatid, String FertType) {
        Map<String, String> map = new HashMap<String, String>();
        // 获取1.2.3肥的含量和使用率
        List<SfFlhllyM> FertPara = sfFlhllyMService.SelectFertType(fid1, fid2, fid3);
        float βn1 = FertPara.get(0).getFertAn();
        float βk1 = FertPara.get(0).getFertAk();
        float βp1 = FertPara.get(0).getFertAp();
        float Kp1 = FertPara.get(0).getUseeffiAp();
        float Kn1 = FertPara.get(0).getUseeffiAn();
        float Kk1 = FertPara.get(0).getUseeffiAk();

        float βn2 = FertPara.get(1).getFertAn();
        float βk2 = FertPara.get(1).getFertAk();
        float βp2 = FertPara.get(1).getFertAp();
        float Kp2 = FertPara.get(1).getUseeffiAp();
        float Kn2 = FertPara.get(1).getUseeffiAn();
        float Kk2 = FertPara.get(1).getUseeffiAk();

        float βn3 = FertPara.get(2).getFertAn();
        float βk3 = FertPara.get(2).getFertAk();
        float βp3 = FertPara.get(2).getFertAp();
        float Kp3 = FertPara.get(2).getUseeffiAp();
        float Kn3 = FertPara.get(2).getUseeffiAn();
        float Kk3 = FertPara.get(2).getUseeffiAk();
        // 根据地块ID和种植植物ID查询底肥K元素含量
        String FertData = null;
        if (FertType.equals("1")) {
            FertData = sfSddgsfzdscMService.selectBottomFert(fieldid, vatid);
        } else {
            FertData = sfZddgsfzdscMService.selectBottomFert(fieldid, vatid);
        }
        float Fandb = Float.parseFloat(FertData.split(",")[0]);
        float Fapdb = Float.parseFloat(FertData.split(",")[1]);
        float Fakdb = Float.parseFloat(FertData.split(",")[2]);
        String WaringN = null;
        String WaringP = null;
        String WaringK = null;
        if ((Fn * βn1 * Kn1 + Fn * βn2 * Kn2 + Fn * βn3 * Kn3) > Fandb) {
            WaringN = "氮";
        }
        if ((Fp * βp1 * Kp1 + Fp * βp2 * Kp2 + Fp * βp3 * Kp3) > Fapdb) {
            WaringP = "磷";
        }
        if ((Fk * βk1 * Kk1 + Fk * βk2 * Kk2 + Fk * βk3 * Kk3) > Fakdb) {
            WaringK = "钾";
        }
        if (WaringN == null) {
            WaringN = "";
        }
        if (WaringK == null) {
            WaringK = "";
        }
        if (WaringP == null) {
            WaringP = "";
        }
        String a = "";
        String b = "";
        if ((WaringN == "氮" && WaringK == "钾")
                || (WaringN == "氮" && WaringP == "磷")) {
            a = "、";
        }
        if (WaringP == "磷" && WaringK == "钾") {
            b = "、";
        }
        if (WaringN == "" && WaringK == "" && WaringP == "") {
            String msg = "";
            map.put("warning", msg);
        } else {
            String msg = "请选择不同种类的" + WaringN + a + WaringP + b + WaringK + "元素肥，否则将超量！！";
            if (a != "" && b != "") {
                msg = "您使用的肥料全部超出既定元素使用量，请检查或重新选择";
            } else if (WaringN != null && WaringP != null) {
                msg = "请不要选择" + WaringN + "、" + WaringP + "复合肥，否则将导致超量";
            } else if (WaringK != null && WaringP != null) {
                msg = "请不要选择" + WaringK + "、" + WaringP + "复合肥，否则将导致超量";
            } else {
                msg = "请不要选择" + WaringN + "、" + WaringK + "复合肥，否则将导致超量";
            }
            map.put("warning", msg);
        }
        map.put("rest", String.valueOf(Fn));
        return map;
    }
}
