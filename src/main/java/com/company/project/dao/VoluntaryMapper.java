package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.StudentVoluntary;
import com.company.project.model.Voluntary;
import com.company.project.model.VoluntaryVo;

import java.util.List;

public interface VoluntaryMapper extends Mapper<Voluntary> {
    public List<Voluntary> findUnhandle(int tid);

    public List<Voluntary> getfailStudent();

    public List<StudentVoluntary> getStudentVoluntaryList();
}