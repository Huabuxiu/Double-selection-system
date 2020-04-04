package com.company.project.model;

import com.company.project.model.StudentVoluntary;
import com.company.project.model.Voluntary;
import com.company.project.model.VoluntaryVo;
import com.company.project.service.VoluntaryService;
import com.company.project.service.impl.VoluntaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentListElement {



    public List<StudentVoluntary> voluntaries;


    public StudentListElement() {
        this.voluntaries = new ArrayList<StudentVoluntary>();
    }

    public void setVoluntarie(StudentVoluntary V){
        voluntaries.add(V);
    }


}
