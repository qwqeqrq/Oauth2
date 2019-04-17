package com.second.service.Dao;

import com.second.service.BaseDao.SfDgggscM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface SfDgggscMMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfDgggscM record);

    int insertSelective(SfDgggscM record);

    SfDgggscM selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SfDgggscM record);

    int updateByPrimaryKey(SfDgggscM record);

    int RecordCount(String fieldid, String vatid, String date, Object IrriName);

    long SelectId(String fieldid, String vatid, String date, Object IrriName);

    int countInAutomaticIrrigation(String fieldid, String vatid, Object irriName, int m, String dc);

    long selectIdOnAutomaticIrrigation(String fieldid, String vatid, Object irriName, int m, String dc);

    Date selectMinIrriGroupStartForAutomaticIrrigation(String fieldid, String vatid, String dc);

    Date selectMaxIrriGroupStartForAutomaticIrrigation(String fieldid, String vatid, String dc);
}