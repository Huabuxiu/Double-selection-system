package com.company.project.service.impl;

import com.company.project.dao.EducationMapper;
import com.company.project.model.Education;
import com.company.project.service.EducationService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Huabuxiu on 2020/03/20.
 */
@Service
@Transactional
public class EducationServiceImpl extends AbstractService<Education> implements EducationService {
    @Resource
    private EducationMapper educationMapper;

}
