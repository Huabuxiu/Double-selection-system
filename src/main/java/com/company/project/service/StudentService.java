package com.company.project.service;
import com.company.project.model.Student;
import com.company.project.core.Service;

import java.util.List;


/**
 * Created by Huabuxiu on 2020/03/20.
 */
public interface StudentService extends Service<Student> {
    public List<Student> dontHaveVoluntary ();
}
