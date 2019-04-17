package com.second.service.Dao;

import com.second.service.BaseDao.IrCrpStage;
import com.second.service.BaseDao.IrCrpStageWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IrCrpStageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IrCrpStageWithBLOBs record);

    int insertSelective(IrCrpStageWithBLOBs record);

    IrCrpStageWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IrCrpStageWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(IrCrpStageWithBLOBs record);

    int updateByPrimaryKey(IrCrpStage record);

    List<IrCrpStage> IrCrpStagelist(String fieldid, String vatid);

    List<IrCrpStage> IrCrpStagelistWithoutParaOfFieldid(String did, String vatid);

    List<IrCrpStage> IrCrpStagelistWithoutParaOfFieldidAndDid(String cid, String vatid);

    List<IrCrpStage> IrCrpStagelistWithoutParaOfFieldidAndDidAndCid(String pid, String vatid);

    int getCrpStage(String vatid);

    int selectCrpStgIdByNow(long dg, String vatid);
}