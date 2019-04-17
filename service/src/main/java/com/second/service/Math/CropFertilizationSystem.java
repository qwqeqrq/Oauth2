package com.second.service.Math;

import com.second.service.BaseDao.IrCrpStage;
import com.second.service.BaseDao.SfPgsfzdscM;
import com.second.service.BaseDao.SfSddgsfzdscM;
import com.second.service.BaseDao.SfZddgsfzdscM;
import com.second.service.Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CropFertilizationSystem {
    private static Logger log = LoggerFactory.getLogger(CropFertilizationSystem.class);

    @Autowired
    private IrCrpStageService irCrpStageService;
    @Autowired
    private SfPggccsMService sfPggccsMService;
    @Autowired
    private CropPlantService cropPlantService;
    @Autowired
    private SfXffxscMService sfXffxscMService;
    @Autowired
    private SfPgsfzdscMService sfPgsfzdscMService;
    @Autowired
    private SfDggccsMService sfDggccsMService;
    @Autowired
    private SfZddgsfzdscMService sfZddgsfzdscMService;
    @Autowired
    private SfSddgsfzdscMService sfSddgsfzdscMService;


    /**
     * ----喷灌----
     *
     * @param did 区ID
     * @param cid 市ID
     * @param pid 省ID
     */
    public Map<String, Float> sprayIrrigation(String fieldid,
                                              String vatid, String pid, String cid, String did) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Float> map = new HashMap<String, Float>();
        String now = sdf.format(new Date());
        // 通过地块id,作物品种id,追肥次数不为空 查询所有匹配的生育阶段序号
        try {
            List<IrCrpStage> Length = irCrpStageService.selectIrCrpStageName(fieldid, vatid, pid, cid, did);
            int max_Stage = 0;
            max_Stage = Length.get(Length.size()).getCrpStgId();
            // 追肥占施肥比例
            String dataKr = sfPggccsMService.ParaOfSpary(fieldid, vatid);
            float krn = Float.parseFloat(dataKr.split(",")[0]);
            float krp = Float.parseFloat(dataKr.split(",")[1]);
            float krk = Float.parseFloat(dataKr.split(",")[2]);
            String starttime = cropPlantService.SelectStartTime(fieldid, vatid);
            long date = sdf2.parse(starttime).getTime();
            for (int i = 1; i <= max_Stage; i++) {
                String stgname = Length.get(i - 1).getCrpStgName();
                int stgday = (Length.get(i - 1).getCrpStgDend()) - 1;
                int stgday2 = (Length.get(i - 1).getCrpStgDini()) - 1;
                // 通过 地块id,作物品种id,生育阶段序号 查询 施肥补N,P,K量
                String Fferts = sfXffxscMService.ParaOfFert(fieldid, vatid, i);
                // 施肥补N量
                float Ffert_N = Float.parseFloat(Fferts.split(",")[0]);
                // 施肥补P量
                float Ffert_P = Float.parseFloat(Fferts.split(",")[0]);
                // 施肥补K量
                float Ffert_K = Float.parseFloat(Fferts.split(",")[0]);
                // 追肥次数
                int nf = sfPggccsMService.FertNum(fieldid, vatid, i);
                // 第k次追肥N.p.k素比例
                String data = sfPggccsMService.FertRatio(fieldid, vatid, i);
                // 第k次追肥N素比例
                float KNI = Float.parseFloat(data.split(",")[0]);
                // 第k次追肥P素比例
                float KPI = Float.parseFloat(data.split(",")[1]);
                // 第k次追肥K素比例
                float KKI = Float.parseFloat(data.split(",")[2]);
                // 第i次追肥N素量
                float FAN_i = Ffert_N * KNI * krn;
                // 第i次追肥P素量
                float FAP_i = Ffert_P * KPI * krp;
                // 第i次追肥K素量
                float FAK_i = Ffert_K * KKI * krk;
                // 底肥N.P.K元素量
                float fansb = Ffert_N * (1 - krn);
                float faksb = Ffert_K * (1 - krk);
                float fapsb = Ffert_P * (1 - krp);
                map.put("FAN_I", FAN_i);
                map.put("FAP_I", FAP_i);
                map.put("FAK_I", FAK_i);
                map.put("FAN_SPRAY_BASIC", fansb);
                map.put("FAP_SPRAY_BASIC", fapsb);
                map.put("FAK_SPRAY_BASIC", faksb);

                // 根据地块id,作物品种id,当前日期 查询数据库有无数据
                int count = sfPgsfzdscMService.coutOfRecord(fieldid, vatid, i, now);
                SfPgsfzdscM record = new SfPgsfzdscM();
                if (count == 0) {
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    // 存入数据库
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setCrpStgId(i);
                    record.setFertITime(nf);
                    record.setCrpEtarealDate(sdf.parse(now));
                    record.setCrpStgName((stgname + "(" + sdf2.format(new Date(date5)) + "---" + sdf2.format(new Date(date3)) + ")"));
                    record.setFanI(FAN_i);
                    record.setFapI(FAP_i);
                    record.setFakI(FAK_i);
                    record.setFanSprayBasic(fansb);
                    record.setFapSprayBasic(fapsb);
                    record.setFakSprayBasic(faksb);
                    sfPgsfzdscMService.insert(record);
                    log.info("保存数据库成功------------------------");
                } else if (count == 1) {
                    long id = sfPgsfzdscMService.SelectId(fieldid, vatid, i, now);
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    // 更新数据库
                    record.setId(id);
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setCrpStgId(i);
                    record.setFertITime(nf);
                    record.setCrpEtarealDate(sdf.parse(now));
                    record.setCrpStgName((stgname + "(" + sdf2.format(new Date(date5)) + "---" + sdf2.format(new Date(date3)) + ")"));
                    record.setFanI(FAN_i);
                    record.setFapI(FAP_i);
                    record.setFakI(FAK_i);
                    record.setFanSprayBasic(fansb);
                    record.setFapSprayBasic(fapsb);
                    record.setFakSprayBasic(faksb);
                    sfPgsfzdscMService.Update(record);
                    log.info("调用 作物设定施肥分析模型更新成功------------------------");
                } else {
                    log.error("调用 作物设定施肥分析模型保存或更新失败,原因:数据库数据多于1条------------------------");
                }
                log.info("调用 作物设定施肥分析模型成功------------------------");
            }
        } catch (Exception e) {
            log.error("调用 作物设定施肥分析模型失败------------------------");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * ----自动滴灌----
     *
     * @param did
     * @param cid
     * @param pid
     * @return
     */
    public Map<String, Float> dropIrrigation(String fieldid,
                                             String vatid, String pid, String cid, String did) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Float> map = new HashMap<String, Float>();
        String now = sdf.format(new Date());
        try {
            List<IrCrpStage> Length = irCrpStageService.selectIrCrpStageName(fieldid, vatid, pid, cid, did);
            int max_Stage = Length.get(Length.size() - 1).getCrpStgId();
            // 追肥占施肥比
            String dataKr = sfDggccsMService.FertRatio(fieldid, vatid);
            float krn = Float.parseFloat(dataKr.split(",")[0]);
            float krp = Float.parseFloat(dataKr.split(",")[1]);
            float krk = Float.parseFloat(dataKr.split(",")[2]);
            String starttime = cropPlantService.SelectStartTime(fieldid, vatid);
            long date = sdf2.parse(starttime).getTime();
            for (int i = 1; i <= max_Stage; i++) {
                // K.N.P补肥量\第i个生育阶段耗K.N.P.元素量
                String data1 = sfXffxscMService.ParaOfFert(fieldid, vatid, i);
                float fertk = Float.parseFloat(data1.split(",")[0]);
                float fertn = Float.parseFloat(data1.split(",")[1]);
                float fertp = Float.parseFloat(data1.split(",")[2]);
                float fstagek = Float.parseFloat(data1.split(",")[3]);
                float fstagen = Float.parseFloat(data1.split(",")[4]);
                float fstagep = Float.parseFloat(data1.split(",")[5]);
                // 生育阶段长度
                int ndi = Length.get(i - 1).getCrpStgD();
                String stgname = Length.get(i - 1).getCrpStgName();
                int stgday = (Length.get(i - 1).getCrpStgDend()) - 1;
                int stgday2 = (Length.get(i - 1).getCrpStgDini()) - 1;
                // 模型开始
                // 第I个生育阶段，每日消耗N.P.K元素量
                float FANSstage_i = fstagen * krn / ndi;
                float FAPSstage_i = fstagep * krp / ndi;
                float FAKSstage_i = fstagek * krk / ndi;
                // 底肥N.P.K元素量
                float fandb = fertn * (1 - krn);
                float fakdb = fertk * (1 - krk);
                float fapdb = fertp * (1 - krp);
                map.put("fans", FANSstage_i);
                map.put("faps", FAPSstage_i);
                map.put("faks", FAKSstage_i);
                map.put("fandb", fandb);
                map.put("fapdb", fapdb);
                map.put("fakdb", fakdb);

                // 根据地块id,作物品种id,当前日期 查询数据库有无数据
                int count = sfZddgsfzdscMService.coutOfRecord(fieldid, vatid, i, now);
                SfZddgsfzdscM recrod = new SfZddgsfzdscM();
                if (count == 0) {
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    // 存入数据库
                    recrod.setFieldId(Long.parseLong(fieldid));
                    recrod.setCrpVrtId(Long.parseLong(vatid));
                    recrod.setCrpStgName((stgname + "(" + sdf2.format(new Date(date5)) + "---" + sdf2.format(new Date(date3)) + ")"));
                    recrod.setCrpStgId(i);
                    recrod.setFansI(FANSstage_i);
                    recrod.setCrpEtarealDate(now);
                    recrod.setFapsI(FAPSstage_i);
                    recrod.setFaksI(FAKSstage_i);
                    recrod.setFanDripBasic(fandb);
                    recrod.setFakDripBasic(fakdb);
                    recrod.setFapDripBasic(fapdb);
                    sfZddgsfzdscMService.insert(recrod);
                    log.info("保存数据库成功------------------------");
                } else if (count == 1) {
                    long id = sfZddgsfzdscMService.SelectId(fieldid, vatid, i, now);
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    // 更新数据库
                    recrod.setId(id);
                    recrod.setFieldId(Long.parseLong(fieldid));
                    recrod.setCrpVrtId(Long.parseLong(vatid));
                    recrod.setCrpStgName((stgname + "(" + sdf2.format(new Date(date5)) + "---" + sdf2.format(new Date(date3)) + ")"));
                    recrod.setCrpStgId(i);
                    recrod.setFansI(FANSstage_i);
                    recrod.setCrpEtarealDate(now);
                    recrod.setFapsI(FAPSstage_i);
                    recrod.setFaksI(FAKSstage_i);
                    recrod.setFanDripBasic(fandb);
                    recrod.setFakDripBasic(fakdb);
                    recrod.setFapDripBasic(fapdb);
                    sfZddgsfzdscMService.Update(recrod);
                    log.info("调用 作物设定施肥分析模型更新成功------------------------");
                } else {
                    log.error("调用 作物设定施肥分析模型保存或更新失败,原因:数据库数据多于1条------------------------");
                }
                log.info("调用 作物设定施肥分析模型成功------------------------");
            }

        } catch (Exception e) {
            log.error("调用 作物设定施肥分析模型失败------------------------");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 設定滴灌
     *
     * @param fieldid
     * @param vatid
     * @param did
     * @param cid
     * @param pid
     * @return
     */
    public Map<String, Float> SettingUpManuring(String fieldid, String vatid, String pid, String cid, String did) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date Now = new Date();
        String now = sdf.format(Now);
        Map<String, Float> map = new HashMap<String, Float>();
        try {
            List<IrCrpStage> Length = irCrpStageService.selectIrCrpStageName(vatid, fieldid, pid, cid, did);
            int max_Stage = Length.get(Length.size() - 1).getCrpStgId();
            // 追肥占施肥比
            String dataKr = sfDggccsMService.FertRatio(fieldid, vatid);
            float krn = Float.parseFloat(dataKr.split(",")[0]);
            float krp = Float.parseFloat(dataKr.split(",")[1]);
            float krk = Float.parseFloat(dataKr.split(",")[2]);
            String starttime = cropPlantService.SelectStartTime(fieldid, vatid);
            long date = sdf2.parse(starttime).getTime();
            // 根据生育阶段，地块id，作物品种id查找出追肥占施肥比率，第I个生育阶段K.N.O元素追肥比例，第i个生育阶段追肥次数
            for (int i = 1; i <= max_Stage; i++) {// 根据生育阶段序号查找数据直到i<=最大生育阶段数
                String stgname = Length.get(i - 1).getCrpStgName();
                int stgday = (Length.get(i - 1).getCrpStgDend()) - 1;
                int stgday2 = (Length.get(i - 1).getCrpStgDini()) - 1;
                String data1 = sfDggccsMService.FertPara(fieldid, vatid, i);
                float Kkdi = Float.parseFloat(data1.split(",")[0]);
                float Kndi = Float.parseFloat(data1.split(",")[1]);
                float Kpdi = Float.parseFloat(data1.split(",")[2]);
                int ndi = Integer.parseInt(data1.split(",")[3]);
                // 根据作物品种id和地块id查找出施肥补K.N.P量
                String data2 = sfXffxscMService.ParaOfFert(fieldid, vatid, i);
                float fertk = Float.parseFloat(data2.split(",")[0]);
                float fertn = Float.parseFloat(data2.split(",")[1]);
                float fertp = Float.parseFloat(data2.split(",")[2]);
                /**
                 * 模型開始
                 *
                 */
                // 第i个生育阶段单次追K.N.P元素量
                float fans = fertn * Kndi * krn / ndi;
                float faks = fertk * Kkdi * krk / ndi;
                float faps = fertp * Kpdi * krp / ndi;
                // 底肥K.N.P元素量
                float fandb = fertn * (1 - krn);
                float fakdb = fertk * (1 - krk);
                float fapdb = fertp * (1 - krp);
                map.put("fans", fans);
                map.put("faks", faks);
                map.put("faps", faps);
                map.put("fandb", fandb);
                map.put("fakdb", fakdb);
                map.put("fapdb", fapdb);
                // 存入数据库
                int count1 = sfSddgsfzdscMService.coutOfRecord(fieldid, vatid, i, now);
                SfSddgsfzdscM recrod = new SfSddgsfzdscM();
                if (count1 == 0) {
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    recrod.setFieldId(Long.parseLong(fieldid));
                    recrod.setCrpVrtId(Long.parseLong(vatid));
                    recrod.setFanDi(fans);
                    recrod.setFakDi(faks);
                    recrod.setFapDi(faps);
                    recrod.setCrpStgId(i);
                    recrod.setCrpStgName((stgname + "(" + sdf2.format(new Date(date5)) + "---" + sdf2.format(new Date(date3)) + ")"));
                    recrod.setFanDripBasic(fandb);
                    recrod.setFertITimes(ndi);
                    recrod.setFapDripBasic(fapdb);
                    recrod.setCrpEtarealDate(now);
                    recrod.setFakDripBasic(fakdb);
                    sfSddgsfzdscMService.insert(recrod);
                } else if (count1 == 1) {
                    // 通过地块编号,作物品种编号,预测日期 查询 id
                    long id = sfSddgsfzdscMService.SelectId(fieldid, vatid, i, now);
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    recrod.setId(id);
                    recrod.setFieldId(Long.parseLong(fieldid));
                    recrod.setCrpVrtId(Long.parseLong(vatid));
                    recrod.setFanDi(fans);
                    recrod.setFakDi(faks);
                    recrod.setFapDi(faps);
                    recrod.setCrpStgId(i);
                    recrod.setCrpStgName((stgname + "(" + sdf2.format(new Date(date5)) + "---" + sdf2.format(new Date(date3)) + ")"));
                    recrod.setFanDripBasic(fandb);
                    recrod.setFertITimes(ndi);
                    recrod.setFapDripBasic(fapdb);
                    recrod.setCrpEtarealDate(now);
                    recrod.setFakDripBasic(fakdb);
                    sfSddgsfzdscMService.Update(recrod);
                    log.info("调用 作物设定施肥分析模型更新成功------------------------");
                } else {
                    log.error("调用 作物设定施肥分析模型保存或更新失败,原因:数据库数据多于1条------------------------");
                }
                log.info("调用 作物设定施肥分析模型成功------------------------");
            }

        } catch (Exception e) {
            log.error("调用 作物设定施肥分析模型失败------------------------");
            e.printStackTrace();
        }
        return map;
    }
}
