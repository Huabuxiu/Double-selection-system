package com.company.project.service.impl;

import com.company.project.dao.VoluntaryStateMapper;
import com.company.project.model.VoluntaryState;
import com.company.project.service.VoluntaryStateService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Huabuxiu on 2020/03/28.
 */
@Service
@Transactional
public class VoluntaryStateServiceImpl extends AbstractService<VoluntaryState> implements VoluntaryStateService {
    @Resource
    private VoluntaryStateMapper voluntaryStateMapper;

}
