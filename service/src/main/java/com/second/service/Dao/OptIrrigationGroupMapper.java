package com.second.service.Dao;

import com.second.service.BaseDao.OptIrrigationGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OptIrrigationGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OptIrrigationGroup record);

    int insertSelective(OptIrrigationGroup record);

    OptIrrigationGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OptIrrigationGroup record);

    int updateByPrimaryKey(OptIrrigationGroup record);

    int IrriId(String fieldid, String PlanId, int i);
}