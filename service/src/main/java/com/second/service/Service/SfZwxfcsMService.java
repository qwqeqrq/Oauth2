package com.second.service.Service;

import com.second.service.Dao.SfZwxfcsMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SfZwxfcsMService {

    @Autowired
    SfZwxfcsMMapper sfZwxfcsMMapper;

    public String FertNeedOfElement(String fieldid, String vatid) {
        String result = sfZwxfcsMMapper.FertNeedOfElement(fieldid, vatid);
        return result;
    }

    public String FertNeedOfElementEveryFertTime(String fieldid, String vatid, int i) {
        String result = sfZwxfcsMMapper.FertNeedOfElementEveryFertTime(fieldid, vatid, i);
        return result;
    }
}
