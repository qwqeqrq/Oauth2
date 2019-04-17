package com.second.service.Service;

import com.second.service.BaseDao.SfFlhllyM;
import com.second.service.Dao.SfFlhllyMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SfFlhllyMService {

    @Autowired
    SfFlhllyMMapper sfFlhllyMMapper;

    public List<SfFlhllyM> SelectFertType(String Fidk, String Fidn, String Fidp) {
        List<SfFlhllyM> result = sfFlhllyMMapper.SelectFertType(Fidk, Fidn, Fidp);
        return result;
    }

    public int selectCrpStgD() {
        int result = sfFlhllyMMapper.selectCrpStgD();
        return result;
    }
}
