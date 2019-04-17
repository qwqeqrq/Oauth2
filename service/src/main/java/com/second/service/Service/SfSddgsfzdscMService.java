package com.second.service.Service;

import com.second.service.BaseDao.SfSddgsfzdscM;
import com.second.service.Dao.SfSddgsfzdscMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfSddgsfzdscMService {

    @Autowired
    SfSddgsfzdscMMapper sfSddgsfzdscMMapper;

    public int coutOfRecord(String fieldid, String vatid, int i, String date) {
        int result = sfSddgsfzdscMMapper.RecordCount(fieldid, vatid, i, date);
        return result;
    }

    public long SelectId(String fieldid, String vatid, int i, String date) {
        long id = sfSddgsfzdscMMapper.SelectId(fieldid, vatid, i, date);
        return id;
    }

    public void insert(SfSddgsfzdscM sfSddgsfscM) {
        sfSddgsfzdscMMapper.insert(sfSddgsfscM);
    }

    public void Update(SfSddgsfzdscM sfSddgsfscM) {
        sfSddgsfzdscMMapper.updateByPrimaryKey(sfSddgsfscM);
    }

    public String FertPlan(String fieldid, String vatid, int i, String date) {
        String result = sfSddgsfzdscMMapper.FertPlan(fieldid, vatid, i, date);
        return result;
    }

    public String selectBottomFert(String fieldid, String vatid) {
        String result = sfSddgsfzdscMMapper.selectBottomFert(fieldid, vatid);
        return result;
    }
}
