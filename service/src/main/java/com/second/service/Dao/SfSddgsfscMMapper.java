package com.second.service.Dao;

import com.second.service.BaseDao.SfSddgsfscM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface SfSddgsfscMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfSddgsfscM record);

    int insertSelective(SfSddgsfscM record);

    SfSddgsfscM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfSddgsfscM record);

    int updateByPrimaryKey(SfSddgsfscM record);

    long SelectId(String fieldid, String vatid, String date, int crpStgid, int faddTime);

    int RecordCount(String fieldid, String vatid, String date, int crpStgid, int faddTime);

    int RecordCountForDripPlan(String fieldid, String vatid, int crpStgid, int faddTime);

    Date selectFaddTimeForUpdate(String fieldid, String vatid, long id);

    Date selectMaxFertDate(String fieldid, String vatid, Date Dfi, String Dc);

    Date selectMinFertDate(String fieldid, String vatid, Date Dfi, String Dc);

    long selectIdWithFadd_date(String fieldid, String vatid, Date faddDate, String crpEtarealDate);

    long selectIdForUpdate(String fieldid, String vatid, String dc, int i, int j);
}