package com.second.service.Service;

import com.second.service.Dao.IrCrpirrPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IrCrpirrPlanService {

    @Autowired
    private IrCrpirrPlanMapper irCrpirrPlanMapper;

    public Date crpirrDate(String fieldid, String vatid) {
        Date result = irCrpirrPlanMapper.selectCrpirrDate(fieldid, vatid);
        return result;
    }

    public float selectCrpirrPlandata(String fieldid, String vatid, Date dfi) {
        float result = irCrpirrPlanMapper.selectCrpirrPlandata(fieldid, vatid,dfi);
        return result;
    }

    public Date selectcrpirrDateInFirsttime(String fieldid, String vatid) {
        Date result = irCrpirrPlanMapper.selectcrpirrDateInFirsttime(fieldid, vatid);
        return result;
    }
}
