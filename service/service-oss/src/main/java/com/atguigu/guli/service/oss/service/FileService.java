package com.atguigu.guli.service.oss.service;

import java.io.InputStream;


public interface FileService {
    String upload(InputStream inputStream,String model,String fileName);
    void removeFile(String url);
}
