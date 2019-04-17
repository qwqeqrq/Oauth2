package com.second.service.Dao;

import com.second.service.BaseDao.Device;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface DeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    String extension(String fieldid);

    int Valuenum(String fieldid, int i);

    String Valuename(String fieldid, int i);
}