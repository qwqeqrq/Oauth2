package com.second.service.Service;

import com.second.service.BaseDao.SfPgsfzdscM;
import com.second.service.Dao.SfPgsfzdscMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfPgsfzdscMService {

    @Autowired
    SfPgsfzdscMMapper sfPgsfzdscMMapper;

    public int coutOfRecord(String fieldid, String vatid, int i, String date) {
        int result = sfPgsfzdscMMapper.RecordCount(fieldid, vatid, i, date);
        return result;
    }

    public long SelectId(String fieldid, String vatid, int i, String date) {
        long id = sfPgsfzdscMMapper.SelectId(fieldid, vatid, i, date);
        return id;
    }

    public void insert(SfPgsfzdscM sfPgsfzdscM) {
        sfPgsfzdscMMapper.insert(sfPgsfzdscM);
    }

    public void Update(SfPgsfzdscM sfPgsfzdscM) {
        sfPgsfzdscMMapper.updateByPrimaryKey(sfPgsfzdscM);
    }
}
