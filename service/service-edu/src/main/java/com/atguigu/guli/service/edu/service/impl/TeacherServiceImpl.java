package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.Course;
import com.atguigu.guli.service.edu.entity.Teacher;
import com.atguigu.guli.service.edu.entity.vo.TeacherQueryVo;
import com.atguigu.guli.service.edu.mapper.CourseMapper;
import com.atguigu.guli.service.edu.mapper.TeacherMapper;
import com.atguigu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Jzp
 * @since 2020-02-12
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public IPage<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo) {
        //首先建立查询条件
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        //没有条件就全查询
        if (teacherQueryVo == null) {
            return baseMapper.selectPage(pageParam, queryWrapper);
        }

        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String beginDate = teacherQueryVo.getBeginDate();
        String endDate = teacherQueryVo.getEndDate();
        //非空验证
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (level != null) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(beginDate)) {
            queryWrapper.ge("join_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            queryWrapper.le("join_date", endDate);
        }

        return baseMapper.selectPage(pageParam, queryWrapper);

    }

    @Override
    public List<Map<String, Object>> selectTeacherByLikeKey(String key) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        queryWrapper.likeRight("name", key);
        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);
        return list;
    }

    @Override
    public Map<String, Object> webSelectPage(Page<Teacher> pageParam) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        baseMapper.selectPage(pageParam, queryWrapper);

        List<Teacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     * 根据讲师id获取讲师详情页数据
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> selectTeacherInfoById(String id) {
        //获取讲师信息
        Teacher teacher = baseMapper.selectById(id);
        //根据讲师id获取讲师课程
        List<Course> courseList =  courseMapper.selectList(new QueryWrapper<Course>().eq("teacher_id", id));

        Map<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        map.put("courseList", courseList);
        return map;
    }
}