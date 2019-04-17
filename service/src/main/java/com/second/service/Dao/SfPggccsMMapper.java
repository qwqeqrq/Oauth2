package com.second.service.Dao;

import com.second.service.BaseDao.SfPggccsM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfPggccsMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfPggccsM record);

    int insertSelective(SfPggccsM record);

    SfPggccsM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfPggccsM record);

    int updateByPrimaryKey(SfPggccsM record);

    String FertRatio(String fieldid, String vatid, int i);

    int FertCount(String fieldid, String vatid, int i);

    String ParaOfSpary(String fieldid, String vatid);
}