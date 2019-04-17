package com.second.service.Service;

import com.second.service.Dao.CropPlantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CropPlantService {

    @Autowired
    CropPlantMapper cropPlantMapper;

    public String SelectStartTime(String fieldid, String vatid) {
        String result = cropPlantMapper.starttime(fieldid, vatid);
        return result;
    }

    public String selectArea(String fieldid) {
        String result = cropPlantMapper.selectArea(fieldid);
        return result;
    }
    public String SelectEndTime(String fieldid, String vatid) {
        String result = cropPlantMapper.endtime(fieldid, vatid);
        return result;
    }
}
