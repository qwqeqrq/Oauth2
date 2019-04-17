package com.second.service.Dao;

import com.second.service.BaseDao.Rtudogrps;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RtudogrpsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Rtudogrps record);

    int insertSelective(Rtudogrps record);

    Rtudogrps selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Rtudogrps record);

    int updateByPrimaryKey(Rtudogrps record);

    int CountOfRtudogrps(String fieldid);

    String IrriName(String fieldid, int IrriId);
}