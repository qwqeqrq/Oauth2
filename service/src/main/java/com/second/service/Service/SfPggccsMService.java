package com.second.service.Service;

import com.second.service.Dao.SfPggccsMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfPggccsMService {
    @Autowired
    SfPggccsMMapper sfPggccsMMapper;

    public String ParaOfSpary(String fieldid, String vatid) {
        String relust = sfPggccsMMapper.ParaOfSpary(fieldid, vatid);
        return relust;
    }

    public int FertNum(String fieldid, String vatid, int i) {
        int result = sfPggccsMMapper.FertCount(fieldid, vatid, i);
        return result;
    }

    public String FertRatio(String fieldid, String vatid, int i) {
        String result = sfPggccsMMapper.FertRatio(fieldid, vatid, i);
        return result;
    }
}
