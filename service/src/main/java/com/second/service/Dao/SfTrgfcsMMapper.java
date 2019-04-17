package com.second.service.Dao;

import com.second.service.BaseDao.SfTrgfcsM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfTrgfcsMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfTrgfcsM record);

    int insertSelective(SfTrgfcsM record);

    SfTrgfcsM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfTrgfcsM record);

    int updateByPrimaryKey(SfTrgfcsM record);

    String getParaOfField(String fieldid, String vatid);
}