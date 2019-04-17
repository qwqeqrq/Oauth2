package com.second.service.Math;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.second.service.Service.DeviceService;
import com.second.service.Service.IrrigationPlanWorkingService;
import com.second.service.Service.OptIrrigationGroupService;
import com.second.service.Service.RtudogrpsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FindVar {
    private static Logger logger = LoggerFactory.getLogger(FindVar.class);

    @Autowired
    IrrigationPlanWorkingService irrigationPlanWorkingService;
    @Autowired
    RtudogrpsService rtudogrpsService;
    @Autowired
    OptIrrigationGroupService optIrrigationGroupService;
    @Autowired
    DeviceService deviceService;

    // 查询轮灌组id及数目工具
    public  Map<String, Object> IrriNumAndId(String fieldid) {
        long timeStart = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 1,根据地块查询灌溉计划方案Id
            String planId = irrigationPlanWorkingService.findPlanId(fieldid);
            // 2,根据灌溉计划id查询轮灌组数
            int IrriNum = rtudogrpsService.countId(fieldid);
            // 3,根据地块id查询轮灌组名称及编号
            Map<Integer, String> IrriNameAndId = new HashMap<Integer, String>();
            for (int i = 1; i <= IrriNum; i++) {
                int IrriId = optIrrigationGroupService.IrriId(fieldid, planId, i);
                String IrriName = rtudogrpsService.irriNum(fieldid, IrriId);
                IrriNameAndId.put(IrriId, IrriName);
            }
            map.put("IrriNum", IrriNum);
            map.put("IrriName", IrriNameAndId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("查询耗时：" + String.valueOf(System.currentTimeMillis() - timeStart) + "毫秒");
        return map;
    }

    // 查询轮灌组对应阀门编号及阀门数工具,单阀门控制最大流量、单阀门控制最大面积
    public Map<String, Object> IrriValve(String fieldid) {
        long timeStart = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<Object, Object> ValueNumAndName = new HashMap<Object, Object>();
        Map<Object, Integer> ValueNum = new HashMap<Object, Integer>();// 阀门数量
        Map<Object, String> ValueName = new HashMap<Object, String>();// 阀门编号
        // 获取device表中储存的单阀门控制最大流量、单阀门控制最大面积的Json语句
        String JsonObject = deviceService.extension(fieldid);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Map mapJson = gson.fromJson(JsonObject, new TypeToken<Map<String, Object>>() {
        }.getType());
        String MapJsonOfIrriArea = mapJson.get("area").toString();
        float mapJsonOfIrriArea = Float.parseFloat(MapJsonOfIrriArea);// 单阀门控制面积
        String MapJsonOfIrriFlow = mapJson.get("designFlow").toString();
        int mapJsonOfIrriFlow = (int) Float.parseFloat(MapJsonOfIrriFlow);// 单阀门控制流量
        // 获取轮灌组内阀门数量及阀门编号
        Map<String, Object> IrriNumAndName = IrriNumAndId(fieldid);
        // 获取轮灌组数
        int IrriNum = (int) IrriNumAndName.get("IrriNum");
        int Valuenum = 0;
        String Valuename = "";
        for (int i = 1; i <= IrriNum; i++) {
            Valuenum = deviceService.Valuenum(fieldid, i);
            ValueNum.put(i + "Valuenum", Valuenum);
            Valuename = deviceService.Valuename(fieldid, i);
            ValueName.put(i + "Valuename", Valuename);
            ValueNumAndName.put(i + "ValueNum", ValueNum);
            ValueNumAndName.put(i + "ValueName", ValueName);
        }
        map.put("ValueNumAndName", ValueNumAndName);// 存储有关阀门的数据
        map.put("Area", mapJsonOfIrriArea);// 存储单阀门面积
        map.put("Flow", mapJsonOfIrriFlow);// 存储单阀门控制流量
        logger.info("查询耗时：" + String.valueOf(System.currentTimeMillis() - timeStart) + "毫秒");
        return map;
    }
}
