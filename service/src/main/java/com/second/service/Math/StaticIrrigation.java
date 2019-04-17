package com.second.service.Math;

import com.second.service.BaseUtils.DateUtils;
import com.second.service.Service.BaseDataPearsonService;
import com.second.service.Service.CropPlantService;
import com.second.service.Service.IrBcropMService;
import com.second.service.Service.IrQxdtcrMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class StaticIrrigation {
    private static Logger log = LoggerFactory.getLogger(StaticIrrigation.class);

    @Autowired
    private CropPlantService cropPlantService;
    @Autowired
    private IrBcropMService irBcropMService;
    @Autowired
    private IrQxdtcrMService irQxdtcrMService;
    @Autowired
    private BaseDataPearsonService baseDataPearsonService;

    /**
     * 水文年型计算
     *
     * @param fieldid 地块id
     * @param vatid   作物品种id
     * @return
     */
    public Map<String, Object> HydrologicalYearTypeCalculation(String fieldid, String vatid, int msg) {
        DecimalFormat decimalFormatForHundred = new DecimalFormat("0.00");
        DecimalFormat decimalFormatForTen = new DecimalFormat("0.0");
        Calendar cal = Calendar.getInstance();
        Map<String, Object> map = new HashMap<>();
        Map<Integer, Float> FR_CV = new HashMap<>();
        Map<Integer, Float> FR_CV_ET0 = new HashMap<>();
        Map<Integer, Float> FR_CV_P = new HashMap<>();
        // IR_W01_0001：种植日期，收获日期
        try {
            Date DateSeeding = DateUtils.formatStrToDate(cropPlantService.SelectStartTime(fieldid, vatid), "yyyy-MM-dd");
            String nowyear = String.valueOf(DateUtils.getYear(DateSeeding));
            Date DateEnding = DateUtils.formatStrToDate(cropPlantService.SelectEndTime(fieldid, vatid), "yyyy-MM-dd");
            if (DateEnding == null) {
                DateSeeding = DateUtils.formatStrToDate((nowyear + "-" + irBcropMService.FindcrpVrtSeeddateM(vatid) + "-" + irBcropMService.FindcrpVrtSeeddateD(vatid)), "yyyy-MM-dd");
                DateEnding = DateUtils.formatStrToDate((nowyear + "-" + irBcropMService.FindcrpVrtHvsdateM(vatid) + "-" + irBcropMService.FindcrpVrtHvsdateD(vatid)), "yyyy-MM-dd");
                if (DateSeeding.getTime() >= DateEnding.getTime()) {
                    DateEnding = DateUtils.formatStrToDate((String.valueOf(Integer.parseInt(nowyear) + 1) + "-" + irBcropMService.FindcrpVrtHvsdateM(vatid) + "-" + irBcropMService.FindcrpVrtHvsdateD(vatid)), "yyyy-MM-dd");
                }
            }
            long dateseeding = DateSeeding.getTime();
            Date DateSeeding1 = DateUtils.formatStrToDate((DateUtils.dateAdd("d", 1, DateSeeding)), "yyyy-MM-dd");
            long dateending = DateEnding.getTime();
            // IR_wo1_0003:获取当前地块绑定的气象站
            String QXZID = null;
            try {
                QXZID = irQxdtcrMService.fildQxzId(fieldid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 根据气象站编号获取气象站种类，并判断属于哪种气象站
            float ET0ini = 0;
            float Pini = 0;
            float SumEt0 = 0;
            float SumP = 0;
            double Xp25 = 0;
            double Xp50 = 0;
            double Xp75 = 0;
            String Yresult25seed = null;
            String Yresult50seed = null;
            String Yresult75seed = null;
            String Yresult25end = null;
            String Yresult50end = null;
            String Yresult75end = null;
            // 根据播种时间查找出et0和降雨量p
            String Date = irQxdtcrMService.findQxdate(QXZID, DateSeeding, DateSeeding1);
            float et0sum = 0;
            float psum = 0;
            try {
                if (Date == null) {
                    ET0ini = irQxdtcrMService.findEt0InMonth(QXZID, DateSeeding);
                    Pini = irQxdtcrMService.findCrInMonth(QXZID, DateSeeding);
                } else {
                    String[] a = Date.split(",");
                    for (int i = 1; i <= a.length; i++) {
                        String time = a[i - 1];
                        et0sum += irQxdtcrMService.findEt0InDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd HH:mm:ss"));
                        psum += irQxdtcrMService.findCrInDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd HH:mm:ss"));
                    }
                    ET0ini = et0sum / a.length;
                    Pini = psum / a.length;
                }
            } catch (Exception e) {
                ET0ini = 0;
                Pini = 0;
            }
            SumEt0 = ET0ini;
            SumP = Pini;
            FR_CV_ET0.put(0, ET0ini);
            FR_CV_P.put(0, Pini);
            float ET0end = 0;
            float Pend = 0;
            int j = 1;
            // 根据播种收获日期计算出播种时间内的ET0总和和降雨量p总和
            for (long i = dateseeding + 86400000; i <= dateending; i = i + 86400000, j = j + 1) {
                et0sum = 0;
                psum = 0;
                Date ET0endDate = new Date(i);
                long et0enddate1 = i + 86400000;
                Date ET0EndDate1 = new Date(et0enddate1);
                String Et0Dateend = irQxdtcrMService.findQxdate(QXZID, ET0endDate, ET0EndDate1);
                try {
                    if (Et0Dateend == null) {
                        ET0end = irQxdtcrMService.findEt0InMonth(QXZID, ET0endDate);
                        Pend = irQxdtcrMService.findCrInMonth(QXZID, ET0endDate);
                    } else {
                        String[] c = Et0Dateend.split(",");
                        for (int k = 1; k <= c.length; k++) {
                            String time = c[k - 1];
                            et0sum += irQxdtcrMService.findEt0InDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd"));
                            psum += irQxdtcrMService.findCrInDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd"));
                        }
                        ET0end = et0sum / c.length;
                        Pend = psum / c.length;
                    }
                } catch (Exception e) {
                    ET0end = 0;
                    Pend = 0;
                }
                SumEt0 += ET0end;
                SumP += Pend;
                FR_CV_ET0.put(j, ET0end);
                FR_CV_P.put(j, Pend);
            }

            if (msg == 0) {
                log.info("该用户使用的是ET0计算方法！");
                float FRsum = SumEt0;
                FR_CV = FR_CV_ET0;
                // FR均值
                float FRavg = FRsum / DateUtils.daysBetween(DateSeeding, DateEnding);
                // 离差系数(利用方差计算)
                double FRCV = 0;
                for (int i = 1; i <= FR_CV.size(); i++) {
                    FRCV += Math.pow(FR_CV.get(i - 1) - FRavg, 2);
                }
                FRCV = FRCV / (FR_CV.size() - 1);
                FRCV = Double.parseDouble(decimalFormatForHundred.format(Math.sqrt(FRCV)));
                // 偏差系数
                double FRCS = Double.parseDouble(decimalFormatForHundred.format(FRCV * 2.5));
                float FRCStest = Float.parseFloat(decimalFormatForTen.format(FRCS));
                if (FRCS < FRCStest) {
                    FRCStest = Float.parseFloat(decimalFormatForTen.format(FRCStest - 0.1));
                }
                /**
                 * 根据皮尔逊Ⅲ型计算公式得出X(P)
                 */
                // 1.查询数据库找出皮尔逊Ⅲ型系数(p=0.25)
                float F25B = baseDataPearsonService.findPara(FRCStest, 25, 0);
                float F25F = baseDataPearsonService.findPara(FRCStest, 25, 0.1f);
                // 2.求出F(FR_CS,P)
                double Ffr_csp25 = F25B + ((F25F - F25B) * (FRCS - F25B) * 10);
                // 3.计算K(P)
                double Kp25 = Ffr_csp25 * FRCV;
                // 计算X(P)
                Xp25 = Kp25 * FRavg;
                // 1.查询数据库找出皮尔逊Ⅲ型系数(p=0.50)
                float F50B = baseDataPearsonService.findPara(FRCStest, 50, 0);
                float F50F = baseDataPearsonService.findPara(FRCStest, 50, 0.1f);
                // 2.求出F(FR_CS,P)
                double Ffr_csp50 = F50B + ((F50F - F50B) * (FRCS - F50B) * 10);
                // 3.计算K(P)
                double Kp50 = Ffr_csp50 * FRCV;
                // 计算X(P)
                Xp50 = Kp50 * FRavg;
                // 1.查询数据库找出皮尔逊Ⅲ型系数(p=0.75)
                float F75B = baseDataPearsonService.findPara(FRCStest, 75, 0);
                float F75F = baseDataPearsonService.findPara(FRCStest, 75, 0.1f);
                // 2.求出F(FR_CS,P)
                double Ffr_csp75 = F75B + ((F75F - F75B) * (FRCS - F75B) * 10);
                // 3.计算K(P)
                double Kp75 = Ffr_csp75 * FRCV;
                // 计算X(P)
                Xp75 = Kp75 * FRavg;

            } else {
                log.info("该用户使用的是降雨量计算方法！");
                float FRsum = SumP;
                FR_CV = FR_CV_P;
                // FR均值
                float FRavg = FRsum / ((dateending - dateseeding) / 86400000);
                // 离差系数(利用方差计算)
                float FRCV = 0;
                for (int i = 1; i <= FR_CV.size(); i++) {
                    FRCV += Math.pow(FR_CV.get(i - 1), 2);
                }
                FRCV = FRCV / (FR_CV.size() - 1);
                FRCV = Float.parseFloat(decimalFormatForHundred.format(Math.sqrt(FRCV)));
                // 偏差系数
                double FRCS = Double.parseDouble(decimalFormatForHundred.format(FRCV * 2.5));
                float FRCStest = Float.parseFloat(decimalFormatForTen.format(FRCS * 2.5));
                if (FRCS < FRCStest) {
                    FRCStest = (float) (FRCS - 0.1);
                }
                /**
                 * 根据皮尔逊Ⅲ型计算公式得出X(P)
                 */
                // 1.查询数据库找出皮尔逊Ⅲ型系数(p=0.25)
                float F25B = baseDataPearsonService.findPara(FRCStest, 25, 0);
                float F25F = baseDataPearsonService.findPara(FRCStest, 25, 0.1f);
                // 2.求出F(FR_CS,P)
                double Ffr_csp25 = F25B + ((F25F - F25B) * (FRCS - F25B) * 10);
                // 3.计算K(P)
                double Kp25 = Ffr_csp25 * FRCV;
                // 计算X(P)
                Xp25 = Kp25 * FRavg;
                // 1.查询数据库找出皮尔逊Ⅲ型系数(p=0.50)
                float F50B = baseDataPearsonService.findPara(FRCStest, 50, 0);
                float F50F = baseDataPearsonService.findPara(FRCStest, 50, 0.1f);
                // 2.求出F(FR_CS,P)
                double Ffr_csp50 = F50B + ((F50F - F50B) * (FRCS - F50B) * 10);
                // 3.计算K(P)
                double Kp50 = Ffr_csp50 * FRCV;
                // 计算X(P)
                Xp50 = Kp50 * FRavg;
                // 1.查询数据库找出皮尔逊Ⅲ型系数(p=0.75)
                float F75B = baseDataPearsonService.findPara(FRCStest, 75, 0);
                float F75F = baseDataPearsonService.findPara(FRCStest, 75, 0.1f);
                // 2.求出F(FR_CS,P)
                double Ffr_csp75 = F75B + ((F75F - F75B) * (FRCS - F75B) * 10);
                // 3.计算K(P)
                double Kp75 = Ffr_csp75 * FRCV;
                // 计算X(P)
                Xp75 = Kp75 * FRavg;
            }
            Date year = irQxdtcrMService.findMinQxDate(QXZID);
            int Year = Integer.parseInt(DateUtils.formatDateToStr(year, "yyyy"));
            String[] monthanddayseed = (DateUtils.formatDateToStr(DateSeeding, "yyyy-MM-dd")).split("-");
            String[] monthanddayend = (DateUtils.formatDateToStr(DateEnding, "yyyy-MM-dd")).split("-");
            String monthseed = monthanddayseed[1];
            String dayseed = monthanddayseed[2];
            String monthend = monthanddayend[1];
            String dayend = monthanddayend[2];
            double compare25 = 0;
            double compare125[] = new double[Integer.parseInt(DateUtils.formatDateToStr(irQxdtcrMService.findMaxQxDate(QXZID), "yyyy")) - Year + 1];
            double compareresult25 = 0;
            double compare50 = 0;
            double compare150[] = new double[Integer.parseInt(DateUtils.formatDateToStr(irQxdtcrMService.findMaxQxDate(QXZID), "yyyy")) - Year + 1];
            double compareresult50 = 0;
            double compare75 = 0;
            double compare175[] = new double[Integer.parseInt(DateUtils.formatDateToStr(irQxdtcrMService.findMaxQxDate(QXZID), "yyyy")) - Year + 1];
            double compareresult75 = 0;
            for (int y = Year; y <= Integer.parseInt(DateUtils.formatDateToStr(irQxdtcrMService.findMaxQxDate(QXZID), "yyyy")); y++) {
                SumEt0 = 0;
                SumP = 0;
                String Y = DateUtils.formatDateToStr(irQxdtcrMService.findMinQxDate(QXZID), "yyyy");
                String HistoryDateSeeding = Y + "-" + monthseed + "-" + dayseed;
                long historydateseeding = DateUtils.formatStrToDate((HistoryDateSeeding), "yyyy-MM-dd").getTime();
                String HistoryDateSeeding1 = DateUtils.dateAdd("d", 1, DateUtils.formatStrToDate((HistoryDateSeeding), "yyyy-MM-dd"));
                String HistoryDateEnding = Y + "-" + monthend + "-" + dayend;
                long historydateending = DateUtils.formatStrToDate((HistoryDateEnding), "yyyy-MM-dd").getTime();
                if (historydateseeding >= historydateending) {
                    HistoryDateEnding = DateUtils.dateAdd("y", 1, DateUtils.formatStrToDate(HistoryDateEnding, "yyyy-MM-dd"));
                    historydateending = DateUtils.formatStrToDate(HistoryDateEnding, "yyyy-MM-dd").getTime();
                }
                String Et0Datehistory = irQxdtcrMService.findQxdate(QXZID, DateUtils.formatStrToDate(HistoryDateSeeding, "yyyy-MM-dd"), DateUtils.formatStrToDate(HistoryDateSeeding1, "yyyy-MM-dd"));
                float et0sumhistory = 0;
                float psumhistory = 0;
                try {
                    if (Et0Datehistory == null) {
                        ET0ini = irQxdtcrMService.findEt0InMonth(QXZID, DateUtils.formatStrToDate(HistoryDateSeeding, "yyyy-MM-dd HH:mm:ss"));
                        Pini = irQxdtcrMService.findCrInMonth(QXZID, DateUtils.formatStrToDate(HistoryDateSeeding, "yyyy-MM-dd HH:mm:ss"));
                    } else {
                        String[] ahistory = Et0Datehistory.split(",");
                        for (int i = 1; i <= ahistory.length; i++) {
                            String time = ahistory[i - 1];
                            et0sumhistory += irQxdtcrMService.findEt0InDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd HH:mm:ss "));
                            psumhistory += irQxdtcrMService.findCrInDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd HH:mm:ss"));
                        }
                        ET0ini = et0sumhistory / ahistory.length;
                        Pini = psumhistory / ahistory.length;
                    }
                } catch (Exception e) {
                    ET0ini = 0;
                    Pini = 0;
                }
                SumEt0 += ET0ini;
                SumP += Pini;
                // 根据播种收获日期计算出播种时间内的ET0总和和降雨量p总和
                int u = 1;
                for (long i = historydateseeding + 86400000; i <= historydateending; i = i + 86400000, u = u + 1) {
                    Date ET0endDate = new Date(i);
                    long et0enddate1 = i + 86400000;
                    Date ET0EndDate1 = new Date(et0enddate1);
                    String Et0Dateend = irQxdtcrMService.findQxdate(QXZID, ET0endDate, ET0EndDate1);
                    try {
                        if (Et0Dateend == null) {
                            ET0end = irQxdtcrMService.findEt0InMonth(QXZID, ET0endDate);
                            Pend = irQxdtcrMService.findCrInMonth(QXZID, ET0endDate);
                        } else {
                            String[] c = Et0Dateend.split(",");
                            for (int k = 1; k <= c.length; k++) {
                                String time = c[k - 1];
                                et0sum += irQxdtcrMService.findEt0InDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd HH:mm:ss"));
                                psum += irQxdtcrMService.findCrInDate(QXZID, DateUtils.formatStrToDate(time, "yyyy-MM-dd HH:mm:ss"));
                            }
                            ET0end = et0sum / c.length;
                            Pend = psum / c.length;
                        }
                    } catch (Exception e) {
                        ET0end = 0;
                        Pend = 0;
                    }
                    SumEt0 += ET0end;
                    SumP += Pend;
                }
                double FR_SUM = 0;
                if (msg == 0) {
                    log.info("此地块使用的是ET0方法！");
                    FR_SUM = SumEt0;
                } else {
                    log.info("此地块使用的是降雨量方法！");
                    FR_SUM = SumP;
                }
                compare25 = Math.abs(Xp25 - FR_SUM);
                compare125[y - Year] = compare25;
                compare50 = Math.abs(Xp50 - FR_SUM);
                compare150[y - Year] = compare50;
                compare75 = Math.abs(Xp75 - FR_SUM);
                compare175[y - Year] = compare75;
            }
            for (int p = 1; p <= compare125.length; p++) {
                if (p == 1) {
                    compareresult25 = compare125[0];
                } else {
                    compareresult25 = Math.min(compare125[p - 1],
                            compareresult25);
                }
            }
            for (int u = 1; u <= compare125.length; u++) {
                if (compareresult25 == compare125[u - 1]) {
                    Yresult25seed = String.valueOf(u - 1 + Year) + "-"
                            + monthseed + "-" + dayseed;
                    Yresult25end = String.valueOf(u - 1 + Year) + "-"
                            + monthend + "-" + dayend;
                    break;
                }
            }
            for (int p = 1; p <= compare150.length; p++) {
                if (p == 1) {
                    compareresult50 = compare150[0];
                } else {
                    compareresult50 = Math.min(compare150[p - 1],
                            compareresult50);

                }
            }
            for (int u = 1; u <= compare150.length; u++) {
                if (compareresult50 == compare150[u - 1]) {
                    Yresult50seed = String.valueOf(u - 1 + Year) + "-"
                            + monthseed + "-" + dayseed;
                    Yresult50end = String.valueOf(u - 1 + Year) + "-"
                            + monthend + "-" + dayend;
                    break;
                }
            }
            for (int p = 1; p <= compare175.length; p++) {
                if (p == 1) {
                    compareresult75 = compare175[0];
                } else {
                    compareresult75 = Math.min(compare175[p - 1],
                            compareresult75);
                }
            }
            for (int u = 1; u <= compare125.length; u++) {
                if (compareresult75 == compare175[u - 1]) {
                    Yresult75seed = String.valueOf(u + Year - 1) + "-"
                            + monthseed + "-" + dayseed;
                    Yresult75end = String.valueOf(u - 1 + Year) + "-"
                            + monthend + "-" + dayend;
                    break;
                }
            }

            // 存储水文年型结果进数据库(?)
            map.put("25seed", Yresult25seed);
            log.info("丰水年为" + Yresult25seed);
            map.put("25end", Yresult25end);
            map.put("50seed", Yresult50seed);
            log.info("平水年为" + Yresult50seed);
            map.put("50end", Yresult50end);
            map.put("75seed", Yresult75seed);
            log.info("枯水年为" + Yresult75seed);
            map.put("75end", Yresult75end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 统计灌溉制度
     *
     * @param fieldid
     * @param vatid
     * @return
     *
     @SuppressWarnings("unused") public static Map<String, Object> StatisticIrrigation(String fieldid,
     String vatid, int msg, String pid, String did, String cid) {
     Map<String, Object> map = new HashMap<String, Object>();
     Calendar calF = Calendar.getInstance();
     Calendar calP = Calendar.getInstance();
     Calendar calK = Calendar.getInstance();
     Calendar calF1 = Calendar.getInstance();
     Calendar calP1 = Calendar.getInstance();
     Calendar calK1 = Calendar.getInstance();
     Calendar cal = Calendar.getInstance();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     try {
     int type = 0;
     String function = "";
     float moist = 0;
     if (Device.dao.isPattern(Db.queryStr(
     "SELECT ANAME FROM AREA WHERE ID=?", fieldid)) != null) {
     type = 4;
     function = "喷灌";
     moist = 1;
     } else {
     type = 5;
     function = "滴灌";
     moist = (float) 0.5;
     }
     Map<String, Object> HydrologicalYearTypeCalculation = HydrologicalYearTypeCalculation(
     fieldid, vatid, msg);
     // 查询该地块种植的作物处于哪个气象站
     int QXZID = Integer
     .parseInt(Db
     .queryStr(
     "SELECT A.QXZ_STN_ID FROM t_ir_qxzdvc_m AS A, area AS B WHERE B.id=? AND a.QXZ_TYPE ='基本站' OR a.QXZ_TYPE='基准站' AND b.lat is not NULL AND b.lng is not NULL ORDER BY SQRT(POW((A.QXZ_X-B.lng),2)+POW((a.QXZ_Y-b.lat),2)) LIMIT 1",
     fieldid));

     // 获取地块种植信息
     Date DateSeeding = Db
     .queryDate(
     "SELECT CRP_YYMM FROM T_IR_FIELD_M WHERE FIELD_ID=? AND CRP_VRT_ID=?",
     fieldid, vatid);
     Date DateEnding = Db
     .queryDate(
     "SELECT CRP_HVSD FROM T_IR_FIELD_M WHERE FIELD_ID=? AND CRP_VRT_ID=?",
     fieldid, vatid);
     String nowyear = String.valueOf(cal.get(Calendar.YEAR));
     if (DateEnding == null) {
     DateSeeding = sdf
     .parse(nowyear
     + "-"
     + Db.queryStr(
     "SELECT crpVrtSeeddateM FROM T_IR_BCROP_M WHERE CRP_VRT_ID=?",
     vatid)
     + "-"
     + Db.queryStr(
     "SELECT crpVrtSeeddateD FROM T_IR_BCROP_M WHERE CRP_VRT_ID=?",
     vatid));
     DateEnding = sdf
     .parse(nowyear
     + "-"
     + Db.queryStr(
     "SELECT crpVrtHvsdateM FROM T_IR_BCROP_M WHERE CRP_VRT_ID=?",
     vatid)
     + "-"
     + Db.queryStr(
     "SELECT crpVrtHvsdateD FROM T_IR_BCROP_M WHERE CRP_VRT_ID=?",
     vatid));
     }
     long dateseeding = DateSeeding.getTime();
     long dateending = DateEnding.getTime();
     if (dateending < dateseeding) {
     DateEnding = sdf
     .parse(String.valueOf(Integer.parseInt(nowyear) + 1)
     + "-"
     + Db.queryStr(
     "SELECT crpVrtHvsdateM FROM T_IR_BCROP_M WHERE CRP_VRT_ID=?",
     vatid)
     + "-"
     + Db.queryStr(
     "SELECT crpVrtHvsdateD FROM T_IR_BCROP_M WHERE CRP_VRT_ID=?",
     vatid));
     dateending = DateEnding.getTime();
     }
     // 获取种植面积
     int A = Db.queryInt(
     "SELECT AREA FROM CROP_PLANT WHERE rid=? AND varietyId=?",
     fieldid, vatid);
     // 根层土壤有效降水比例α
     int α = Db
     .queryInt(
     "SELECT SLWC_IRR_YXBL FROM T_IR_FLDSLTP_M WHERE FIELD_ID=? AND CRP_VRT_ID=? ",
     fieldid, vatid);
     // 土壤允许消耗水量Psw
     int Psw = Db
     .queryInt(
     "SELECT SLTP_IRR_PSW FROM T_IR_FLDSLTP_M WHERE FIELD_ID=? AND CRP_VRT_ID=? ",
     fieldid, vatid);
     // 灌溉水利用系数
     float γ = Db.queryFloat(
     "SELECT IRR_DVC_EFF FROM T_IR_DEVICEIRR_M WHERE TYPE=?",
     type);
     int yearF = 0;
     int yearP = 0;
     int yearK = 0;
     int yearF1 = 0;
     int yearP1 = 0;
     int yearK1 = 0;
     String yearseedF = HydrologicalYearTypeCalculation.get("25seed")
     .toString();
     String yearseedP = HydrologicalYearTypeCalculation.get("50seed")
     .toString();
     String yearseedK = HydrologicalYearTypeCalculation.get("75seed")
     .toString();
     String yearendF = HydrologicalYearTypeCalculation.get("25end")
     .toString();
     String yearendP = HydrologicalYearTypeCalculation.get("50end")
     .toString();
     String yearendK = HydrologicalYearTypeCalculation.get("75end")
     .toString();
     calF.setTime(sdf.parse(yearseedF));
     yearF = calF.get(Calendar.YEAR);
     calP.setTime(sdf.parse(yearseedP));
     yearP = calP.get(Calendar.YEAR);
     calK.setTime(sdf.parse(yearseedK));
     yearK = calK.get(Calendar.YEAR);
     calF1.setTime(sdf.parse(yearendF));
     yearF1 = calF.get(Calendar.YEAR);
     calP1.setTime(sdf.parse(yearendP));
     yearP1 = calP.get(Calendar.YEAR);
     calK1.setTime(sdf.parse(yearendK));
     yearK1 = calK.get(Calendar.YEAR);

     int d = 1;// 代表种植天数（初始值为1）
     double Ws = 0;
     double Dr = 0;
     int K = 0;
     double I = 0;
     double Wi = 0;
     float eta = 0;
     d = 1;// 代表种植天数（初始值为1）
     Ws = 0;
     Dr = 0;
     K = 0;
     I = 0;
     Wi = 0;
     eta = 0;
     // 根据种植时间（播种日期-收获日期）取出Kc经验值表及时段初时段末天数及当前时间所处生育阶段长度
     for (long i = dateseeding; i <= dateending; i = i + 86400000, d += 1) {
     // 获取水文年的种植日期的月份及收获日期的月份
     String seed[] = sdf.format(new Date(i)).split("-");
     int mounthseed = Integer.parseInt(seed[1]);
     int dayseed = Integer.parseInt(seed[2]);
     Date beginYear = sdf.parse(String.valueOf(yearP) + "-"
     + mounthseed + "-" + dayseed);
     int stage = Db
     .queryInt(
     "SELECT CRP_STG_ID FROM T_IR_CRP_STAGE WHERE CRP_VRT_ID=? AND CRP_STG_DINI<=? AND CRP_STG_DEND>=?",
     vatid, d, d);
     List<IrCrpStage> Length = IrCrpStage.dao.selectIrCrpStageName(
     vatid, fieldid, pid, cid, did);

     // 特殊需水量
     float Wsp = 0;
     try {
     Wsp = Db.queryFloat(
     "SELECT CRP_STGWT_SP FROM T_IR_CRP_DMDPARA WHERE FIELD_ID=? AND CRP_VRT_ID=? AND CRP_STG_ID=?",
     fieldid, vatid, stage);
     } catch (Exception e) {
     Wsp = 0;
     }
     // 土壤含水量下限
     int β = 0;
     try {
     β = Db.queryInt(
     "SELECT CRPSM_LOW_LIMIT FROM T_IR_CRPSM_LIMIT WHERE CRP_VRT_ID=? AND CRP_STG_ID=?",
     vatid, stage);
     } catch (Exception e) {
     β = 0;
     }
     // 作物根系深度h
     float h = 0;
     try {
     h = Length.get(stage).getCrpStgRtdend();
     } catch (Exception e) {
     h = 0;
     }
     // W下限
     float Wlimit = β * h;
     // low
     int low = 0;
     try {
     low = Db.queryInt(
     "SELECT CRPSM_LOW_ALM FROM T_IR_CRPSM_LIMIT WHERE CRP_VRT_ID=? AND CRP_STG_ID=?",
     vatid, stage);
     } catch (Exception e) {
     low = 0;
     }
     float Kci = 0;
     float Wlow = 0;
     float Kcini = 0;
     float Kcend = 0;
     int Dini = 0;
     int stageD = 0;
     try {
     // Wlow
     Wlow = low * h;
     // 时段初Kc值
     Kcini = Length.get(stage).getCrpKciniJyz();
     // 时段末Kc值
     Kcend = Length.get(stage).getCrpKcendJyz();
     // 时段初时间
     Dini = Length.get(stage).getCrpStgDini();
     // 生育阶段长度
     stageD = Length.get(stage).getCrpStgD();
     // 计算逐日作物系数
     Kci = Kcini + ((d - Dini) * (Kcend - Kcini) * (1 / stageD));
     } catch (Exception e) {
     Kci = 0;
     }
     double ETC = 0;
     double PEI = 0;
     long i1 = beginYear.getTime() + 86400000;
     String seed1 = sdf.format(new Date(i1));
     double et0 = 0;
     // 每日作物需水量
     try {
     et0 = Db.queryDouble(
     "SELECT IFNULL((SUM(DISTINCT QX_D_ET0)/COUNT(DISTINCT QX_D_ET0)),-1) FROM T_IR_QXDTCR_M WHERE QXZ_STN_ID =? AND QX_D_DATE >=? AND QX_D_DATE<?",
     QXZID, beginYear, seed1);

     if (et0 == -1) {
     et0 = Db.queryDouble(
     "SELECT sum(QX_D_ET0)/COUNT(QX_D_ET0) FROM t_ir_qxdtcr_m WHERE QXZ_STN_ID = ? AND MONTH(QX_D_DATE)=MONTH(?)",
     QXZID, beginYear);
     }
     } catch (Exception e) {
     et0 = 0;
     }
     ETC = Kci * et0;
     eta += ETC;
     double Pei = 0;
     // 有效降水量
     try {
     Pei = Db.queryDouble(
     "SELECT IFNULL((SUM(DISTINCT QX_D_P_CR)/COUNT(DISTINCT QX_D_P_CR)),-1) FROM T_IR_QXDTCR_M WHERE QXZ_STN_ID =? AND QX_D_DATE >=? AND QX_D_DATE<?",
     QXZID, beginYear, seed1);
     if (String.valueOf(Pei) == null) {
     Pei = Db.queryDouble(
     "SELECT sum(QX_D_P_CR)/COUNT(QX_D_p_cr) FROM t_ir_qxdtcr_m WHERE QXZ_STN_ID = ? AND MONTH(QX_D_DATE)=MONTH(?)",
     QXZID, beginYear);
     }
     } catch (Exception e) {
     Pei = 0;
     }
     PEI = Math.max(Pei, 0) * α;
     // 排水量
     double Wdateseeding = Psw + Wlimit;
     if (d == 1) {
     Dr = Math.max(Wdateseeding + PEI, 0);
     Ws = Wdateseeding + PEI - Dr - ETC;
     } else {
     Dr = Math.max(Ws + PEI, 0);
     Ws = Ws + PEI - Dr - ETC;
     }
     if (Ws >= Wlow) {
     continue;
     } else {
     if (dayseed <= 10) {// 上旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid).set("X_ET0", eta)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("MOIST", moist).set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 1)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1",fieldid,
     // vatid, mounthseed);
     // if(r>1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 1)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 10 && dayseed <= 20) {// 中旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 2)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 2)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20
     && dayseed <= 31
     && mounthseed != 2
     && (mounthseed == 1 || mounthseed == 3
     || mounthseed == 5 || mounthseed == 7
     || mounthseed == 8 || mounthseed == 10 || mounthseed == 12)) {// 非二月31天月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20
     && dayseed <= 30
     && mounthseed != 2
     && (mounthseed == 4 || mounthseed == 6
     || mounthseed == 9 || mounthseed == 11)) {// 非二月30天月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20 && dayseed <= 29 && mounthseed == 2
     && Integer.parseInt(seed[0]) % 4 == 0) {// 闰年二月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20 && dayseed <= 28 && mounthseed == 2
     && Integer.parseInt(seed[0]) % 4 != 0) {// 平年二月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =50",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 50).set("YEAR", yearP)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else {
     log.error("模型出错或时间不对");
     }
     }
     }
     d = 1;// 代表种植天数（初始值为1）
     Ws = 0;
     Dr = 0;
     K = 0;
     I = 0;
     Wi = 0;
     eta = 0;
     for (long i = dateseeding; i <= dateending; i = i + 86400000, d += 1) {
     // 获取水文年的种植日期的月份及收获日期的月份
     String seed[] = sdf.format(new Date(i)).split("-");
     int mounthseed = Integer.parseInt(seed[1]);
     int dayseed = Integer.parseInt(seed[2]);
     Date beginYear = sdf.parse(String.valueOf(yearF) + "-"
     + mounthseed + "-" + dayseed);
     int stage = Db
     .queryInt(
     "SELECT CRP_STG_ID FROM T_IR_CRP_STAGE WHERE CRP_VRT_ID=? AND CRP_STG_DINI<=? AND CRP_STG_DEND>=?",
     vatid, d, d);
     List<IrCrpStage> Length = IrCrpStage.dao.selectIrCrpStageName(
     vatid, fieldid, pid, cid, did);

     // 特殊需水量
     float Wsp = 0;
     try {
     Wsp = Db.queryFloat(
     "SELECT CRP_STGWT_SP FROM T_IR_CRP_DMDPARA WHERE FIELD_ID=? AND CRP_VRT_ID=? AND CRP_STG_ID=?",
     fieldid, vatid, stage);
     } catch (Exception e) {
     Wsp = 0;
     }
     // 土壤含水量下限
     int β = 0;
     try {
     β = Db.queryInt(
     "SELECT CRPSM_LOW_LIMIT FROM T_IR_CRPSM_LIMIT WHERE CRP_VRT_ID=? AND CRP_STG_ID=?",
     vatid, stage);
     } catch (Exception e) {
     β = 0;
     }
     // 作物根系深度h
     float h = 0;
     try {
     h = Length.get(stage).getCrpStgRtdend();
     } catch (Exception e) {
     h = 0;
     }
     // W下限
     float Wlimit = β * h;
     // low
     int low = 0;
     try {
     low = Db.queryInt(
     "SELECT CRPSM_LOW_ALM FROM T_IR_CRPSM_LIMIT WHERE CRP_VRT_ID=? AND CRP_STG_ID=?",
     vatid, stage);
     } catch (Exception e) {
     low = 0;
     }
     float Kci = 0;
     float Wlow = 0;
     float Kcini = 0;
     float Kcend = 0;
     int Dini = 0;
     int stageD = 0;
     try {
     // Wlow
     Wlow = low * h;
     // 时段初Kc值
     Kcini = Length.get(stage).getCrpKciniJyz();
     // 时段末Kc值
     Kcend = Length.get(stage).getCrpKcendJyz();
     // 时段初时间
     Dini = Length.get(stage).getCrpStgDini();
     // 生育阶段长度
     stageD = Length.get(stage).getCrpStgD();
     // 计算逐日作物系数
     Kci = Kcini + ((d - Dini) * (Kcend - Kcini) * (1 / stageD));
     } catch (Exception e) {
     Kci = 0;
     }
     double ETC = 0;
     double PEI = 0;
     long i1 = beginYear.getTime() + 86400000;
     String seed1 = sdf.format(new Date(i1));
     double et0 = 0;
     // 每日作物需水量
     try {
     et0 = Db.queryDouble(
     "SELECT IFNULL((SUM(DISTINCT QX_D_ET0)/COUNT(DISTINCT QX_D_ET0)),-1) FROM T_IR_QXDTCR_M WHERE QXZ_STN_ID =? AND QX_D_DATE >=? AND QX_D_DATE<?",
     QXZID, beginYear, seed1);

     if (et0 == -1) {
     et0 = Db.queryDouble(
     "SELECT sum(QX_D_ET0)/COUNT(QX_D_ET0) FROM t_ir_qxdtcr_m WHERE QXZ_STN_ID = ? AND MONTH(QX_D_DATE)=MONTH(?)",
     QXZID, beginYear);
     }
     } catch (Exception e) {
     et0 = 0;
     }
     ETC = Kci * et0;
     eta += ETC;
     double Pei = 0;
     // 有效降水量
     try {
     Pei = Db.queryDouble(
     "SELECT IFNULL((SUM(DISTINCT QX_D_P_CR)/COUNT(DISTINCT QX_D_P_CR)),-1) FROM T_IR_QXDTCR_M WHERE QXZ_STN_ID =? AND QX_D_DATE >=? AND QX_D_DATE<?",
     QXZID, beginYear, seed1);
     if (String.valueOf(Pei) == null) {
     Pei = Db.queryDouble(
     "SELECT sum(QX_D_P_CR)/COUNT(QX_D_p_cr) FROM t_ir_qxdtcr_m WHERE QXZ_STN_ID = ? AND MONTH(QX_D_DATE)=MONTH(?)",
     QXZID, beginYear);
     }
     } catch (Exception e) {
     Pei = 0;
     }
     PEI = Math.max(Pei, 0) * α;
     // 排水量
     double Wdateseeding = Psw + Wlimit;
     if (d == 1) {
     Dr = Math.max(Wdateseeding + PEI, 0);
     Ws = Wdateseeding + PEI - Dr - ETC;
     } else {
     Dr = Math.max(Ws + PEI, 0);
     Ws = Ws + PEI - Dr - ETC;
     }
     if (Ws >= Wlow) {
     continue;
     } else {
     if (dayseed <= 10) {// 上旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid).set("X_ET0", eta)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("MOIST", moist).set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 1)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1",fieldid,
     // vatid, mounthseed);
     // if(r>1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 1)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 10 && dayseed <= 20) {// 中旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 2)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 2)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20
     && dayseed <= 31
     && mounthseed != 2
     && (mounthseed == 1 || mounthseed == 3
     || mounthseed == 5 || mounthseed == 7
     || mounthseed == 8 || mounthseed == 10 || mounthseed == 12)) {// 非二月31天月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20
     && dayseed <= 30
     && mounthseed != 2
     && (mounthseed == 4 || mounthseed == 6
     || mounthseed == 9 || mounthseed == 11)) {// 非二月30天月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20 && dayseed <= 29 && mounthseed == 2
     && Integer.parseInt(seed[0]) % 4 == 0) {// 闰年二月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20 && dayseed <= 28 && mounthseed == 2
     && Integer.parseInt(seed[0]) % 4 != 0) {// 平年二月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =25",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 25).set("YEAR", yearF)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else {
     log.error("模型出错或时间不对");
     }
     }
     }
     d = 1;// 代表种植天数（初始值为1）
     Ws = 0;
     Dr = 0;
     K = 0;
     I = 0;
     Wi = 0;
     eta = 0;
     // 根据种植时间（播种日期-收获日期）取出Kc经验值表及时段初时段末天数及当前时间所处生育阶段长度
     for (long i = dateseeding; i <= dateending; i = i + 86400000, d += 1) {
     // 获取水文年的种植日期的月份及收获日期的月份
     String seed[] = sdf.format(new Date(i)).split("-");
     int mounthseed = Integer.parseInt(seed[1]);
     int dayseed = Integer.parseInt(seed[2]);
     Date beginYear = sdf.parse(String.valueOf(yearK) + "-"
     + mounthseed + "-" + dayseed);
     int stage = Db
     .queryInt(
     "SELECT CRP_STG_ID FROM T_IR_CRP_STAGE WHERE CRP_VRT_ID=? AND CRP_STG_DINI<=? AND CRP_STG_DEND>=?",
     vatid, d, d);
     List<IrCrpStage> Length = IrCrpStage.dao.selectIrCrpStageName(
     vatid, fieldid, pid, cid, did);

     // 特殊需水量
     float Wsp = Db
     .queryFloat(
     "SELECT CRP_STGWT_SP FROM T_IR_CRP_DMDPARA WHERE FIELD_ID=? AND CRP_VRT_ID=? AND CRP_STG_ID=?",
     fieldid, vatid, stage);
     // 土壤含水量下限
     int β = Db
     .queryInt(
     "SELECT CRPSM_LOW_LIMIT FROM T_IR_CRPSM_LIMIT WHERE CRP_VRT_ID=? AND CRP_STG_ID=?",
     vatid, stage);
     // 作物根系深度h
     float h = Length.get(stage).getCrpStgRtdend();
     // W下限
     float Wlimit = β * h;
     // low
     int low = Db
     .queryInt(
     "SELECT CRPSM_LOW_ALM FROM T_IR_CRPSM_LIMIT WHERE CRP_VRT_ID=? AND CRP_STG_ID=?",
     vatid, stage);
     // Wlow
     float Wlow = low * h;
     // 时段初Kc值
     float Kcini = Length.get(stage).getCrpKciniJyz();
     // 时段末Kc值
     float Kcend = Length.get(stage).getCrpKcendJyz();
     // 时段初时间
     int Dini = Length.get(stage).getCrpStgDini();
     // 生育阶段长度
     int stageD = Length.get(stage).getCrpStgD();
     // 计算逐日作物系数
     float Kci = Kcini
     + ((d - Dini) * (Kcend - Kcini) * (1 / stageD));
     double ETC = 0;
     double PEI = 0;
     long i1 = beginYear.getTime() + 86400000;
     String seed1 = sdf.format(new Date(i1));
     double et0 = 0;
     // 每日作物需水量
     try {
     et0 = Db.queryDouble(
     "SELECT IFNULL((SUM(DISTINCT QX_D_ET0)/COUNT(DISTINCT QX_D_ET0)),-1) FROM T_IR_QXDTCR_M WHERE QXZ_STN_ID =? AND QX_D_DATE >=? AND QX_D_DATE<?",
     QXZID, beginYear, seed1);

     if (et0 == -1) {
     et0 = Db.queryDouble(
     "SELECT sum(QX_D_ET0)/COUNT(QX_D_ET0) FROM t_ir_qxdtcr_m WHERE QXZ_STN_ID = ? AND MONTH(QX_D_DATE)=MONTH(?)",
     QXZID, beginYear);
     }
     } catch (Exception e) {
     et0 = 0;
     }
     ETC = Kci * et0;
     eta += ETC;
     double Pei = 0;
     // 有效降水量
     try {
     Pei = Db.queryDouble(
     "SELECT IFNULL((SUM(DISTINCT QX_D_P_CR)/COUNT(DISTINCT QX_D_P_CR)),-1) FROM T_IR_QXDTCR_M WHERE QXZ_STN_ID =? AND QX_D_DATE >=? AND QX_D_DATE<?",
     QXZID, beginYear, seed1);
     if (String.valueOf(Pei) == null) {
     Pei = Db.queryDouble(
     "SELECT sum(QX_D_P_CR)/COUNT(QX_D_p_cr) FROM t_ir_qxdtcr_m WHERE QXZ_STN_ID = ? AND MONTH(QX_D_DATE)=MONTH(?)",
     QXZID, beginYear);
     }
     } catch (Exception e) {
     Pei = 0;
     }
     PEI = Math.max(Pei, 0) * α;
     // 排水量
     double Wdateseeding = Psw + Wlimit;
     if (d == 1) {
     Dr = Math.max(Wdateseeding + PEI, 0);
     Ws = Wdateseeding + PEI - Dr - ETC;
     } else {
     Dr = Math.max(Ws + PEI, 0);
     Ws = Ws + PEI - Dr - ETC;
     }
     if (Ws >= Wlow) {
     continue;
     } else {
     if (dayseed <= 10) {// 上旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid).set("X_ET0", eta)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("MOIST", moist).set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 1)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1",fieldid,
     // vatid, mounthseed);
     // if(r>1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=1 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 1)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 10 && dayseed <= 20) {// 中旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 2)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=2 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 2)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20
     && dayseed <= 31
     && mounthseed != 2
     && (mounthseed == 1 || mounthseed == 3
     || mounthseed == 5 || mounthseed == 7
     || mounthseed == 8 || mounthseed == 10 || mounthseed == 12)) {// 非二月31天月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20
     && dayseed <= 30
     && mounthseed != 2
     && (mounthseed == 4 || mounthseed == 6
     || mounthseed == 9 || mounthseed == 11)) {// 非二月30天月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20 && dayseed <= 29 && mounthseed == 2
     && Integer.parseInt(seed[0]) % 4 == 0) {// 闰年二月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else if (dayseed > 20 && dayseed <= 28 && mounthseed == 2
     && Integer.parseInt(seed[0]) % 4 != 0) {// 平年二月下旬
     // 亏缺水量Wdfc
     double Wdfc = Math.max(Ws - Wlow, 0) + Wsp + Psw;
     // 灌溉水量I
     I = Wdfc + Wsp + Psw;
     int count = Db
     .queryInt(
     "SELECT COUNT(1) FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     if (count == 0) {
     Wi = 0;
     K = 0;
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     Record datas = new Record()
     .set("FIELD_ID", fieldid)
     .set("CRP_VRT_ID", vatid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.save("T_IR_SYSTEM", datas);
     log.info("存储数据库成功！");
     } else if (count == 1) {
     // int r =
     // Db.queryInt("SELECT X_irr_tim FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3",fieldid,
     // vatid, mounthseed);
     // if(r!=1){
     // Wi = 0;
     // K = 0;
     // }
     // 存储数据（按旬存储预测灌溉水量和灌溉次数）
     Wi = Wi + (I * A / 1000 / γ);
     K = K + 1;
     int id = Db
     .queryInt(
     "SELECT ID FROM T_IR_SYSTEM WHERE FIELD_ID=? AND CRP_VRT_ID=? AND MOUNTH=? AND DAY=3 AND YEAR_TYPE =75",
     fieldid, vatid, mounthseed);
     Record datas = new Record().set("id", id)
     .set("FIELD_ID", fieldid)
     .set("YEAR_TYPE", 75).set("YEAR", yearK)
     .set("X_ET0", eta).set("MOIST", moist)
     .set("efficiency", γ)
     .set("METHOD", function)
     .set("CRP_VRT_ID", vatid)
     .set("MOUNTH", mounthseed).set("DAY", 3)
     .set("X_IRR_VOL", Wi).set("X_IRR_TIM", K);
     Db.update("T_IR_SYSTEM", datas);
     log.info("更新数据库成功！");
     }
     } else {
     log.error("模型出错或时间不对");
     }
     }
     }
     log.info("统计灌溉制度模型成功！");
     } catch (Exception e) {
     e.printStackTrace();
     log.error("统计灌溉制度模型计算失败");
     }
     return map;
     }
     */
}
