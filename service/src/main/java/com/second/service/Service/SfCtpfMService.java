package com.second.service.Service;

import com.second.service.Dao.SfCtpfMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfCtpfMService {

    @Autowired
    SfCtpfMMapper sfCtpfMMapper;

    public String getElementOfField(String fieldid) {
        String result = sfCtpfMMapper.getElementOfField(fieldid);
        return result;
    }
}
