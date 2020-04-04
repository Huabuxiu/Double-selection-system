package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Student;

import javax.validation.constraints.Null;
import java.util.List;

public interface StudentMapper extends Mapper<Student> {

  public List<Student> dontHaveVoluntary ();

}