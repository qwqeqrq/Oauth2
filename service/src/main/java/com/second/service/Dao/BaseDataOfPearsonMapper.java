package com.second.service.Dao;

import com.second.service.BaseDao.BaseDataOfPearson;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BaseDataOfPearsonMapper {
    int deleteByPrimaryKey(Byte id);

    int insert(BaseDataOfPearson record);

    int insertSelective(BaseDataOfPearson record);

    BaseDataOfPearson selectByPrimaryKey(Byte id);

    int updateByPrimaryKeySelective(BaseDataOfPearson record);

    int updateByPrimaryKey(BaseDataOfPearson record);

    float findPara(float frcStest, int i,float j);
}