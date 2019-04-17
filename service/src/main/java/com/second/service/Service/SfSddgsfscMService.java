package com.second.service.Service;

import com.second.service.BaseDao.SfSddgsfscM;
import com.second.service.Dao.SfSddgsfscMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SfSddgsfscMService {

    @Autowired
    SfSddgsfscMMapper sfSddgsfscMMapper;

    public int coutOfRecord(String fieldid, String vatid, String date, int crpStgid, int faddTime) {
        int result = sfSddgsfscMMapper.RecordCount(fieldid, vatid, date, crpStgid, faddTime);
        return result;
    }

    public int RecordCountForDripPlan(String fieldid, String vatid, int crpStgid, int faddTime) {
        int result = sfSddgsfscMMapper.RecordCountForDripPlan(fieldid, vatid, crpStgid, faddTime);
        return result;
    }

    public long SelectId(String fieldid, String vatid, String date, int crpStgid, int faddTime) {
        long id = sfSddgsfscMMapper.SelectId(fieldid, vatid, date, crpStgid, faddTime);
        return id;
    }

    public void insert(SfSddgsfscM sfSddgsfscM) {
        sfSddgsfscMMapper.insert(sfSddgsfscM);
    }

    public void Update(SfSddgsfscM sfSddgsfscM) {
        sfSddgsfscMMapper.updateByPrimaryKeySelective(sfSddgsfscM);
    }

    public Date selectFaddTimeForUpdate(String fieldid, String vatid, long id) {
        Date result = sfSddgsfscMMapper.selectFaddTimeForUpdate(fieldid, vatid, id);
        return result;
    }

    public Date selectMaxFertDate(String fieldid, String vatid, Date Dfi, String Dc) {
        Date result = sfSddgsfscMMapper.selectMaxFertDate(fieldid, vatid, Dfi, Dc);
        return result;
    }

    public Date selectMinFertDate(String fieldid, String vatid, Date Dfi, String Dc) {
        Date result = sfSddgsfscMMapper.selectMinFertDate(fieldid, vatid, Dfi, Dc);
        return result;
    }

    public long selectIdWithFadd_date(String fieldid, String vatid, Date FaddDate, String crpEtarealDate) {
        long result = sfSddgsfscMMapper.selectIdWithFadd_date(fieldid, vatid, FaddDate, crpEtarealDate);
        return result;
    }


    public long SelectIdForUpdate(String fieldid, String vatid, String dc, int i, int j) {
        long id = sfSddgsfscMMapper.selectIdForUpdate(fieldid, vatid, dc, i, j);
        return id;
    }
}
