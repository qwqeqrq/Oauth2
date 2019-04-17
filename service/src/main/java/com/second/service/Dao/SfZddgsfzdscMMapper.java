package com.second.service.Dao;

import com.second.service.BaseDao.SfZddgsfzdscM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfZddgsfzdscMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfZddgsfzdscM record);

    int insertSelective(SfZddgsfzdscM record);

    SfZddgsfzdscM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfZddgsfzdscM record);

    int updateByPrimaryKey(SfZddgsfzdscM record);

    int RecordCount(String fieldid, String vatid, int i, String date);

    long SelectId(String fieldid, String vatid, int i, String date);

    String selectFertElement(String fieldid, String vatid);

    String selectBottomFert(String fieldid, String vatid);
}