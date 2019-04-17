package com.second.service.Service;

import com.second.service.Dao.BaseDataOfPearsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseDataPearsonService {

    @Autowired
    BaseDataOfPearsonMapper baseDataOfPearsonMapper;


    public float findPara(float frcStest, int i, float j) {
        float result = baseDataOfPearsonMapper.findPara(frcStest, i, j);
        return result;
    }
}
