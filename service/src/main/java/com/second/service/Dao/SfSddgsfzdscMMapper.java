package com.second.service.Dao;

import com.second.service.BaseDao.SfSddgsfzdscM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfSddgsfzdscMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfSddgsfzdscM record);

    int insertSelective(SfSddgsfzdscM record);

    SfSddgsfzdscM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfSddgsfzdscM record);

    int updateByPrimaryKey(SfSddgsfzdscM record);

    int RecordCount(String fieldid, String vatid, int i, String date);

    long SelectId(String fieldid, String vatid, int i, String date);

    String FertPlan(String fieldid, String vatid, int i, String date);

    String selectBottomFert(String fieldid, String vatid);
}