package com.second.service.Service;

import com.second.service.Dao.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    public String extension(String fieldid) {
        String extensionName = deviceMapper.extension(fieldid);
        return extensionName;
    }

    public int Valuenum(String fieldid, int i) {
        int result = deviceMapper.Valuenum(fieldid, i);
        return result;
    }

    public String Valuename(String fieldid, int i) {
        String result = deviceMapper.Valuename(fieldid, i);
        return result;
    }
}
