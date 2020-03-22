package com.company.project.service.impl;

import com.company.project.dao.VoluntaryMapper;
import com.company.project.model.Voluntary;
import com.company.project.service.VoluntaryService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Huabuxiu on 2020/03/20.
 */
@Service
@Transactional
public class VoluntaryServiceImpl extends AbstractService<Voluntary> implements VoluntaryService {
    @Resource
    private VoluntaryMapper voluntaryMapper;

}
