package com.second.service.Dao;

import com.second.service.BaseDao.CropPlant;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CropPlantMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CropPlant record);

    int insertSelective(CropPlant record);

    CropPlant selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CropPlant record);

    int updateByPrimaryKey(CropPlant record);

    String starttime(String fieldid, String vatid);

    String selectArea(String fieldid);

    String endtime(String fieldid, String vatid);
}