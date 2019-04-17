package com.second.service.Service;

import com.second.service.Dao.IrCrpEtayldMaxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrCrpEtayldMaxService {
    @Autowired
    IrCrpEtayldMaxMapper irCrpEtayldMaxMapper;

    public float SearchYaim(String fieldid, String vatid, String date) {
        float result = irCrpEtayldMaxMapper.searchYaim(fieldid, vatid, date);
        return result;
    }
}
