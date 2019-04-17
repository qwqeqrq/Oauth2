package com.second.service.Controller;

import com.second.service.BaseDao.IrCrpStage;
import com.second.service.Math.*;
import com.second.service.Service.IrCrpStageService;
import com.second.service.Service.IrrigationPlanWorkingService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApiOperation(value = "模型调用")
@RestController
@RequestMapping(value = "/second")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IrCrpStageService irCrpStageService;
    @Autowired
    private IrrigationPlanWorkingService irrigationPlanWorkingService;
    @Autowired
    private FindVar var;
    @Autowired
    private CropFertilizationSystem cropFertilizationSystem;
    @Autowired
    private FertCropNeedAnalysis fertCropNeedAnalysis;
    @Autowired
    private DripIrrigationFertilizationPlan dripIrrigationFertilizationPlan;
    @Autowired
    private AnalysisFertUseful analysisFertUseful;
    @Autowired
    private StaticIrrigation staticIrrigation;

    @ApiOperation(value = "测试系统")
    @RequestMapping(value = "/test")
    public String test() {
        IrCrpStage a = irCrpStageService.find(24L);
        return a.getCrpCname();
    }

    @ApiOperation(value = "查找轮灌组模型调用")
    @RequestMapping(value = "/FindVar")
    public Map<String, Object> FindVar() {
        Map<String, Object> result = var.IrriValve("37889691230797829");
        return result;
    }

    @ApiOperation(value = "设定追肥模型调用")
    @RequestMapping(value = "/CropFert")
    public Map<String, Float> CropFert() {
        Map<String, Float> result = cropFertilizationSystem.SettingUpManuring("37889691230797829", "19734574245814270", "5", "66", "767");
        return result;
    }

    @ApiOperation(value = "分析模型调用")
    @RequestMapping(value = "/FertAnalysis")
    public List<Map<String, Float>> FertAnalysis() {
        List<Map<String, Float>> result = fertCropNeedAnalysis.cropNeedAnalysis("37889691230797829", "19734574245814270", "5", "66", "767");
        return result;
    }

    @ApiOperation(value = "底肥模型调用")
    @RequestMapping(value = "/DripPlanOfBottom")
    public Map<String, Float> DripPlanOfBottom() throws ParseException {
        Map<String, Float> result = dripIrrigationFertilizationPlan.CalculationOfBottomFertilization("37889691230797829", "19734574245814270", "硝酸磷肥", "过磷酸钙", "氯化钾", 1);
        return result;
    }

    @ApiOperation(value = "追肥模型调用")
    @RequestMapping(value = "DripPlanOfFertilization")
    public Map<String, Float> DripPlanOfFertilization() throws ParseException {
        Map<String, Float> result = dripIrrigationFertilizationPlan.SettingUpManuring("37889691230797829", "19734574245814270", "04:20:15", "5", "767", "66", "硝酸磷肥", "过磷酸钙", "氯化钾", "1");
        return result;
    }

    @RequestMapping(value = "/AnalysisFertUseful/{Fn}")
    public Map<String, String> SprinkBottomFertAnalysisDi(@PathVariable("Fn") float Fn) {
        Map<String, String> result = analysisFertUseful.SprinkBottomFertAnalysisDi(Fn, 0.01f, 0.02f, "硝酸磷肥", "过磷酸钙", "氯化钾", "37889691230797829", "19734574245814270", "1");
        return result;
    }

    @ApiOperation(value = "水文年模型调用")
    @RequestMapping(value = "/staticIrrigation")
    public Map<String, Object> StaticIrrigation() {
        Map<String, Object> result = staticIrrigation.HydrologicalYearTypeCalculation("37889691230797829", "19734574245814270", 0);
        return result;
    }

    @RequestMapping("/failed")
    public Map<String, String> requestFailed() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "-1");
        map.put("msg", "请求错误");
        return map;
    }

    @ApiOperation(value = "消息测试")
    @RequestMapping(value = "/sendMq")
    @ResponseBody
    public String sendMq() {

        /**
         * 这里exchange、routingkey都叫testMq
         * **/
        Object message;
        for (int i = 0; i < 10; i++) {
            log.info("生产者：第{}条消息", i);
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            message = "第" + i + "条消息";
            rabbitTemplate.convertAndSend("testMq", "testMq", message, correlationId);
        }
        return "sending...";
    }
}
