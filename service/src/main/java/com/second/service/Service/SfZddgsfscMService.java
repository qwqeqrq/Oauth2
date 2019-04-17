package com.second.service.Service;

import com.second.service.BaseDao.SfZddgsfscM;
import com.second.service.Dao.SfZddgsfscMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfZddgsfscMService {

    @Autowired
    SfZddgsfscMMapper sfZddgsfscMMapper;

    public int coutOfRecord(String fieldid, String vatid, String date) {
        int result = sfZddgsfscMMapper.RecordCount(fieldid, vatid, date);
        return result;
    }

    public int SelectId(String fieldid, String vatid, String date) {
        int id = sfZddgsfscMMapper.SelectId(fieldid, vatid, date);
        return id;
    }

    public void insert(SfZddgsfscM sfZddgsfscM) {
        sfZddgsfscMMapper.insert(sfZddgsfscM);
    }

    public void Update(SfZddgsfscM sfZddgsfscM) {
        sfZddgsfscMMapper.updateByPrimaryKey(sfZddgsfscM);
    }

    public String selectFertPara(String fieldid, String vatid, int stgid) {
        String result = sfZddgsfscMMapper.selectFertPara(fieldid, vatid, stgid);
        return result;
    }
}
