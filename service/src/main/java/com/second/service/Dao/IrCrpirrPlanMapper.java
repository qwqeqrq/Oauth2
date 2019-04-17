package com.second.service.Dao;

import com.second.service.BaseDao.IrCrpirrPlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface IrCrpirrPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IrCrpirrPlan record);

    int insertSelective(IrCrpirrPlan record);

    IrCrpirrPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IrCrpirrPlan record);

    int updateByPrimaryKey(IrCrpirrPlan record);

    Date selectCrpirrDate(String fieldid, String vatid);

    float selectCrpirrPlandata(String fieldid, String vatid, Date dfi);

    Date selectcrpirrDateInFirsttime(String fieldid, String vatid);
}