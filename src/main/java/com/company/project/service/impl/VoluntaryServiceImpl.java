package com.company.project.service.impl;

import com.company.project.dao.VoluntaryMapper;
import com.company.project.model.*;
import com.company.project.service.EducationService;
import com.company.project.service.ResultsService;
import com.company.project.service.StudentService;
import com.company.project.service.VoluntaryService;
import com.company.project.core.AbstractService;
import com.company.project.util.ProgessState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Huabuxiu on 2020/03/20.
 */
@Service
@Transactional
public class VoluntaryServiceImpl extends AbstractService<Voluntary> implements VoluntaryService {
    @Resource
    private VoluntaryMapper voluntaryMapper;

    @Resource
    StudentService studentService;

    @Resource
    ResultsService resultsService;

    @Resource
    TeacherServiceImpl teacherService;

    @Resource
    EducationService educationService;


    public List<VoluntaryVo> getVoList(List<Voluntary> list){
        List<VoluntaryVo> voList = new ArrayList<>();
        for (Voluntary voluntary:list){
            VoluntaryVo voluntaryVo = new VoluntaryVo();
            Student student = studentService.findById(voluntary.getSid());
            Teacher teacher = teacherService.findById(voluntary.getTid());
            Results results = resultsService.findBy("sid",student.getSid());
            Education education = educationService.findBy("sid",student.getSid());
            voluntaryVo.setSid(voluntary.getSid());
            voluntaryVo.setStudent_name(student.getName());
            voluntaryVo.setTid(voluntary.getTid());
            voluntaryVo.setTeacher_name(teacher.getName());
            voluntaryVo.setLevel(voluntary.getLevel());
            voluntaryVo.setTotal_score(results.getTotalScore());
            voluntaryVo.setExam_type(results.getExamType());
            voluntaryVo.setState(ProgessState.getState(voluntary.getProgress()));
            voluntaryVo.setVoluntary_time(voluntary.getDate());
            voluntaryVo.setSchool(education.getSchool());
            voluntaryVo.setMajor(education.getMajor());
            voList.add(voluntaryVo);
        }
        return voList;
    }

}
