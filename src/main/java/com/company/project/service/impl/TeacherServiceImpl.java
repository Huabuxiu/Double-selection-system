package com.company.project.service.impl;

import com.company.project.dao.TeacherMapper;
import com.company.project.model.Teacher;
import com.company.project.service.TeacherService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Huabuxiu on 2020/03/28.
 */
@Service
@Transactional
public class TeacherServiceImpl extends AbstractService<Teacher> implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

}
