package com.second.service.Service;

import com.second.service.BaseDao.IrCrpStage;
import com.second.service.Dao.IrCrpStageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IrCrpStageService {

    @Autowired
    private IrCrpStageMapper irCrpStageMapper;

    public IrCrpStage find(long id) {
        return irCrpStageMapper.selectByPrimaryKey(id);
    }

    public List<IrCrpStage> selectIrCrpStageName(String vatid, String fieldid, String pid, String cid, String did) {
        List<IrCrpStage> IrCrpStagelist = new ArrayList<IrCrpStage>();

        if (!fieldid.equals(null)) {
            IrCrpStagelist = irCrpStageMapper.IrCrpStagelist(fieldid, vatid);
            if (IrCrpStagelist.size() >= 1) {
                return IrCrpStagelist;
            }
        }
        if (!did.equals(null)) {
            IrCrpStagelist = irCrpStageMapper.IrCrpStagelistWithoutParaOfFieldid(did, vatid);
            if (IrCrpStagelist.size() >= 1) {
                return IrCrpStagelist;
            }
        }
        if (!cid.equals(null)) {
            IrCrpStagelist = irCrpStageMapper.IrCrpStagelistWithoutParaOfFieldidAndDid(cid, vatid);
            if (IrCrpStagelist.size() >= 1) {
                return IrCrpStagelist;
            }
        }
        if (!pid.equals(null)) {
            IrCrpStagelist = irCrpStageMapper.IrCrpStagelistWithoutParaOfFieldidAndDidAndCid(pid, vatid);
            if (IrCrpStagelist.size() >= 1) {
                return IrCrpStagelist;
            }
        }
        return IrCrpStagelist;
    }

    public int stage(String vatid) {
        int result = irCrpStageMapper.getCrpStage(vatid);
        return result;
    }

    public int selectCrpStgIdByNow(long dg, String vatid) {
        int result = irCrpStageMapper.selectCrpStgIdByNow(dg,vatid);
        return result;
    }
}
