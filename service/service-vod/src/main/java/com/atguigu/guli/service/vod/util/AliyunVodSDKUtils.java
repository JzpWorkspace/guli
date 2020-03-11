package com.atguigu.guli.service.vod.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunVodSDKUtils {
    public static DefaultAcsClient initVodClient(String keyid, String keysecret) throws ClientException {
        String regionId = "cn-beijing";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, keyid, keysecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
