package com.second.service.Dao;

import com.second.service.BaseDao.SfCtpfM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SfCtpfMMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfCtpfM record);

    int insertSelective(SfCtpfM record);

    SfCtpfM selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SfCtpfM record);

    int updateByPrimaryKey(SfCtpfM record);

    String getElementOfField(String fieldid);
}