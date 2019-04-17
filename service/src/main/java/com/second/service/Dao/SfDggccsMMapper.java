package com.second.service.Dao;

import com.second.service.BaseDao.SfDggccsM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfDggccsMMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfDggccsM record);

    int insertSelective(SfDggccsM record);

    SfDggccsM selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SfDggccsM record);

    int updateByPrimaryKey(SfDggccsM record);

    String FertRatio(String fieldid, String vatid);

    String FertPara(String fieldid, String vatid, int i);

    int selectFertTime(String fieldid, String vatid, int i);

    String selectFertInterMaxAndDripDailyWork(String fieldid, String vatid);

    String selectFertInterMaxAndDripDailyWorkIrriFertgroupDeepth(String fieldid, String vatid);
}