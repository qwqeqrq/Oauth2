package com.second.service.Dao;

import com.second.service.BaseDao.IrField;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IrFieldMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IrField record);

    int insertSelective(IrField record);

    IrField selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IrField record);

    int updateByPrimaryKey(IrField record);
}