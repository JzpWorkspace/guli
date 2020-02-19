package com.atguigu.guli.service.oss.controller;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api(description = "阿里云文件管理")
@CrossOrigin
@RequestMapping("/admin/oss/file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
    public R uploadFile(
       @ApiParam(name = "file",value = "上传文件",readOnly = true)
            @RequestParam("file") MultipartFile file,
       @ApiParam(name = "module",value = "上传模块",readOnly = true)
       @RequestParam("module") String module) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        String uploadUrl = fileService.upload(inputStream, module, originalFilename);

        return R.ok().message("文件上传成功").data("url",uploadUrl);
    }
}
