package com.second.service.Dao;

import com.second.service.BaseDao.SfZddgsfscM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfZddgsfscMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfZddgsfscM record);

    int insertSelective(SfZddgsfscM record);

    SfZddgsfscM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfZddgsfscM record);

    int updateByPrimaryKey(SfZddgsfscM record);

    int SelectId(String fieldid, String vatid, String date);

    int RecordCount(String fieldid, String vatid, String date);

    String selectFertPara(String fieldid, String vatid, int stgid);
}