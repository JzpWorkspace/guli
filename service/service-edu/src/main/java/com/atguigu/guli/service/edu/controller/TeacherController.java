package com.atguigu.guli.service.edu.controller;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.Teacher;
import com.atguigu.guli.service.edu.entity.vo.TeacherQueryVo;
import com.atguigu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Jzp
 * @since 2020-02-12
 */
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "根据左姓名查询讲师",notes = "根据左姓名查询讲师")
    @GetMapping("list/name/{key}")
    public R selectListNameByKey(
            @ApiParam(name = "key",value = "左姓名查询",required = true)
            @PathVariable String key){
        List<Map<String, Object>> nameList = this.teacherService.selectTeacherByLikeKey(key);
        return R.ok().data("nameList",nameList);
    }

    @ApiOperation(value = "所有讲师的列表")
    @GetMapping("list")
    public R teacherList() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items",list).message("获取讲师列表成功！");
    }

    @ApiOperation(value = "根据id批量删除讲师",notes = "根据ID批量删除讲师，逻辑删除并非物理删除！")
    @DeleteMapping("removeBath")
    public R deleteTeacherById(
            @ApiParam(name = "idList",value = "讲师ID列表",required = true)
            @RequestBody List<String> idList) {
        teacherService.removeByIds(idList);
        return R.ok().message("讲师批量删除成功！");
    }

    @ApiOperation(value = "根据id删除讲师",notes = "根据ID删除讲师，逻辑删除并非物理删除！")
    @DeleteMapping("remove/{id}")
    public R deleteTeacherById(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable String id) {
        teacherService.removeById(id);
        return R.ok().message("讲师删除成功！");
    }


    @ApiOperation(value = "实现分页查询",notes = "实现传入分页数据实现分页")
    @GetMapping("list/{page}/{limit}")
    public R index(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页的条数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQueryVo",value = "查询条件对象",required = false)
            TeacherQueryVo teacherQueryVo
    ){
        Page<Teacher> pageParam = new Page<>(page, limit);
        IPage<Teacher> pageModel = this.teacherService.selectPage(pageParam, teacherQueryVo);
        List<Teacher> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "添加讲师",notes = "页面传来的数据json数据进行读取和封装插入数据库")
    @PostMapping("save")
    public R insert(
            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody Teacher teacher
    ){
        this.teacherService.save(teacher);
        return R.ok().message("新增讲师成功！");
    }

    @ApiOperation(value = "查询讲师",notes = "根据ID查询讲师")
    @GetMapping("get/{id}")
    public R queryTeacherById(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable Long id){

        Teacher teacherResult = this.teacherService.getById(id);
        return R.ok().data("item",teacherResult);
    }

    @ApiOperation(value = "更新讲师",notes = "根据ID更新讲师")
    @PostMapping("update")
    public R updateTeacherById(
            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody Teacher teacher){
        this.teacherService.updateById(teacher);
        return R.ok().message("更新讲师成功！");
    }

}

