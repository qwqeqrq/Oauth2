package com.second.service.Dao;

import com.second.service.BaseDao.IrCrpEtayldMax;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IrCrpEtayldMaxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IrCrpEtayldMax record);

    int insertSelective(IrCrpEtayldMax record);

    IrCrpEtayldMax selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IrCrpEtayldMax record);

    int updateByPrimaryKey(IrCrpEtayldMax record);

    float searchYaim(String fieldid, String vatid, String date);

}