package com.company.project.service;
import com.company.project.model.*;
import com.company.project.core.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;


/**
 * Created by Huabuxiu on 2020/03/20.
 */
public interface VoluntaryService extends Service<Voluntary> {

    List<VoluntaryVo> getVoList(List<Voluntary> list);

    List<Voluntary> findUnhandle( int tid);

    List<VoluntaryVo> getEpmtyVoList(List<Student> list);

    public List<Voluntary>  getByState(List<VoluntaryState> states);

    List<Voluntary> getfailStudent();

    public VoluntaryVo getVo(Voluntary voluntary,String states);

    public List<StudentVoluntary> getStudentVoluntaryList();
}
