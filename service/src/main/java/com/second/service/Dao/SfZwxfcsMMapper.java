package com.second.service.Dao;

import com.second.service.BaseDao.SfZwxfcsM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfZwxfcsMMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfZwxfcsM record);

    int insertSelective(SfZwxfcsM record);

    SfZwxfcsM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfZwxfcsM record);

    int updateByPrimaryKey(SfZwxfcsM record);

    String FertNeedOfElement(String fieldid, String vatid);

    String FertNeedOfElementEveryFertTime(String fieldid, String vatid, int i);
}