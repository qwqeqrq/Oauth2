package com.second.service.Math;

import com.second.service.BaseDao.*;
import com.second.service.Service.*;
import com.second.service.Service.CropPlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DripIrrigationFertilizationPlan {
    private static Logger log = LoggerFactory.getLogger(DripIrrigationFertilizationPlan.class);

    @Autowired
    CropPlantService cropPlantService;
    @Autowired
    SfFlhllyMService sfFlhllyMService;
    @Autowired
    SfZddgsfzdscMService sfZddgsfzdscMService;
    @Autowired
    SfZddgsfscMService sfZddgsfscMService;
    @Autowired
    IrCrpStageService irCrpStageService;
    @Autowired
    SfDggccsMService sfDggccsMService;
    @Autowired
    SfSddgsfscMService sfSddgsfscMService;
    @Autowired
    IrCrpirrPlanService irCrpirrPlanService;
    @Autowired
    FindVar findVar;
    @Autowired
    SfDgggscMService sfDgggscMService;
    @Autowired
    SfSddgsfzdscMService sfSddgsfzdscMService;

    /**
     * 底肥计算
     *
     * @param fieldid
     * @param vatid
     * @param ana
     */
    public Map<String, Float> CalculationOfBottomFertilization(String fieldid, String vatid, String Fidk, String Fidn, String Fidp, int ana) throws ParseException {
        Map<String, Float> map = new HashMap<String, Float>();
        SimpleDateFormat sdfOfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date dc = new Date();
        DecimalFormat df = new DecimalFormat("0.000");
        String now2 = sdfOfDay.format(dc);
        Date now = sdfOfDay.parse(now2);
        // 根据地块查询种植面积
        float S = Float.parseFloat(cropPlantService.selectArea(fieldid));
        // 根据肥料种类选择施肥顺序,根据地块ID和种植植物ID查询N.P.K肥当季利用率和肥料中N.K.P含量
        List<SfFlhllyM> FertPara = sfFlhllyMService.SelectFertType(Fidk, Fidn, Fidp);
        float βn1 = FertPara.get(0).getFertAn();
        float βk1 = FertPara.get(0).getFertAk();
        float βp1 = FertPara.get(0).getFertAp();
        float Kp1 = FertPara.get(0).getUseeffiAp();
        float Kn1 = FertPara.get(0).getUseeffiAn();
        float Kk1 = FertPara.get(0).getUseeffiAk();
        // 根据地块ID和种植植物ID查询底肥K.N.P元素含量
        String CropElement = sfZddgsfzdscMService.selectFertElement(fieldid, vatid);
        float Fakdb = Float.parseFloat(CropElement.split(",")[0]);
        float Fapdb = Float.parseFloat(CropElement.split(",")[1]);
        float Fandb = Float.parseFloat(CropElement.split(",")[2]);
        // 根据底肥N.P.K元素含量和肥料中N.P.K元素含量以及N.P.K当季利用率，计算K.N.P元素对肥的需求量
        float F1kdb = 0.0f;
        float F1ndb = 0.0f;
        float F1pdb = 0.0f;
        if (βn1 == 0) {
            F1ndb = 1f / 0;
        } else {
            // N素对肥1需求量
            F1ndb = Fandb / (βn1 * Kn1);
        }
        if (βp1 == 0) {
            F1pdb = 1f / 0;
        } else {
            // P素对肥1需求量
            F1pdb = Fapdb / (βp1 * Kp1);
        }
        if (βk1 == 0) {
            F1kdb = 1f / 0;
        } else {
            // K素对肥1需求量
            F1kdb = Fakdb / (βk1 * Kk1);
        }
        // 取出K.N.P元素对肥的需求量中最低的值
        float f1db = Math.min(F1ndb, F1pdb);
        float F1db = Math.min(f1db, F1kdb);
        // 根据底肥N.P.K元素含量，肥料中N.P.K元素含量以及N.P.K当季利用率，计算一次选肥后N.P.K元素缺失量
        float Fsakdb = Float.parseFloat(df.format(Fakdb
                - Float.parseFloat(df.format(F1db * Kk1 * βk1))));
        float Fsandb = Float.parseFloat(df.format(Fandb
                - Float.parseFloat(df.format(F1db * Kn1 * βn1))));
        float Fsapdb = Float.parseFloat(df.format(Fapdb
                - Float.parseFloat(df.format(F1db * Kp1 * βp1))));
        float Fnsdb = 0;
        float Fksdb = 0;
        float Fpsdb = 0;
        if (Fsakdb == 0) {
            Fksdb = F1db;
        }
        if (Fsapdb == 0) {
            Fpsdb = F1db;
        }
        if (Fsandb == 0) {
            Fnsdb = F1db;
        }
        // 第二次选肥
        float βn2 = FertPara.get(1).getFertAn();
        float βk2 = FertPara.get(1).getFertAk();
        float βp2 = FertPara.get(1).getFertAp();
        float Kp2 = FertPara.get(1).getUseeffiAp();
        float Kn2 = FertPara.get(1).getUseeffiAn();
        float Kk2 = FertPara.get(1).getUseeffiAk();
        // 根据第一次选肥后N.P.K元素缺失量和肥料中N.P.K元素含量以及N.P.K当季利用率，计算N.P.K元素对肥2的需求量
        float F2kdb = 0.0f;
        float F2ndb = 0.0f;
        float F2pdb = 0.0f;
        if (βn2 != 0 && Fsandb != 0) {
            // N素对肥1需求量
            F2ndb = Fsandb / (βn2 * Kn2);
        } else {
            F2ndb = 1f / 0;
        }
        if (βp2 != 0 && Fsapdb != 0) {
            // N素对肥1需求量
            F2pdb = Fsapdb / (βp2 * Kp2);
        } else {
            F2pdb = 1f / 0;
        }
        if (βk2 != 0 && Fsakdb != 0) {
            // N素对肥1需求量
            F2kdb = Fsakdb / (βk2 * Kk2);
        } else {
            F2kdb = 1f / 0;
        }
        // 取出K.N.P元素对肥2的需求量中的最小值
        float f2db = Math.min(F2ndb, F2pdb);
        float F2db = Math.min(F2kdb, f2db);
        float Fssakdb = 0;
        float Fssandb = 0;
        float Fssapdb = 0;
        if (String.valueOf(F2db) == "Infinity") {
            F2db = 0;
        } else {
            // 根据一次选肥后N.P.K元素缺失量，K.N.P元素对肥2的需求量中的最小值，肥料中N.P.K元素含量以及N.P.K当季利用率，计算出二次选肥后K.N.P元素缺失量
            if (Fsandb == 0) {
                Fssandb = 0;
            } else {
                Fssandb = Float.parseFloat(df.format(Fsandb
                        - Float.parseFloat(df.format(F2db * Kn2 * βn2))));
            }
            if (Fsakdb == 0) {
                Fssakdb = 0;
            } else {
                Fssakdb = Float.parseFloat(df.format(Fsakdb
                        - Float.parseFloat(df.format(F2db * Kk2 * βk2))));
            }
            if (Fsapdb == 0) {
                Fssapdb = 0;
            } else {
                Fssapdb = Float.parseFloat(df.format(Fsapdb
                        - Float.parseFloat(df.format(F2db * Kp2 * βp2))));
            }

            if (Fssakdb == 0 && Fsakdb != 0) {
                Fksdb = F2db;
            }
            if (Fssapdb == 0 && Fsapdb != 0) {
                Fpsdb = F2db;
            }
            if (Fssandb == 0 && Fsandb != 0) {
                Fnsdb = F2db;
            }
        }
        // 第三次选肥
        float βn3 = FertPara.get(2).getFertAn();
        float βk3 = FertPara.get(2).getFertAk();
        float βp3 = FertPara.get(2).getFertAp();
        float Kp3 = FertPara.get(2).getUseeffiAp();
        float Kn3 = FertPara.get(2).getUseeffiAn();
        float Kk3 = FertPara.get(2).getUseeffiAk();
        // 根据筛选条件选出底肥单位面积P肥肥量
        if (βk3 != 0 && Fssakdb != 0) {
            Fksdb = Fssakdb / (βk3 * Kk3);
        } else {
            if (βk3 == 0) {
                log.info("肥料选择出错！");
            }
        }
        // 根据筛选条件选出底肥单位面积N肥肥量
        if (βp3 != 0 && Fssapdb != 0) {
            Fpsdb = Fssapdb / (βp3 * Kp3);
        } else {
            if (βp3 == 0) {
                log.info("肥料选择出错！");
            }
        }
        // 根据筛选条件选出底肥单位面积P肥肥量
        if (βn3 != 0 && Fssandb != 0) {
            Fnsdb = Fssandb / (βn3 * Kn3);
        } else {
            if (βn3 == 0) {
                log.info("肥料选择出错！");
            }
        }
        // 根据面积和底肥单位面积K.N.P肥量，计算出底肥K.N.P肥肥量
        float Fkdb = Fksdb * S;
        float Fndb = Fnsdb * S;
        float Fpdb = Fpsdb * S;
        // 将计算结果存放至Map
        map.put("Fndb", Fndb);
        map.put("Fpdb", Fpdb);
        map.put("Fkdb", Fkdb);
        map.put("Fnsdb", Fnsdb);
        map.put("Fpsdb", Fpsdb);
        map.put("Fksdb", Fksdb);
        if (ana == 2) {
            // 通过 地块id,作物品种id 查询有无数据
            int count = sfZddgsfscMService.coutOfRecord(fieldid, vatid, now2);
            if (count == 0) {
                // 存入数据库
                SfZddgsfscM record = new SfZddgsfscM();
                record.setFieldId(Long.parseLong(fieldid));
                record.setCrpVrtId(Long.parseLong(vatid));
                record.setFertsNBasic(Fnsdb);
                record.setCrpEtarealDate(now);
                record.setFertsPBasic(Fpsdb);
                record.setFertsKBasic(Fksdb);
                record.setFertNBasic(Fndb);
                record.setFertPBasic(Fpdb);
                record.setFertKBasic(Fkdb);
                sfZddgsfscMService.insert(record);
                log.info("底肥計算模型計算結果存储成功------------------------");
            } else if (count == 1) {
                int id = sfZddgsfscMService.SelectId(fieldid, vatid, now2);
                // 更新数据库
                SfZddgsfscM record = new SfZddgsfscM();
                record.setId(id);
                record.setFieldId(Long.parseLong(fieldid));
                record.setCrpVrtId(Long.parseLong(vatid));
                record.setFertsNBasic(Fnsdb);
                record.setCrpEtarealDate(now);
                record.setFertsPBasic(Fpsdb);
                record.setFertsKBasic(Fksdb);
                record.setFertNBasic(Fndb);
                record.setFertPBasic(Fpdb);
                record.setFertKBasic(Fkdb);
                sfZddgsfscMService.Update(record);
            }
        } else if (ana == 1) {
            int stage = irCrpStageService.stage(vatid);
            for (int i = 1; i <= stage; i++) {
                int ndi = sfDggccsMService.selectDggccsMService(fieldid, vatid, i);
                for (int j = 1; j <= ndi; j++) {
                    // 通过 地块id,作物品种id 查询有无数据
                    int count2 = sfSddgsfscMService.coutOfRecord(fieldid, vatid, now2, i, j);
                    if (count2 == 0) {
                        // 存入数据库
                        SfSddgsfscM record = new SfSddgsfscM();
                        record.setFieldId(Long.parseLong(fieldid));
                        record.setCrpVrtId(Long.parseLong(vatid));
                        record.setFertsNBasic(Fnsdb);
                        record.setCrpEtarealDate(now);
                        record.setFertsPBasic(Fpsdb);
                        record.setFertsKBasic(Fksdb);
                        record.setFertNBasic(Fndb);
                        record.setFertPBasic(Fpdb);
                        record.setFertKBasic(Fkdb);
                        record.setCrpStgId(i);
                        record.setFaddTime(j);
                        sfSddgsfscMService.insert(record);
                        log.info("底肥計算模型計算結果存储成功------------------------");
                    } else if (count2 == 1) {
                        Long id = sfSddgsfscMService.SelectId(fieldid, vatid, now2, i, j);
                        // 更新数据库
                        SfSddgsfscM record = new SfSddgsfscM();
                        record.setId(id);
                        record.setFieldId(Long.parseLong(fieldid));
                        record.setCrpVrtId(Long.parseLong(vatid));
                        record.setFertsNBasic(Fnsdb);
                        record.setCrpEtarealDate(now);
                        record.setFertsPBasic(Fpsdb);
                        record.setFertsKBasic(Fksdb);
                        record.setFertNBasic(Fndb);
                        record.setFertPBasic(Fpdb);
                        record.setFertKBasic(Fkdb);
                        record.setCrpStgId(i);
                        record.setFaddTime(j);
                        sfSddgsfscMService.Update(record);
                    }
                }
            }
        }
        return map;

    }

    /**
     * 设定追肥
     *
     * @param fieldid 地块id
     * @param vatid   作物品种id
     * @param tpsp    开机时间
     * @param fidP
     * @param fidN
     * @param fidK
     * @param log2
     * @throws ParseException
     */
    public Map<String, Float> SettingUpManuring(String fieldid, String vatid, String tpsp, String pid, String did, String cid, String fidK, String fidN, String fidP, String log2) throws ParseException {
        Map<String, Float> map = new HashMap<String, Float>();
        SimpleDateFormat timeOnSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeOnDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeOnYear = new SimpleDateFormat("yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        // 预测灌溉日期:预测灌溉模块求出,预测灌溉模块需要一期数据.
        Date Dfi = irCrpirrPlanService.crpirrDate(fieldid, vatid);
        if (Dfi == null) {
            Dfi = irCrpirrPlanService.selectcrpirrDateInFirsttime(fieldid, vatid);
        }
        String DFI = timeOnDate.format(Dfi);
        long dfi = Dfi.getTime();
        // 设置开机时刻，开机时刻=开始时间；
        String Tst = DFI + " " + tpsp;
        Date TPSP = timeOnSecond.parse(Tst);
        long Tpsp = TPSP.getTime();
        // 获取当前日期
        Date now = new Date();
        String Dc = timeOnDate.format(now);
        String Now = timeOnYear.format(now);
        long dc = timeOnDate.parse(Dc).getTime();
        // IR_W01_0001:播种日期
        Date Dp = timeOnDate.parse(cropPlantService.SelectStartTime(fieldid, vatid));
        long dp = Dp.getTime();
        long Dg = (dc - dp) / 86400000;
        // 当前地块所种植的农作物的生育阶段数
        int max_Stage = irCrpStageService.stage(vatid);
        Map<String, Object> map2 = findVar.IrriNumAndId(fieldid);
        Map<String, Object> map3 = findVar.IrriValve(fieldid);
        // 轮灌组内所有阀门名称
        Map<String, Object> map4 = (Map<String, Object>) map2.get("IrriName");
        // 轮灌组个数及名称
        Map<String, Object> nv = (Map<String, Object>) map3.get("ValueNumAndName");
        // SF_M05_0006:最大施肥间隔,灌溉系統日工作時長
        String data3 = sfDggccsMService.selectFertInterMaxAndDripDailyWork(fieldid, vatid);
        int Nv = 0;
        // 轮灌组数
        int Nr = (int) map2.get("IrriNum");
        // 最大施肥间隔
        int Dtol = Integer.parseInt(data3.split(",")[0]);
        // 轮灌组工作时长
        float Tdwd = Float.parseFloat(data3.split(",")[1]);
        // 最大面积对应的设计流量
        String v = map3.get("Flow").toString();
        int Qv = Integer.parseInt(v);
        // 单阀门控制最大面积
        float Svmax = (float) map3.get("Area");
        // IR_W04_0011:轮灌组灌溉水深,每一组轮灌组的灌溉水深都是一样的。
        float Iirri = irCrpirrPlanService.selectCrpirrPlandata(fieldid, vatid, Dfi);
        long daf = 0;
        // --- 储存追肥日期模型Start---
        for (int i = 1; i <= max_Stage; i++) {
            int ndi = sfDggccsMService.selectDggccsMService(fieldid, vatid, i);
            List<IrCrpStage> Length = irCrpStageService.selectIrCrpStageName(vatid, fieldid, pid, cid, did);
            int length = Length.get(i - 1).getCrpStgD();
            int a = length / ndi;
            long a2 = a * 864;
            long time = a2 * 100000;
            for (int j = 1; j <= ndi; j++) {
                if (i == 1 && j == 1) {
                    daf = dp + time;
                } else {
                    daf += time;
                }
                Date DAF = new Date(daf);
                LocalDate localDate2 = DAF.toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                Date Daf = java.sql.Date.valueOf(localDate2);
                // 存储数据,如果第一次进行施肥决策时存储，如果不是第一次决策，获取前一次的决策施肥日期
                long idForUpdate = sfSddgsfscMService.SelectId(fieldid, vatid, Dc, i, j);
                if (sfSddgsfscMService.RecordCountForDripPlan(fieldid, vatid, i, j) == 0) {
                    long id = sfSddgsfscMService.SelectIdForUpdate(fieldid, vatid, Dc, i, j);
                    SfSddgsfscM recordOfFertDate = new SfSddgsfscM();
                    recordOfFertDate.setId(id);
                    recordOfFertDate.setFieldId(Long.parseLong(fieldid));
                    recordOfFertDate.setCrpVrtId(Long.parseLong(vatid));
                    recordOfFertDate.setFaddDate(Daf);
                    recordOfFertDate.setCrpStgId(i);
                    recordOfFertDate.setFaddTime(j);
                    recordOfFertDate.setCrpEtarealDate(timeOnDate.parse(Dc));
                    sfSddgsfscMService.Update(recordOfFertDate);
                } else {
                    long id = sfSddgsfscMService.SelectIdForUpdate(fieldid, vatid, Dc, i, j);
                    SfSddgsfscM recordOfFertDate = new SfSddgsfscM();
                    recordOfFertDate.setId(id);
                    recordOfFertDate.setFieldId(Long.parseLong(fieldid));
                    recordOfFertDate.setCrpVrtId(Long.parseLong(vatid));
                    recordOfFertDate.setFaddDate(sfSddgsfscMService.selectFaddTimeForUpdate(fieldid, vatid, idForUpdate));
                    recordOfFertDate.setCrpStgId(i);
                    recordOfFertDate.setFaddTime(j);
                    recordOfFertDate.setCrpEtarealDate(timeOnDate.parse(Dc));
                    sfSddgsfscMService.Update(recordOfFertDate);
                }
            }
        }
        // 轮灌组模型开始
        Date daF = null;
        if (sfSddgsfscMService.coutOfRecord(fieldid, vatid, Dc, 1, 1) == 0) {
            daF = sfSddgsfscMService.selectMaxFertDate(fieldid, vatid, Dfi, Dc);
        } else {
            daF = sfSddgsfscMService.selectMinFertDate(fieldid, vatid, Dfi, Dc);
        }
        long Daf = daF.getTime();
        // 判断有无灌水或施肥要求
        if (dfi - Daf > Dtol * 86400000 && log2 != "3") {
            // 滴灌灌溉强度
            double Psi = Qv * 1000 / (Svmax * 666.7);
            // 轮灌组i灌溉时长
            long Tirri = (long) ((Iirri / Psi) * 3600000);
            long Tl = 0;
            String Tif = null;
            long tif = 0;
            for (int b = 1; b <= Nr; b++) { // -------------------轮灌组数Nr
                Map<Object, Object> nv2 = (Map<Object, Object>) nv.get(b
                        + "ValueName");
                Object ValveName = nv2.get(b + "Valuename");
                Object IrriName = map4.get(b);
                // 系统灌溉持续时间
                Tl = Tl + Tirri;
                if (b == 1) {
                    if (Tl < Tdwd * 3600000) {
                        // Tst是灌溉组开始时间；Tif是灌溉组结束时间
                        tif = Tpsp + Tirri;
                        Tif = timeOnSecond.format(new Date(tif));
                    } else {
                        Tl = 0;
                        tif = Tpsp + Tirri;
                        Tif = timeOnSecond.format(new Date(tif));
                    }
                } else {
                    // 滴灌灌溉强度
                    if (Tl < Tdwd * 3600000) {
                        // Tst是灌溉组开始时间；Tif是灌溉组结束时间
                        Tst = Tif;
                        tif += Tirri;
                        Tif = timeOnSecond.format(new Date(tif));
                    } else {
                        Tl = 0;
                        Tst = timeOnSecond.format(new Date(
                                (long) (tif + ((24 - Tdwd) * 7200000))));
                        tif = timeOnSecond.parse(Tst).getTime() + Tirri;
                        Tif = timeOnSecond.format(new Date(tif));
                    }

                }

                // -----------输出轮灌组i的开始时间Tst,i和灌溉开始时间Tst;输出灌溉组N结束时间Tif,i
                int count2 = sfDgggscMService.coutOfRecord(fieldid, vatid, Dc, IrriName);
                if (count2 == 0) {
                    // 将地块编号,作物品种编号,
                    // 输出轮灌组i的开始时间Tst,i和灌溉开始时间Tst;输出灌溉组N结束时间Tif,i 存入数据库
                    SfDgggscM record = new SfDgggscM();
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setCrpEtarealDate(timeOnDate.parse(Dc));
                    record.setIrriGroupStart(timeOnSecond.parse(Tst));
                    record.setIrriGroupStop(timeOnSecond.parse(Tif));
                    record.setIrriGroupNum(IrriName.toString());
                    record.setIrriValveName(ValveName.toString());
                    record.setCropGds(Integer.parseInt(String.valueOf(Dg)));
                    sfDgggscMService.insert(record);
                } else if (count2 == 1) {
                    // 通过地块编号,作物品种编号,预测日期 查询 id
                    long id = sfDgggscMService.SelectId(fieldid, vatid, Dc, IrriName);
                    // 将地块编号,作物品种编号,预测日期,当前播种天数,当前所处生育阶段序号,综合评估依据指标
                    // 存入数据库
                    SfDgggscM record = new SfDgggscM();
                    record.setId(id);
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setCrpEtarealDate(timeOnDate.parse(Dc));
                    record.setIrriGroupStart(timeOnSecond.parse(Tst));
                    record.setIrriGroupStop(timeOnSecond.parse(Tif));
                    record.setIrriGroupNum(IrriName.toString());
                    record.setIrriValveName(ValveName.toString());
                    record.setCropGds(Integer.parseInt(String.valueOf(Dg)));
                    sfDgggscMService.Update(record);
                } else {
                    log.error("存入或更新数据到数据库失败,数据库 数据条数 多于1条,请检查----------------------");
                }
            }
        }
        // 水肥耦合模型
        else {
            if (log2 != "3") {
                if (Math.abs(dfi - Daf) < Dtol * 86400000) {
                    // 如果执行水肥耦合，将施肥日期变成预测灌溉日期（预测灌溉日期是一期数据，不可变）
                    long id = sfSddgsfscMService.selectIdWithFadd_date(fieldid, vatid, daF, Dc);
                    SfSddgsfscM record = new SfSddgsfscM();
                    record.setId(id);
                    record.setFaddDate(timeOnDate.parse(DFI));
                    sfSddgsfscMService.Update(record);
                    Daf = dfi;
                }
                // 滴灌灌溉强度
                double Psi = Qv * 1000 / (Svmax * 666.7);
                // 轮灌组i灌溉时长
                long Tirri = (long) ((Iirri / Psi) * 3600000);
                long Tl = 0;
                String Tif = null;
                long tif = 0;
                for (int b = 1; b <= Nr; b++) { // -------------------轮灌组数Nr
                    Map<Object, Object> nv2 = (Map<Object, Object>) nv.get(b
                            + "ValueName");
                    Object ValveName = nv2.get(b + "Valuename");
                    Object IrriName = map4.get(b);
                    // 系统灌溉持续时间
                    Tl = Tl + Tirri;
                    if (b == 1) {
                        if (Tl < Tdwd * 3600000) {
                            // Tst是灌溉组开始时间；Tif是灌溉组结束时间
                            tif = Tpsp + Tirri;
                            Tif = timeOnSecond.format(new Date(tif));
                        } else {
                            Tl = 0;
                            tif = Tpsp + Tirri;
                            Tif = timeOnSecond.format(new Date(tif));
                        }
                    } else {
                        // 滴灌灌溉强度
                        if (Tl < Tdwd * 3600000) {
                            // Tst是灌溉组开始时间；Tif是灌溉组结束时间
                            Tst = Tif;
                            tif += Tirri;
                            Tif = timeOnSecond.format(new Date(tif));
                        } else {
                            Tl = 0;
                            Tst = timeOnSecond.format(new Date(
                                    (long) (tif + ((24 - Tdwd) * 7200000))));
                            tif = timeOnSecond.parse(Tst).getTime() + Tirri;
                            Tif = timeOnSecond.format(new Date(tif));
                        }

                    }
                    // -----------输出轮灌组i的开始时间Tst,i和灌溉开始时间Tst;输出灌溉组N结束时间Tif,i
                    int count2 = sfDgggscMService.coutOfRecord(fieldid, vatid, Dc, IrriName);
                    if (count2 == 0) {
                        // 将地块编号,作物品种编号,
                        // 输出轮灌组i的开始时间Tst,i和灌溉开始时间Tst;输出灌溉组N结束时间Tif,i 存入数据库
                        SfDgggscM record = new SfDgggscM();
                        record.setFieldId(Long.parseLong(fieldid));
                        record.setCrpVrtId(Long.parseLong(vatid));
                        record.setCrpEtarealDate(timeOnDate.parse(Dc));
                        record.setIrriGroupStart(timeOnSecond.parse(Tst));
                        record.setIrriGroupStop(timeOnSecond.parse(Tif));
                        record.setIrriGroupNum(IrriName.toString());
                        record.setIrriValveName(ValveName.toString());
                        record.setCropGds(Integer.parseInt(String.valueOf(Dg)));
                        sfDgggscMService.insert(record);
                    } else if (count2 == 1) {
                        // 通过地块编号,作物品种编号,预测日期 查询 id
                        long id = sfDgggscMService.SelectId(fieldid, vatid, Dc, IrriName);
                        // 将地块编号,作物品种编号,预测日期,当前播种天数,当前所处生育阶段序号,综合评估依据指标
                        // 存入数据库
                        SfDgggscM record = new SfDgggscM();
                        record.setId(id);
                        record.setFieldId(Long.parseLong(fieldid));
                        record.setCrpVrtId(Long.parseLong(vatid));
                        record.setCrpEtarealDate(timeOnDate.parse(Dc));
                        record.setIrriGroupStart(timeOnSecond.parse(Tst));
                        record.setIrriGroupStop(timeOnSecond.parse(Tif));
                        record.setIrriGroupNum(IrriName.toString());
                        record.setIrriValveName(ValveName.toString());
                        record.setCropGds(Integer.parseInt(String.valueOf(Dg)));
                        sfDgggscMService.Update(record);
                    } else {
                        log.error("存入或更新数据到数据库失败,数据库 数据条数 多于1条,请检查----------------------");
                    }
                }
            }
            // 施肥部分
            float fnak = 0;
            float fnap = 0;
            float fnan = 0;
            float Fpgk = 0;
            float Fngk = 0;
            float Fkgk = 0;

            for (int i = 1; i <= max_Stage; i++) {
                // 第i次追肥N素量,第i次追肥P素量,第i次追肥K素量
                String data2 = sfSddgsfzdscMService.FertPlan(fieldid, vatid, i, Now);
                // 第i次追肥N素量
                float Fansb = Float.parseFloat(data2.split(",")[0]);
                // 第i次追肥P素量
                float Fapsb = Float.parseFloat(data2.split(",")[1]);
                // 第i次追肥K素量
                float Faksb = Float.parseFloat(data2.split(",")[2]);
                // 查询当前生育阶段追肥次数
                int ndi = sfDggccsMService.selectDggccsMService(fieldid, vatid, i);
                for (int j = 1; j <= ndi; j++) {
                    // 根据地块ID和种植植物ID查询N肥当季利用率和肥料中N.K.P含量
                    List<SfFlhllyM> FertPara = sfFlhllyMService.SelectFertType(fidK, fidN, fidP);
                    float βn1 = FertPara.get(0).getFertAn();
                    float βk1 = FertPara.get(0).getFertAk();
                    float βp1 = FertPara.get(0).getFertAp();
                    float Kp1 = FertPara.get(0).getUseeffiAp();
                    float Kn1 = FertPara.get(0).getUseeffiAn();
                    float Kk1 = FertPara.get(0).getUseeffiAk();
                    // 根据底肥N.P.K元素含量和肥料中N.P.K元素含量以及N.P.K当季利用率，计算K.N.P元素对肥的需求量
                    float F1ksb = 0.0f;
                    float F1nsb = 0.0f;
                    float F1psb = 0.0f;
                    if (βn1 == 0) {
                        F1nsb = 1f / 0;
                    } else {
                        // N素对肥1需求量
                        F1nsb = Fansb / (βn1 * Kn1);
                    }
                    if (βp1 == 0) {
                        F1psb = 1f / 0;
                    } else {
                        // P素对肥1需求量
                        F1psb = Fapsb / (βp1 * Kp1);
                    }
                    if (βk1 == 0) {
                        F1ksb = 1f / 0;
                    } else {
                        // K素对肥1需求量
                        F1ksb = Faksb / (βk1 * Kk1);
                    }
                    // 取出K.N.P元素对肥的需求量中最低的值
                    float f1sb = Math.min(F1nsb, F1psb);
                    float F1sb = Math.min(f1sb, F1ksb);
                    // 根据底肥N.P.K元素含量，肥料中N.P.K元素含量以及N.P.K当季利用率，计算一次选肥后N.P.K元素缺失量
                    float Fsaksb = Faksb
                            - Float.parseFloat(decimalFormat.format(F1sb * Kk1 * βk1));
                    float Fsansb = Fansb
                            - Float.parseFloat(decimalFormat.format(F1sb * Kn1 * βn1));
                    float Fsapsb = Fapsb
                            - Float.parseFloat(decimalFormat.format(F1sb * Kp1 * βp1));
                    float Fnssb = 0;
                    float Fkssb = 0;
                    float Fpssb = 0;
                    if (Fsaksb == 0) {
                        Fkssb = F1sb;
                    }
                    if (Fsapsb == 0) {
                        Fpssb = F1sb;
                    }
                    if (Fsansb == 0) {
                        Fnssb = F1sb;
                    }
                    // 根据地块ID和种植植物ID查询P肥当季利用率和肥料中N.K.P含量
                    float βn2 = FertPara.get(1).getFertAn();
                    float βk2 = FertPara.get(1).getFertAk();
                    float βp2 = FertPara.get(1).getFertAp();
                    float Kp2 = FertPara.get(1).getUseeffiAp();
                    float Kn2 = FertPara.get(1).getUseeffiAn();
                    float Kk2 = FertPara.get(1).getUseeffiAk();
                    // 根据第一次选肥后N.P.K元素缺失量和肥料中N.P.K元素含量以及N.P.K当季利用率，计算N.P.K元素对肥2的需求量
                    float F2ksb = 0.0f;
                    float F2nsb = 0.0f;
                    float F2psb = 0.0f;
                    if (βn2 != 0 && Fsansb != 0) {
                        // N素对肥1需求量
                        F2nsb = Fsansb / (βn2 * Kn2);
                    } else {
                        F2nsb = 1f / 0;
                    }
                    if (βp2 != 0 && Fsapsb != 0) {

                        // N素对肥1需求量
                        F2psb = Fsapsb / (βp2 * Kp2);
                    } else {
                        F2psb = 1f / 0;
                    }
                    if (βk2 != 0 && Fsaksb != 0) {

                        // N素对肥1需求量
                        F2ksb = Fsaksb / (βk2 * Kk2);
                    } else {
                        F2ksb = 1f / 0;
                    }
                    // 取出K.N.P元素对肥2的需求量中的最小值
                    float f2sb = Math.min(F2nsb, F2psb);
                    float F2sb = Math.min(F2ksb, f2sb);
                    if (F2ksb == 1f / 0 && f2sb == 1f / 0) {
                        F2sb = 0;
                    }
                    // 根据一次选肥后N.P.K元素缺失量，K.N.P元素对肥2的需求量中的最小值，肥料中N.P.K元素含量以及N.P.K当季利用率，计算出二次选肥后K.N.P元素缺失量
                    float Fssaksb = 0;
                    float Fssansb = 0;
                    float Fssapsb = 0;
                    if (Fsansb == 0) {
                        Fssansb = 0;
                    } else {
                        Fssansb = Float
                                .parseFloat(decimalFormat.format(Fsansb
                                        - Float.parseFloat(decimalFormat.format(F2sb * Kn2
                                        * βn2))));
                    }
                    if (Fsaksb == 0) {
                        Fssaksb = 0;
                    } else {
                        Fssaksb = Float
                                .parseFloat(decimalFormat.format(Fsaksb
                                        - Float.parseFloat(decimalFormat.format(F2sb * Kk2
                                        * βk2))));
                    }
                    if (Fsapsb == 0) {
                        Fssapsb = 0;
                    } else {
                        Fssapsb = Float
                                .parseFloat(decimalFormat.format(Fsapsb
                                        - Float.parseFloat(decimalFormat.format(F2sb * Kp2
                                        * βp2))));
                    }

                    if (Fssaksb == 0 && Fsaksb != 0) {
                        Fkssb = F2sb;
                    }
                    if (Fssapsb == 0 && Fsapsb != 0) {
                        Fpssb = F2sb;
                    }
                    if (Fssansb == 0 && Fsansb != 0) {
                        Fnssb = F2sb;
                    }
                    // 根据地块ID和种植植物ID查询K肥当季利用率和肥料中N.K.P含量
                    float βn3 = FertPara.get(2).getFertAn();
                    float βk3 = FertPara.get(2).getFertAk();
                    float βp3 = FertPara.get(2).getFertAp();
                    float Kp3 = FertPara.get(2).getUseeffiAp();
                    float Kn3 = FertPara.get(2).getUseeffiAn();
                    float Kk3 = FertPara.get(2).getUseeffiAk();
                    // 根据筛选条件选出底肥单位面积K肥肥量
                    if (βk3 != 0 && Fssaksb != 0) {
                        Fkssb = Fssaksb / (βk3 * Kk3);
                    } else {
                        if (βk3 == 0) {
                            log.info("肥料选择出错！");

                        }
                    }
                    // 根据筛选条件选出底肥单位面积N肥肥量

                    if (βp3 != 0 && Fssapsb != 0) {
                        Fpssb = Fssapsb / (βp3 * Kp3);
                    } else {
                        if (βp3 == 0) {
                            log.info("肥料选择出错！");

                        }
                    }
                    // 根据筛选条件选出底肥单位面积P肥肥量

                    if (βn3 != 0 && Fssansb != 0) {
                        Fnssb = Fssansb / (βn3 * Kn3);
                    } else {
                        if (βn3 == 0) {
                            log.info("肥料选择出错！");

                        }
                    }

                    fnak = Fkssb;
                    fnap = Fpssb;
                    fnan = Fnssb;
                    float Fngi = 0;
                    float Fkgi = 0;
                    float Fpgi = 0;
                    // 轮灌组i第k次追K.N.P肥量

                    for (int o = 1; o <= Nr; o++) {
                        Map<Object, Object> nv2 = (Map<Object, Object>) nv
                                .get(o + "ValueNum");
                        Nv = (int) nv2.get(o + "Valuenum");
                        Fngi = fnan * Svmax * Nv;
                        Fkgi = fnak * Svmax * Nv;
                        Fpgi = fnap * Svmax * Nv;
                        Fpgk += Fpgi;
                        Fngk += Fngi;
                        Fkgk += Fkgi;
                    }
                    int count1 = sfSddgsfscMService.coutOfRecord(fieldid, vatid, Dc, i, j);
                    if (count1 == 0) {
                        SfSddgsfscM record = new SfSddgsfscM();
                        record.setFieldId(Long.parseLong(fieldid));
                        record.setCrpVrtId(Long.parseLong(vatid));
                        record.setFertdDnK(fnan);
                        record.setFertdDpK(fnap);
                        record.setFertdDkK(fnak);
                        record.setCrpStgId(i);
                        record.setFaddTime(j);
                        record.setFertdDpGroupik(Fpgi);
                        record.setCrpEtarealDate(timeOnDate.parse(Dc));
                        record.setFertdDkGroupik(Fkgi);
                        record.setFertdDnGroupik(Fngi);
                        record.setFertDpK(Fpgk);
                        record.setFertDnK(Fngk);
                        record.setFertDkK(Fkgk);
                        sfSddgsfscMService.insert(record);
                    } else if (count1 == 1) {
                        // 通过地块编号,作物品种编号,预测日期 查询 id
                        long id = sfSddgsfscMService.SelectId(fieldid, vatid, Dc, i, j);
                        SfSddgsfscM record = new SfSddgsfscM();
                        record.setId(id);
                        record.setFieldId(Long.parseLong(fieldid));
                        record.setCrpVrtId(Long.parseLong(vatid));
                        record.setFertdDnK(fnan);
                        record.setFertdDpK(fnap);
                        record.setFertdDkK(fnak);
                        record.setCrpStgId(i);
                        record.setFaddTime(j);
                        record.setFertdDpGroupik(Fpgi);
                        record.setCrpEtarealDate(timeOnDate.parse(Dc));
                        record.setFertdDkGroupik(Fkgi);
                        record.setFertdDnGroupik(Fngi);
                        record.setFertDpK(Fpgk);
                        record.setFertDnK(Fngk);
                        record.setFertDkK(Fkgk);
                        sfSddgsfscMService.Update(record);
                    } else {
                        log.error("存入或更新数据到数据库失败,数据库 数据条数 多于1条,请检查----------------------");
                    }
                }
            }
        }
        return map;
    }

    /**
     * 自动追肥
     *
     * @param fieldid
     * @param vatid
     * @param fidN
     * @param tpsp
     * @param did
     * @param cid
     * @param pid
     * @param log2
     * @return
     * @throws ParseException
     */
    public Map<String, Object> AutomaticDressing(String fidK, String fidP, String fidN, String tpsp, String fieldid, String vatid, String pid, String cid, String did, String log2) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        SimpleDateFormat timeOfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeOfDate = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> nv = (Map<String, Object>) findVar.IrriValve(fieldid).get("ValueNumAndName");
        Date Dfi = irCrpirrPlanService.crpirrDate(fieldid, vatid);
        if (Dfi == null) {
            Dfi = irCrpirrPlanService.selectcrpirrDateInFirsttime(fieldid, vatid);
        }
        String DFI = timeOfDate.format(Dfi);
        long dfi = Dfi.getTime();
        // 设置开机时刻，开机时刻=开始时间；
        String Tst = DFI + " " + tpsp;
        Date TPSP = timeOfSecond.parse(Tst);
        long Tpsp = TPSP.getTime();
        // IR_W01_0001:播种日期
        String DP = cropPlantService.SelectStartTime(fieldid, vatid);
        Date Dp = timeOfDate.parse(DP);
        long dp = Dp.getTime();
        // 获取当前日期
        Date now = new Date();
        String Dc = timeOfDate.format(now);
        long dc = timeOfDate.parse(Dc).getTime();
        // 根据当前日期获取生长天数
        int gds = Integer.parseInt(String.valueOf((dc - dp) / 86400000));
        // SF_M05_0004:根据地块ID和种植植物ID查询追肥后肥效时长
        int dft = sfFlhllyMService.selectCrpStgD();
        // IR_W04_0011:轮灌组灌溉水深,每一组轮灌组的灌溉水深都是一样的。
        float Iirri = irCrpirrPlanService.selectCrpirrPlandata(fieldid, vatid, Dfi);
        // SF_M05_0006:最大施肥间隔,灌溉系統日工作時長
        String data3 = sfDggccsMService.selectFertInterMaxAndDripDailyWorkIrriFertgroupDeepth(fieldid, vatid);
        // 最大施肥间隔
        int Dtol = Integer.parseInt(data3.split(",")[0]);
        // 轮灌组工作时长
        float Tdwd = Float.parseFloat(data3.split(",")[1]);
        float Iif = Float.parseFloat(data3.split(",")[2]);
        Map<String, Object> map2 = findVar.IrriNumAndId(fieldid);
        Map<String, Object> map3 = findVar.IrriValve(fieldid);
        @SuppressWarnings("unchecked")
        Map<String, Object> map4 = (Map<String, Object>) map2.get("IrriName");
        int Nv = 0;
        // 轮灌组数
        int Nr = (int) map2.get("IrriNum");
        // 最大面积对应的设计流量
        String v = map3.get("Flow").toString();
        int Qv = Integer.parseInt(v);
        // 单阀门控制最大面积
        float Svmax = (float) map3.get("Area");
        /*
         * -------计算开始和结束时间模型------
         */
        float Dink = 0;
        long Tl = 0;
        String Tif = null;
        long tif = 0;
        int m = 1;// 记录第几次施肥
        int stgid = 0;
        long Tirri = 0;
        int Dend = 0;
        List<IrCrpStage> Length = irCrpStageService.selectIrCrpStageName(vatid, fieldid, pid, cid, did);
        Dend = Length.get(Length.size() - 1).getCrpStgDend();
        // 1539014400000 dp 248*86400000 dend = 21427200000
        long denD = Dend * 864;
        long DenD = denD * 100000;
        long stageMax = dp + DenD;
        // 1543766400000 1538966763520
        for (long dek = dfi; dek <= stageMax; dek = dfi + dft * 86400000, m = m + 1) {
            if (log2 != "3") {
                // 判断是否需要继续等待
                if (dc < dfi && dc - dek < Dtol * 86400000 && log2 != "3") {
                    log.error("请再等待！");
                    break;
                }
                // 判断第k次追肥有效天数Dink

                if (dek <= dfi) {
                    if (dc - dek >= Dtol * 86400000) {
                        Dink = Dtol;
                        double Psi = Qv * 1000 / (Svmax * 666.7);
                        Tirri = (long) ((Iif / Psi) * 3600000);
                    } else {
                        Dink = dfi - dek + dft;
                        double Psi = Qv * 1000 / (Svmax * 666.7);
                        Tirri = (long) ((Iirri / Psi) * 3600000);
                    }
                } else {
                    double Psi = Qv * 1000 / (Svmax * 666.7);
                    Tirri = (long) ((Iirri / Psi) * 3600000);
                }

                for (int b = 1; b <= Nr; b++) { // -------------------轮灌组数Nr
                    // 轮灌组对应的阀门名称
                    @SuppressWarnings("unchecked")
                    Map<Object, Object> nv2 = (Map<Object, Object>) nv.get(b
                            + "ValueName");
                    Object ValveName = nv2.get(b + "Valuename");
                    // 获取轮灌组名称
                    Object IrriName = map4.get(b);
                    // 系统灌溉持续时间
                    Tl = Tl + Tirri;
                    if (b == 1 && dek == dfi) {
                        if (Tl < Tdwd * 3600000) {
                            // Tst是灌溉组开始时间；Tif是灌溉组结束时间

                            tif = Tpsp + Tirri;
                            Tif = timeOfSecond.format(new Date(tif));
                        } else {
                            Tl = 0;

                            tif = Tpsp + Tirri;
                            Tif = timeOfSecond.format(new Date(tif));
                        }
                    } else {
                        // 滴灌灌溉强度
                        if (Tl < Tdwd * 3600000) {
                            // Tst是灌溉组开始时间；Tif是灌溉组结束时间
                            Tst = Tif;
                            tif += Tirri;
                            Tif = timeOfSecond.format(new Date(tif));
                        } else {
                            Tl = 0;
                            Tst = timeOfSecond.format(new Date(
                                    (long) (tif + ((24 - Tdwd) * 7200000))));
                            tif = timeOfSecond.parse(Tst).getTime() + Tirri;
                            Tif = timeOfSecond.format(new Date(tif));
                        }

                    }
                    // 将地块编号,作物品种编号,
                    // -----------输出轮灌组i的开始时间Tst,i和灌溉开始时间Tst;输出灌溉组N结束时间Tif,i
                    int count2 = sfDgggscMService.countInAutomaticIrrigation(fieldid, vatid, IrriName, m, Dc);
                    if (count2 == 0) {
                        // 将地块编号,作物品种编号,
                        // 输出轮灌组i的开始时间Tst,i和灌溉开始时间Tst;输出灌溉组N结束时间Tif,i 存入数据库
                        SfDgggscM Record = new SfDgggscM();
                        Record.setFieldId(Long.parseLong(fieldid));
                        Record.setCrpVrtId(Long.parseLong(vatid));
                        Record.setFertTotalTimes(m);
                        Record.setCrpEtarealDate(timeOfDate.parse(Dc));
                        Record.setIrriGroupStart(timeOfSecond.parse(Tst));
                        Record.setIrriGroupStop(timeOfSecond.parse(Tif));
                        Record.setIrriValveName(ValveName.toString());
                        Record.setIrriGroupNum(IrriName.toString());
                        Record.setCropGds(gds);
                        sfDgggscMService.insert(Record);
                    } else if (count2 == 1) {
                        long id = sfDgggscMService.selectIdOnAutomaticIrrigation(fieldid, vatid, IrriName, m, Dc);
                        SfDgggscM Record = new SfDgggscM();
                        Record.setId(id);
                        Record.setFieldId(Long.parseLong(fieldid));
                        Record.setCrpVrtId(Long.parseLong(vatid));
                        Record.setFertTotalTimes(m);
                        Record.setCrpEtarealDate(timeOfDate.parse(Dc));
                        Record.setIrriGroupStart(timeOfSecond.parse(Tst));
                        Record.setIrriGroupStop(timeOfSecond.parse(Tif));
                        Record.setIrriValveName(ValveName.toString());
                        Record.setIrriGroupNum(IrriName.toString());
                        Record.setCropGds(gds);
                        sfDgggscMService.Update(Record);
                    } else {
                        log.error("存入或更新数据到数据库失败,数据库 数据条数 多于1条,请检查----------------------");
                    }
                }
            }
            /*
             * --------肥量计算模型------
             */
            float fnak = 0;
            float fnap = 0;
            float fnan = 0;
            float Fpgk = 0;
            float Fngk = 0;
            float Fkgk = 0;
            // 判断第k次追肥有效天数Dink
            if (dek <= dfi) {
                if (dc - dek >= Dtol) {
                    Dink = Dtol;
                } else {
                    Dink = dfi - dek + dft;
                }
            }
            for (int j = 1; j <= Dink; j++) {
                long dg = dek - dp + j * 86400000;
                long Dg = dg / 86400000;
                // 查询当前日期所处生育阶段
                stgid = irCrpStageService.selectCrpStgIdByNow(Dg, vatid);
                // 第i次追肥N素量,第i次追肥P素量,第i次追肥K素量
                String data2 = sfZddgsfscMService.selectFertPara(fieldid, vatid, stgid);
                // 第i次追肥N素量
                float Fansb = Float.parseFloat(data2.split(",")[0]);
                // 第i次追肥P素量
                float Fapsb = Float.parseFloat(data2.split(",")[1]);
                // 第i次追肥K素量
                float Faksb = Float.parseFloat(data2.split(",")[2]);
                // 根据地块ID和种植植物ID查询N肥当季利用率和肥料中N.K.P含量
                List<SfFlhllyM> FertPara = sfFlhllyMService.SelectFertType(fidK, fidN, fidP);
                float βn1 = FertPara.get(0).getFertAn();
                float βk1 = FertPara.get(0).getFertAk();
                float βp1 = FertPara.get(0).getFertAp();
                float Kp1 = FertPara.get(0).getUseeffiAp();
                float Kn1 = FertPara.get(0).getUseeffiAn();
                float Kk1 = FertPara.get(0).getUseeffiAk();
                // 根据底肥N.P.K元素含量和肥料中N.P.K元素含量以及N.P.K当季利用率，计算K.N.P元素对肥的需求量
                float F1ksb = 0.0f;
                float F1nsb = 0.0f;
                float F1psb = 0.0f;
                if (βn1 == 0) {
                    F1nsb = 1f / 0;
                } else {
                    // N素对肥1需求量
                    F1nsb = Fansb / (βn1 * Kn1);
                }
                if (βp1 == 0) {
                    F1psb = 1f / 0;
                } else {
                    // P素对肥1需求量
                    F1psb = Fapsb / (βp1 * Kp1);
                }
                if (βk1 == 0) {
                    F1ksb = 1f / 0;
                } else {
                    // K素对肥1需求量
                    F1ksb = Faksb / (βk1 * Kk1);
                }
                // 取出K.N.P元素对肥的需求量中最低的值
                float f1sb = Math.min(F1nsb, F1psb);
                float F1sb = Math.min(f1sb, F1ksb);
                // 根据底肥N.P.K元素含量，肥料中N.P.K元素含量以及N.P.K当季利用率，计算一次选肥后N.P.K元素缺失量
                float Fsaksb = Faksb
                        - Float.parseFloat(decimalFormat.format(F1sb * Kk1 * βk1));
                float Fsansb = Fansb
                        - Float.parseFloat(decimalFormat.format(F1sb * Kn1 * βn1));
                float Fsapsb = Fapsb
                        - Float.parseFloat(decimalFormat.format(F1sb * Kp1 * βp1));
                float Fnssb = 0;
                float Fkssb = 0;
                float Fpssb = 0;
                if (Fsaksb == 0) {
                    Fkssb = F1sb;
                }
                if (Fsapsb == 0) {
                    Fpssb = F1sb;
                }
                if (Fsansb == 0) {
                    Fnssb = F1sb;
                }
                // 根据地块ID和种植植物ID查询P肥当季利用率和肥料中N.K.P含量
                float βn2 = FertPara.get(1).getFertAn();
                float βk2 = FertPara.get(1).getFertAk();
                float βp2 = FertPara.get(1).getFertAp();
                float Kp2 = FertPara.get(1).getUseeffiAp();
                float Kn2 = FertPara.get(1).getUseeffiAn();
                float Kk2 = FertPara.get(1).getUseeffiAk();
                // 根据第一次选肥后N.P.K元素缺失量和肥料中N.P.K元素含量以及N.P.K当季利用率，计算N.P.K元素对肥2的需求量
                float F2ksb = 0.0f;
                float F2nsb = 0.0f;
                float F2psb = 0.0f;
                if (βn2 != 0 && Fsansb != 0) {
                    // N素对肥1需求量
                    F2nsb = Fsansb / (βn2 * Kn2);
                } else {
                    F2nsb = 1f / 0;
                }
                if (βp2 != 0 && Fsapsb != 0) {

                    // N素对肥1需求量
                    F2psb = Fsapsb / (βp2 * Kp2);
                } else {
                    F2psb = 1f / 0;
                }
                if (βk2 != 0 && Fsaksb != 0) {

                    // N素对肥1需求量
                    F2ksb = Fsaksb / (βk2 * Kk2);
                } else {
                    F2ksb = 1f / 0;
                }
                // 取出K.N.P元素对肥2的需求量中的最小值
                float f2sb = Math.min(F2nsb, F2psb);
                float F2sb = Math.min(F2ksb, f2sb);
                if (F2ksb == 1f / 0 && f2sb == 1f / 0) {
                    F2sb = 0;
                }
                // 根据一次选肥后N.P.K元素缺失量，K.N.P元素对肥2的需求量中的最小值，肥料中N.P.K元素含量以及N.P.K当季利用率，计算出二次选肥后K.N.P元素缺失量
                float Fssaksb = 0;
                float Fssansb = 0;
                float Fssapsb = 0;
                if (Fsansb == 0) {
                    Fssansb = 0;
                } else {
                    Fssansb = Float.parseFloat(decimalFormat.format(Fsansb
                            - Float.parseFloat(decimalFormat.format(F2sb * Kn2 * βn2))));
                }
                if (Fsaksb == 0) {
                    Fssaksb = 0;
                } else {
                    Fssaksb = Float.parseFloat(decimalFormat.format(Fsaksb
                            - Float.parseFloat(decimalFormat.format(F2sb * Kk2 * βk2))));
                }
                if (Fsapsb == 0) {
                    Fssapsb = 0;
                } else {
                    Fssapsb = Float.parseFloat(decimalFormat.format(Fsapsb
                            - Float.parseFloat(decimalFormat.format(F2sb * Kp2 * βp2))));
                }

                if (Fssaksb == 0 && Fsaksb != 0) {
                    Fkssb = F2sb;
                }
                if (Fssapsb == 0 && Fsapsb != 0) {
                    Fpssb = F2sb;
                }
                if (Fssansb == 0 && Fsansb != 0) {
                    Fnssb = F2sb;
                }
                // 根据地块ID和种植植物ID查询K肥当季利用率和肥料中N.K.P含量
                float βn3 = FertPara.get(2).getFertAn();
                float βk3 = FertPara.get(2).getFertAk();
                float βp3 = FertPara.get(2).getFertAp();
                float Kp3 = FertPara.get(2).getUseeffiAp();
                float Kn3 = FertPara.get(2).getUseeffiAn();
                float Kk3 = FertPara.get(2).getUseeffiAk();
                // 根据筛选条件选出底肥单位面积K肥肥量
                if (βk3 != 0 && Fssaksb != 0) {
                    Fkssb = Fssaksb / (βk3 * Kk3);
                } else {
                    if (βk3 == 0) {
                        log.info("肥料选择出错！");

                    }
                }
                // 根据筛选条件选出底肥单位面积N肥肥量

                if (βp3 != 0 && Fssapsb != 0) {
                    Fpssb = Fssapsb / (βp3 * Kp3);
                } else {
                    if (βp3 == 0) {
                        log.info("肥料选择出错！");

                    }
                }
                // 根据筛选条件选出底肥单位面积P肥肥量

                if (βn3 != 0 && Fssansb != 0) {
                    Fnssb = Fssansb / (βn3 * Kn3);
                } else {
                    if (βn3 == 0) {
                        log.info("肥料选择出错！");

                    }
                }

                fnak += Fkssb;
                fnap += Fpssb;
                fnan += Fnssb;
                float Fngi = 0;
                float Fkgi = 0;
                float Fpgi = 0;
                // 轮灌组i第k次追K.N.P肥量

                for (int o = 1; o <= Nr; o++) {
                    @SuppressWarnings("unchecked")
                    Map<Object, Object> nv2 = (Map<Object, Object>) nv.get(o
                            + "ValueNum");
                    Nv = (int) nv2.get(o + "Valuenum");
                    Fngi = fnan * Svmax * Nv;
                    Fkgi = fnak * Svmax * Nv;
                    Fpgi = fnap * Svmax * Nv;
                    Fpgk += Fpgi;
                    Fngk += Fngi;
                    Fkgk += Fkgi;
                }
                int count1 = sfZddgsfscMService.coutOfRecord(fieldid, vatid, Dc);
                if (count1 == 0) {
                    SfZddgsfscM record = new SfZddgsfscM();
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setFnaddsNK(fnan);
                    record.setFnaddsPK(fnap);
                    record.setFnaddsKK(fnak);
                    record.setFnaddPGroupik(Fpgi);
                    record.setFertdripStartTime(sfDgggscMService.selectMinIrriGroupStartForAutomaticIrrigation(fieldid, vatid, Dc));
                    record.setFertdripStopTime(sfDgggscMService.selectMaxIrriGroupStartForAutomaticIrrigation(fieldid, vatid, Dc));
                    record.setCrpEtarealDate(timeOfDate.parse(Dc));
                    record.setFnaddKGroupik(Fkgi);
                    record.setFnaddNGroupik(Fngi);
                    record.setFnaddPK(Fpgk);
                    record.setFnaddNK(Fngk);
                    record.setFnaddKK(Fkgk);
                    sfZddgsfscMService.insert(record);
                } else if (count1 == 1) {
                    // 通过地块编号,作物品种编号,预测日期 查询 id
                    int id = sfZddgsfscMService.SelectId(fieldid, vatid, Dc);
                    SfZddgsfscM record = new SfZddgsfscM();
                    record.setId(id);
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setFnaddsNK(fnan);
                    record.setFnaddsPK(fnap);
                    record.setFnaddsKK(fnak);
                    record.setFnaddPGroupik(Fpgi);
                    record.setFertdripStartTime(sfDgggscMService.selectMinIrriGroupStartForAutomaticIrrigation(fieldid, vatid, Dc));
                    record.setFertdripStopTime(sfDgggscMService.selectMaxIrriGroupStartForAutomaticIrrigation(fieldid, vatid, Dc));
                    record.setCrpEtarealDate(timeOfDate.parse(Dc));
                    record.setFnaddKGroupik(Fkgi);
                    record.setFnaddNGroupik(Fngi);
                    record.setFnaddPK(Fpgk);
                    record.setFnaddNK(Fngk);
                    record.setFnaddKK(Fkgk);
                    sfZddgsfscMService.Update(record);
                } else {
                    log.error("存入或更新数据到数据库失败,数据库 数据条数 多于1条,请检查----------------------");
                }
            }
            dfi = dek;
        }
        return map;
    }
}
