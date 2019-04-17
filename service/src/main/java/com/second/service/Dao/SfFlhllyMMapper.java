package com.second.service.Dao;

import com.second.service.BaseDao.SfFlhllyM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SfFlhllyMMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfFlhllyM record);

    int insertSelective(SfFlhllyM record);

    SfFlhllyM selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SfFlhllyM record);

    int updateByPrimaryKey(SfFlhllyM record);

    List<SfFlhllyM> SelectFertType(String Fidk, String Fidn, String Fidp);

    int selectCrpStgD();
}