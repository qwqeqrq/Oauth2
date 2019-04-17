package com.second.service.Service;

import com.second.service.BaseDao.SfXffxscM;
import com.second.service.Dao.SfXffxscMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfXffxscMService {

    @Autowired
    SfXffxscMMapper sfXffxscMMapper;

    public String ParaOfFert(String fieldid, String vatid, int i) {
        String result = sfXffxscMMapper.ParaOfFertNeed(fieldid, vatid, i);
        return result;
    }

    public int coutOfRecord(String fieldid, String vatid, int i, String date) {
        int result = sfXffxscMMapper.RecordCount(fieldid, vatid, i, date);
        return result;
    }

    public long SelectId(String fieldid, String vatid, int i, String date) {
        long id = sfXffxscMMapper.SelectId(fieldid, vatid, i, date);
        return id;
    }

    public void insert(SfXffxscM sfXffxscM) {
        sfXffxscMMapper.insert(sfXffxscM);
    }

    public void Update(SfXffxscM sfXffxscM) {
        sfXffxscMMapper.updateByPrimaryKey(sfXffxscM);
    }
}
