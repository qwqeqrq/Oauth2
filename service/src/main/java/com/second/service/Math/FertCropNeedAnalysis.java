package com.second.service.Math;

import com.second.service.BaseDao.IrCrpStage;
import com.second.service.BaseDao.SfXffxscM;
import com.second.service.Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FertCropNeedAnalysis {

    private static Logger log = LoggerFactory.getLogger(FertCropNeedAnalysis.class);

    @Autowired
    IrCrpStageService irCrpStageService;
    @Autowired
    IrCrpEtayldMaxService irCrpEtayldMaxService;
    @Autowired
    SfTrgfcsMService sfTrgfcsMService;
    @Autowired
    SfCtpfMService sfCtpfMService;
    @Autowired
    SfZwxfcsMService sfZwxfcsMService;
    @Autowired
    CropPlantService cropPlantService;
    @Autowired
    SfXffxscMService sfXffxscMService;

    /**
     * ----无作物每日每株耗养分量参数时（无丰缺系数版本）----
     *
     * @param fieldid 地块编号
     * @param vatid   作物品种编号
     * @param did
     * @param pid
     * @param cid
     * @return 第i个生育阶段耗N, P, K素量
     */
    public List<Map<String, Float>> cropNeedAnalysis(String fieldid, String vatid, String pid, String cid, String did) {
        log.info("调用 作物需肥分析模型开始------------------------");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        String nowOfYear = sdf2.format(new Date());
        List<Map<String, Float>> list = new ArrayList<Map<String, Float>>();
        Map<String, Float> map = new HashMap<String, Float>();

        try {
            List<IrCrpStage> Length = irCrpStageService.selectIrCrpStageName(vatid, fieldid, pid, cid, did);
            int max_Stage = Length.get(Length.size() - 1).getCrpStgId();
            // 通过地块编号,作物品种编号 查询目标产量
            float Yaim = irCrpEtayldMaxService.SearchYaim(fieldid, vatid, nowOfYear);
            // 通过地块编号,作物品种编号 查询土壤N校正系数,土壤P校正系数,土壤K校正系数
            String data1 = sfTrgfcsMService.getParaOfField(fieldid, vatid);
            // 土壤N校正系数
            float aN = Float.parseFloat(data1.split(",")[0]);
            // 土壤P校正系数
            float aP = Float.parseFloat(data1.split(",")[1]);
            // 土壤K校正系数
            float aK = Float.parseFloat(data1.split(",")[2]);
            // 获取每个生育阶段的数据
            // 土壤中N.P.K含量值
            String data3 = sfCtpfMService.getElementOfField(fieldid);
            float Ftest_K = Float.parseFloat(data3.split(",")[0]);
            float Ftest_N = Float.parseFloat(data3.split(",")[1]);
            float Ftest_P = Float.parseFloat(data3.split(",")[2]);
            // 通过地块编号,作物品种编号,生育阶段序号 查询 百千克产量需N,P,K量 ,第i个生育阶段N.P.K消耗比
            String data2 = sfZwxfcsMService.FertNeedOfElement(fieldid, vatid);
            // 百千克产量需N量
            float fN = Float.parseFloat(data2.split(",")[0]);
            // 百千克产量需P量
            float fP = Float.parseFloat(data2.split(",")[1]);
            // 百千克产量需K量
            float fK = Float.parseFloat(data2.split(",")[2]);
            String starttime = cropPlantService.SelectStartTime(fieldid, vatid);
            long date = sdf.parse(starttime).getTime();
            for (int i = 1; i <= max_Stage; i++) {
                String stgname = Length.get(i - 1).getCrpStgName();
                int stgday = (Length.get(i - 1).getCrpStgDend()) - 1;
                int stgday2 = (Length.get(i - 1).getCrpStgDini()) - 1;
                // 通过地块编号,作物品种编号,生育阶段序号 查询 百千克产量需N,P,K量 ,第i个生育阶段N.P.K消耗比
                String data = sfZwxfcsMService.FertNeedOfElementEveryFertTime(fieldid, vatid, i);
                // 第i个生育阶段耗N素比例
                float KNSI = Float.parseFloat(data.split(",")[3]);
                // 第i个生育阶段耗P素比例
                float KPSI = Float.parseFloat(data.split(",")[4]);
                // 第i个生育阶段耗K素比例
                float KKSI = Float.parseFloat(data.split(",")[5]);
                // 目标产量需N量
                float Faim_N = (Yaim / 100) * fN;
                // 目标产量需P量
                float Faim_P = (Yaim / 100) * fP;
                // 目标产量需K量
                float Faim_K = (Yaim / 100) * fK;
                // 土壤供N量
                float Fsoil_N = (float) (Ftest_N * aN * 0.15);
                // 土壤供P量
                float Fsoil_P = (float) (Ftest_P * aP * 0.15);
                // 土壤供K量
                float Fsoil_K = (float) (Ftest_K * aK * 0.15);
                // 施肥补N量
                float Ffert_N = Faim_N - Fsoil_N;
                // 施肥补P量
                float Ffert_P = Faim_P - Fsoil_P;
                // 施肥补K量
                float Ffert_K = Faim_K - Fsoil_K;
                // 第i个生育阶段耗N素量
                float FANstage_i = Ffert_N * KNSI;
                // 第i个生育阶段耗P素量
                float FAPstage_i = Ffert_P * KPSI;
                // 第i个生育阶段耗K素量
                float FAKstage_i = Ffert_K * KKSI;

                map.put("N", FANstage_i);
                map.put("P", FAPstage_i);
                map.put("K", FAKstage_i);

                list.add(map);

                // 通过 地块id,作物品种id,当前时间 查询有无数据
                int count = sfXffxscMService.coutOfRecord(fieldid, vatid, i, nowOfYear);
                if (count == 0) {
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    // 存入数据库
                    SfXffxscM record = new SfXffxscM();
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setCrpStgId(i);
                    record.setCrpEtarealDate(nowOfYear);
                    record.setCrpYldMax(Yaim);
                    record.setSoilccN(aN);
                    record.setSoilccP(String.valueOf(aP));
                    record.setSoilccK(String.valueOf(aK));
                    record.setCrpStgName((stgname + "(" + sdf.format(new Date(date5)) + "---" + sdf.format(new Date(date3)) + ")"));
                    record.setFertAimAn(Faim_N);
                    record.setFertAimAp(Faim_P);
                    record.setFertAimAk(Faim_K);
                    record.setSoilsupAn(Fsoil_N);
                    record.setSoilsupAp(Fsoil_P);
                    record.setSoilsupAk(Fsoil_K);
                    record.setFertsupAn(Ffert_N);
                    record.setFertsupAp(Ffert_P);
                    record.setFertsupAk(Ffert_K);
                    record.setFstageAnI(FANstage_i);
                    record.setFstageApI(FAPstage_i);
                    record.setFstageAkI(FAKstage_i);
                    sfXffxscMService.insert(record);
                    log.info("调用 作物需肥分析模型存储成功------------------------");
                } else if (count == 1) {
                    long id = sfXffxscMService.SelectId(fieldid, vatid, i, nowOfYear);
                    long date2 = stgday * 864;
                    long datE2 = date2 * 100000;
                    long date4 = stgday2 * 864;
                    long datE4 = date4 * 100000;
                    long date3 = date + datE2;
                    long date5 = date + datE4;
                    // 更新数据库
                    SfXffxscM record = new SfXffxscM();
                    record.setId(id);
                    record.setFieldId(Long.parseLong(fieldid));
                    record.setCrpVrtId(Long.parseLong(vatid));
                    record.setCrpStgId(i);
                    record.setCrpEtarealDate(nowOfYear);
                    record.setCrpYldMax(Yaim);
                    record.setSoilccN(aN);
                    record.setSoilccP(String.valueOf(aP));
                    record.setSoilccK(String.valueOf(aK));
                    record.setCrpStgName((stgname + "(" + sdf.format(new Date(date5)) + "---" + sdf.format(new Date(date3)) + ")"));
                    record.setFertAimAn(Faim_N);
                    record.setFertAimAp(Faim_P);
                    record.setFertAimAk(Faim_K);
                    record.setSoilsupAn(Fsoil_N);
                    record.setSoilsupAp(Fsoil_P);
                    record.setSoilsupAk(Fsoil_K);
                    record.setFertsupAn(Ffert_N);
                    record.setFertsupAp(Ffert_P);
                    record.setFertsupAk(Ffert_K);
                    record.setFstageAnI(FANstage_i);
                    record.setFstageApI(FAPstage_i);
                    record.setFstageAkI(FAKstage_i);
                    sfXffxscMService.Update(record);
                    log.info("调用 作物需肥分析模型更新成功------------------------");
                } else {
                    log.error("调用 作物需肥分析模型保存或更新失败,原因:数据库数据多于1条------------------------");
                }
                log.info("调用 作物需肥分析模型成功------------------------");
            }

        } catch (Exception e) {
            log.error("调用 作物需肥分析模型失败------------------------");
            e.printStackTrace();
        }
        return list;
    }
}
