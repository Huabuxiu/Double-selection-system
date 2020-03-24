package com.company.project.service.impl;

import com.company.project.dao.ProjectMapper;
import com.company.project.model.Project;
import com.company.project.service.ProjectService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Huabuxiu on 2020/03/24.
 */
@Service
@Transactional
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

}
