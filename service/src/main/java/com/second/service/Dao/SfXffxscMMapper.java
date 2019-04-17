package com.second.service.Dao;

import com.second.service.BaseDao.SfXffxscM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfXffxscMMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfXffxscM record);

    int insertSelective(SfXffxscM record);

    SfXffxscM selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SfXffxscM record);

    int updateByPrimaryKey(SfXffxscM record);

    String ParaOfFertNeed(String fieldid, String vatid, int i);

    int RecordCount(String fieldid, String vatid, int i, String date);

    long SelectId(String fieldid, String vatid, int i, String date);

}