package com.company.project.service.impl;

import com.company.project.dao.ResultsMapper;
import com.company.project.model.Results;
import com.company.project.service.ResultsService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Huabuxiu on 2020/03/20.
 */
@Service
@Transactional
public class ResultsServiceImpl extends AbstractService<Results> implements ResultsService {
    @Resource
    private ResultsMapper resultsMapper;

}
