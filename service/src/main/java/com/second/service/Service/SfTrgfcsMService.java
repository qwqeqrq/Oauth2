package com.second.service.Service;

import com.second.service.Dao.SfTrgfcsMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfTrgfcsMService {
    @Autowired
    SfTrgfcsMMapper sfTrgfcsMMapper;

    public String getParaOfField(String fieldid,String vatid) {
        String result = sfTrgfcsMMapper.getParaOfField(fieldid,vatid);
        return result;
    }
}
