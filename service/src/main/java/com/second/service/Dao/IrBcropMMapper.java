package com.second.service.Dao;

import com.second.service.BaseDao.IrBcropM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IrBcropMMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IrBcropM record);

    int insertSelective(IrBcropM record);

    IrBcropM selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IrBcropM record);

    int updateByPrimaryKey(IrBcropM record);

    String FindcrpVrtSeeddateM(String vatid);

    String FindcrpVrtSeeddateD(String vatid);

    String FindcrpVrtHvsdateM(String vatid);

    String FindcrpVrtHvsdateD(String vatid);
}