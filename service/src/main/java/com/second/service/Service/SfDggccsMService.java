package com.second.service.Service;

import com.second.service.Dao.SfDggccsMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfDggccsMService {
    @Autowired
    private SfDggccsMMapper sfDggccsMMapper;

    public String FertRatio(String fieldid, String vatid) {
        String result = sfDggccsMMapper.FertRatio(fieldid, vatid);
        return result;
    }

    public String FertPara(String fieldid, String vatid, int i) {
        String result = sfDggccsMMapper.FertPara(fieldid, vatid, i);
        return result;
    }

    public int selectDggccsMService(String fieldid, String vatid, int i) {
        int result = sfDggccsMMapper.selectFertTime(fieldid, vatid, i);
        return result;
    }

    public String selectFertInterMaxAndDripDailyWork(String fieldid, String vatid) {
        String result = sfDggccsMMapper.selectFertInterMaxAndDripDailyWork(fieldid, vatid);
        return result;
    }

    public String selectFertInterMaxAndDripDailyWorkIrriFertgroupDeepth(String fieldid, String vatid) {
        String result = sfDggccsMMapper.selectFertInterMaxAndDripDailyWorkIrriFertgroupDeepth(fieldid, vatid);
        return result;
    }
}
