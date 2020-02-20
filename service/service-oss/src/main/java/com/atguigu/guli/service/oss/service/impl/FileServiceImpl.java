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
        String bucketName = ossProperties.getBucketname();
        //创建客户端
        OSS ossClient = new OSSClientBuilder().build(endPoint,keyid,keysecret);

        //上传文件的名称
        String putFileName = UUID.randomUUID().toString().replace("-","");
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
                .append(fileExtention)
                .toString();
        ossClient.putObject(bucketName,objectName,inputStream);
        //关闭
        ossClient.shutdown();
        //图片的url
        return new StringBuffer().append("https://")
                .append(bucketName)
                .append(".")
                .append(endPoint)
                .append("/")
                .append(objectName)
                .toString();
    }

    @Override
    public void removeFile(String url) {
        //获取配置文件
        String endpoint = ossProperties.getEndPoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketName = ossProperties.getBucketname();
        //创建客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint,keyid,keysecret);

        //删除文件地址
        //https://inline-teach-file.oss-cn-beijing.aliyuncs.com/avatar/2020/02/19/a79c541c-b688-4797-b9da-0ba4e5c00604default1%20%282%29.jpg
        String host = "https://" + bucketName + "." + endpoint + "/";
        String objectName = url.substring(host.length());
        System.out.println(objectName);

        //执行删除
        ossClient.deleteObject(bucketName,objectName);

        //关闭客户端
        ossClient.shutdown();
    }
}
