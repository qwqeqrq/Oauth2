package com.second.service.Service;

import com.second.service.Dao.OptIrrigationGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptIrrigationGroupService {
    @Autowired
    private OptIrrigationGroupMapper optIrrigationGroupMapper;

    public int IrriId(String fieldid, String PlanId, int i) {
        int id = optIrrigationGroupMapper.IrriId(fieldid, PlanId, i);
        return id;
    }
}
