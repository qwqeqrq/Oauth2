package com.second.service.Dao;

import com.second.service.BaseDao.IrrigationPlanWorking;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IrrigationPlanWorkingMapper {
    int deleteByPrimaryKey(Long blockid);

    int insert(IrrigationPlanWorking record);

    int insertSelective(IrrigationPlanWorking record);

    IrrigationPlanWorking selectByPrimaryKey(Long blockid);

    int updateByPrimaryKeySelective(IrrigationPlanWorking record);

    int updateByPrimaryKey(IrrigationPlanWorking record);

    String planId(String fieldid);
}