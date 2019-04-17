package com.second.service.Service;

import com.second.service.Dao.RtudogrpsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RtudogrpsService {
    @Autowired
    private RtudogrpsMapper rtudogrpsMapper;

    public int countId(String fieldid) {
        int count = rtudogrpsMapper.CountOfRtudogrps(fieldid);
        return count;
    }

    public String irriNum(String fieldid, int IrriId) {
        String number = rtudogrpsMapper.IrriName(fieldid, IrriId);
        return number;
    }
}
