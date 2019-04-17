package com.second.service.Dao;

import com.second.service.BaseDao.SfPgsfzdscM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfPgsfzdscMMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfPgsfzdscM record);

    int insertSelective(SfPgsfzdscM record);

    SfPgsfzdscM selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SfPgsfzdscM record);

    int updateByPrimaryKey(SfPgsfzdscM record);

    int RecordCount(String fieldid, String vatid, int i, String date);

    long SelectId(String fieldid, String vatid, int i, String date);
}