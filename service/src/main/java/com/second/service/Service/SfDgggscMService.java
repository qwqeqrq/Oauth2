package com.second.service.Service;

import com.second.service.BaseDao.SfDgggscM;
import com.second.service.Dao.SfDgggscMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SfDgggscMService {
    @Autowired
    SfDgggscMMapper sfDgggscMMapper;

    public int coutOfRecord(String fieldid, String vatid, String date, Object IrriName) {
        int result = sfDgggscMMapper.RecordCount(fieldid, vatid, date, IrriName);
        return result;
    }

    public long SelectId(String fieldid, String vatid, String date, Object IrriName) {
        long id = sfDgggscMMapper.SelectId(fieldid, vatid, date, IrriName);
        return id;
    }

    public void insert(SfDgggscM sfDgggscM) {
        sfDgggscMMapper.insert(sfDgggscM);
    }

    public void Update(SfDgggscM sfDgggscM) {
        sfDgggscMMapper.updateByPrimaryKey(sfDgggscM);
    }

    public int countInAutomaticIrrigation(String fieldid, String vatid, Object irriName, int m, String dc) {
        int result = sfDgggscMMapper.countInAutomaticIrrigation(fieldid, vatid, irriName, m, dc);
        return result;
    }

    public long selectIdOnAutomaticIrrigation(String fieldid, String vatid, Object irriName, int m, String dc) {
        long result = sfDgggscMMapper.selectIdOnAutomaticIrrigation(fieldid, vatid, irriName, m, dc);
        return result;
    }

    public Date selectMinIrriGroupStartForAutomaticIrrigation(String fieldid, String vatid, String dc) {
        Date result = sfDgggscMMapper.selectMinIrriGroupStartForAutomaticIrrigation(fieldid, vatid, dc);
        return result;
    }

    public Date selectMaxIrriGroupStartForAutomaticIrrigation(String fieldid, String vatid, String dc) {
        Date result = sfDgggscMMapper.selectMaxIrriGroupStartForAutomaticIrrigation(fieldid, vatid, dc);
        return result;
    }
}
