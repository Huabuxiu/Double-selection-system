package com.company.project.service.impl;

import com.company.project.configurer.Log;
import com.company.project.configurer.WebMvcConfigurer;
import com.company.project.dao.VoluntaryMapper;
import com.company.project.model.*;
import com.company.project.service.*;
import com.company.project.core.AbstractService;
import com.company.project.util.LogAsPect;
import com.company.project.util.ProgessState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

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

    @Resource
    VoluntaryStateService voluntaryStateService;



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
            voluntaryVo.setState(ProgessState.getState(voluntary.getProgress(),voluntaryStateService.findById(voluntary.getVid()).getAlive()));
            voluntaryVo.setVoluntary_time(voluntary.getDate());
            voluntaryVo.setSchool(education.getSchool());
            voluntaryVo.setMajor(education.getMajor());
            voluntaryVo.setStudent_uid(student.getUid());
            voluntaryVo.setTeacher_uid(teacher.getUid());
            voList.add(voluntaryVo);
        }
        return voList;
    }


    public VoluntaryVo getVo(Voluntary voluntary,String states){
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
            if (states.equals("on")){
                voluntaryVo.setState(ProgessState.getState(voluntary.getProgress(),voluntaryStateService.findById(voluntary.getVid()).getAlive()));
            }else {
                voluntaryVo.setState(states);
            }
            voluntaryVo.setVoluntary_time(voluntary.getDate());
            voluntaryVo.setSchool(education.getSchool());
            voluntaryVo.setMajor(education.getMajor());
            voluntaryVo.setStudent_uid(student.getUid());
            voluntaryVo.setTeacher_uid(teacher.getUid());
         return voluntaryVo;
    }

    public List<VoluntaryVo> getEpmtyVoList(List<Student> list){
        List<VoluntaryVo> voList = new ArrayList<>();
        for (Student student:list){
            VoluntaryVo voluntaryVo = new VoluntaryVo();
            voluntaryVo.setSid(student.getSid());
            voluntaryVo.setStudent_name(student.getName());
            voluntaryVo.setState("未提交志愿");
            voList.add(voluntaryVo);
        }
        return voList;
    }

  public List<Voluntary>  getByState(List<VoluntaryState> states){
        List<Voluntary> voluntaries = new ArrayList<>();
      for (VoluntaryState state :
              states) {
          voluntaries.add(findById(state.getVid()));
      }
      return voluntaries;
  }

    @Override
    public List<Voluntary> getfailStudent() {
        return voluntaryMapper.getfailStudent();
    }

    @Override
    public List<StudentVoluntary> getStudentVoluntaryList() {
        return voluntaryMapper.getStudentVoluntaryList();
    }


    @Override
    public List<Voluntary> findUnhandle(int tid) {
        return voluntaryMapper.findUnhandle(tid);
    }

}
