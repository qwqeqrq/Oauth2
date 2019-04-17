package com.second.controller;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.second.controller.HttpClient.httpClient.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerApplicationTests {

    @Test
    public void contextLoads() throws IOException, ClientProtocolException {
        String url = "http://localhost:8082/httpService/sendPostDataByMap";
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "wyj");
        map.put("city", "南京");
        String body = sendPostDataByMap(url, map, "utf-8",null);
        System.out.println("响应结果：" + body);
    }

    @Test
    public void testSendPostDataByJson() throws ClientProtocolException, IOException {
        String url = "http://localhost:8082/httpService/sendPostDataByJson";
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "wyj");
        map.put("city", "南京");
        String body = sendPostDataByJson(url, JSON.toJSONString(map), "utf-8");
        System.out.println("响应结果：" + body);
    }

    @Test
    public void testSendGetData() throws ClientProtocolException, IOException {
        String url = "http://localhost:8082/httpService/sendGetData?name=wyj&city=南京";
        String body = sendGetData(url, "utf-8");
        System.out.println("响应结果：" + body);
    }

    @Test
    public void testSendGetData2() throws ClientProtocolException, IOException {
        String url = "https://www.sogou.com/tx?ie=utf-8&pid=sogou-site-1f2b8183cd1e469a&query=java.lang.ClassNotFoundException%3A%20Cannot%20find%20class%3A";
        String body = sendGetData(url, "utf-8");
        System.out.println("响应结果：" + body);
    }

    @Test
    public void testForMath() throws ClientProtocolException, IOException {
        String url = "http://localhost:8081/test";
        String body = sendGetData(url, "UTF-8");
        System.out.println(body);
    }
}

