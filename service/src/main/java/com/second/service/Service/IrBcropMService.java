package com.second.service.Service;

import com.second.service.Dao.IrBcropMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrBcropMService {
    @Autowired
    private IrBcropMMapper irBcropMMapper;

    public String FindcrpVrtSeeddateM(String vatid) {
        String result = irBcropMMapper.FindcrpVrtSeeddateM(vatid);
        return result;
    }

    public String FindcrpVrtSeeddateD(String vatid) {
        String result = irBcropMMapper.FindcrpVrtSeeddateD(vatid);
        return result;
    }

    public String FindcrpVrtHvsdateM(String vatid) {
        String result = irBcropMMapper.FindcrpVrtHvsdateM(vatid);
        return result;
    }

    public String FindcrpVrtHvsdateD(String vatid) {
        String result = irBcropMMapper.FindcrpVrtHvsdateD(vatid);
        return result;
    }


}
