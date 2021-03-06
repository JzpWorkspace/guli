package com.atguigu.guli.service.edu.controller.api;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.vo.ChapterVo;
import com.atguigu.guli.service.edu.entity.vo.WebCourseVo;
import com.atguigu.guli.service.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(description="大纲")
@RestController
@RequestMapping("/api/edu/chapter")
public class ApiChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "根据课程id查询课程信息")
    @GetMapping("get/{courseId}")
    public R getByCourseId(
            @ApiParam(name = "courseId", value = "课程Id", required = true)
            @PathVariable String courseId){

        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return  R.ok().data("chapterVoList",chapterVoList);
    }
}
