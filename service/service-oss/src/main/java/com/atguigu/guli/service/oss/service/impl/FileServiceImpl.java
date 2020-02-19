package com.atguigu.guli.service.oss.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.guli.service.oss.service.FileService;
import com.atguigu.guli.service.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String upload(InputStream inputStream, String model, String fileName) {
        String endPoint = ossProperties.getEndPoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();
        //创建客户端
        OSS ossClient = new OSSClientBuilder().build(endPoint,keyid,keysecret);

        //上传文件的名称
        String putFileName = UUID.randomUUID().toString()+fileName;
        String fileExtention = fileName.substring(fileName.lastIndexOf("."));

        //上传文件方式
        String dateFolder = new DateTime().toString("yyyy/MM/dd");
        //上传
        //String objectName = model+"/"+dateFolder+"/"+putFileName+fileExtention;
        //性能优化
        String objectName = new StringBuffer()
                .append(model)
                .append("/")
                .append(dateFolder)
                .append("/")
                .append(putFileName)
                .toString();
        ossClient.putObject(bucketname,objectName,inputStream);
        //关闭
        ossClient.shutdown();
        //图片的url
        return new StringBuffer().append("https://")
                .append(bucketname)
                .append(".")
                .append(endPoint)
                .append("/")
                .append(objectName)
                .toString();
    }
}
