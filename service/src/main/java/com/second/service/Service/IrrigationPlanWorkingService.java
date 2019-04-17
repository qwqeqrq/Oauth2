package com.second.service.Service;

import com.second.service.Dao.IrrigationPlanWorkingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrrigationPlanWorkingService {

    @Autowired
    private IrrigationPlanWorkingMapper irrigationPlanWorkingMapper;

    public String findPlanId(String fieldid) {
        String Planid = null;
        try {
            Planid = irrigationPlanWorkingMapper.planId(fieldid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Planid;
    }
}
