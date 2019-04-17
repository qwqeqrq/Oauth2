package com.second.service.Dao;

import com.second.service.BaseDao.IrQxdtcrM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface IrQxdtcrMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IrQxdtcrM record);

    int insertSelective(IrQxdtcrM record);

    IrQxdtcrM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IrQxdtcrM record);

    int updateByPrimaryKey(IrQxdtcrM record);

    String fildQxzId(String fieldid);

    String findQxdate(String qxzid, Date startTime, Date endTime);

    float findEt0InMonth(String qxzid, Date time);

    float findCrInMonth(String qxzid, Date time);

    float findEt0InDate(String qxzid, Date time);

    float findCrInDate(String qxzid, Date time);

    Date findMinQxDate(String qxzid);

    Date findMaxQxDate(String qxzid);
}