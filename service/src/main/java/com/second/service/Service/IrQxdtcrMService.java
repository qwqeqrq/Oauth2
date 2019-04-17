package com.second.service.Service;

import com.second.service.Dao.IrQxdtcrMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IrQxdtcrMService {

    @Autowired
    private IrQxdtcrMMapper irQxdtcrMMapper;

    public String fildQxzId(String fieldid) {
        String result = irQxdtcrMMapper.fildQxzId(fieldid);
        return result;
    }

    public String findQxdate(String qxzid, Date startTime, Date endTime) {
        String result = irQxdtcrMMapper.findQxdate(qxzid, startTime, endTime);
        return result;
    }

    public float findEt0InMonth(String qxzid, Date startTime) {
        float result = irQxdtcrMMapper.findEt0InMonth(qxzid, startTime);
        return result;
    }

    public float findCrInMonth(String qxzid, Date startTime) {
        float result = irQxdtcrMMapper.findCrInMonth(qxzid, startTime);
        return result;
    }

    public float findEt0InDate(String qxzid, Date startTime) {
        float result = irQxdtcrMMapper.findEt0InDate(qxzid, startTime);
        return result;
    }

    public float findCrInDate(String qxzid, Date startTime) {
        float result = irQxdtcrMMapper.findCrInDate(qxzid, startTime);
        return result;
    }


    public Date findMinQxDate(String qxzid) {
        Date result = irQxdtcrMMapper.findMinQxDate(qxzid);
        return result;
    }

    public Date findMaxQxDate(String qxzid) {
        Date result = irQxdtcrMMapper.findMaxQxDate(qxzid);
        return result;
    }
}
