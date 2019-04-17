package com.second.service.Dao;

import com.second.service.BaseDao.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);
}