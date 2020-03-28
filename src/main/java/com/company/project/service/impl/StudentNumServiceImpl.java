package com.company.project.service.impl;

import com.company.project.dao.StudentNumMapper;
import com.company.project.model.StudentNum;
import com.company.project.service.StudentNumService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Huabuxiu on 2020/03/28.
 */
@Service
@Transactional
public class StudentNumServiceImpl extends AbstractService<StudentNum> implements StudentNumService {
    @Resource
    private StudentNumMapper studentNumMapper;

}
