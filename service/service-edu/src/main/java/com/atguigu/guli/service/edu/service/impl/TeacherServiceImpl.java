package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.service.edu.entity.Teacher;
import com.atguigu.guli.service.edu.entity.vo.TeacherQueryVo;
import com.atguigu.guli.service.edu.mapper.TeacherMapper;
import com.atguigu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo) {
        //首先建立查询条件
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        //没有条件就全查询
        if (teacherQueryVo == null){
            return baseMapper.selectPage(pageParam,queryWrapper);
        }

        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String beginDate = teacherQueryVo.getBeginDate();
        String endDate = teacherQueryVo.getEndDate();
        //非空验证
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);
        }
        if (level != null) {
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(beginDate)){
            queryWrapper.ge("join_date",beginDate);
        }
        if (!StringUtils.isEmpty(endDate)){
            queryWrapper.le("join_date",endDate);
        }

        return baseMapper.selectPage(pageParam,queryWrapper);

    }
}
