package com.second.service.Service;

import com.second.service.BaseDao.SfZddgsfzdscM;
import com.second.service.Dao.SfZddgsfzdscMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfZddgsfzdscMService {

    @Autowired
    SfZddgsfzdscMMapper sfZddgsfzdscMMapper;

    public int coutOfRecord(String fieldid, String vatid, int i, String date) {
        int result = sfZddgsfzdscMMapper.RecordCount(fieldid, vatid, i, date);
        return result;
    }

    public long SelectId(String fieldid, String vatid, int i, String date) {
        long id = sfZddgsfzdscMMapper.SelectId(fieldid, vatid, i, date);
        return id;
    }

    public void insert(SfZddgsfzdscM sfZddgsfzdscM) {
        sfZddgsfzdscMMapper.insert(sfZddgsfzdscM);
    }

    public void Update(SfZddgsfzdscM sfZddgsfzdscM) {
        sfZddgsfzdscMMapper.updateByPrimaryKey(sfZddgsfzdscM);
    }

    public String selectFertElement(String fieldid, String vatid) {
        String result = sfZddgsfzdscMMapper.selectFertElement(fieldid, vatid);
        return result;
    }

    public String selectBottomFert(String fieldid, String vatid) {
        String result = sfZddgsfzdscMMapper.selectBottomFert(fieldid, vatid);
        return result;
    }
}
