package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.*;
import com.company.project.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
* Created by Huabuxiu on 2020/03/20.
*/
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Resource
    private EducationService educationService;

    @Resource
    private ProjectService projectService;

    @Resource
    private ResultsService resultsService;


    @Autowired
    HostHolder hostHolder;

    /*
    学生基本信息
     */

    @PostMapping("/basic_add")
    public Result add(@RequestBody Map<String,String> data) {
        Student student = new Student();
        User user = hostHolder.getUser();
        student.setUid(user.getUid());
        student.setPhone(data.get("phone"));
        student.setName(data.get("name"));
        student.seteMail(data.get("eMail"));
        student.setQq(data.get("qq"));
        student.setImage(data.get("image"));
        student.setUpdateTime(new Date());
        studentService.save(student);
        return ResultGenerator.genSuccessResult("新增成功");
    }


    @PostMapping("/basic")
    public Result basic(@RequestBody Map<String,Integer> data) {
        Student student = studentService.findBy("uid",data.get("uid"));
        if (student==null){
            return ResultGenerator.genFailResult("学生信息不存在");
        }
        Map<String,Object> modleMap = new HashMap<>();
        modleMap.put("name",student.getName());
        modleMap.put("phone",student.getPhone());
        modleMap.put("email",student.geteMail());
        modleMap.put("qq",student.getQq());
        modleMap.put("image",student.getImage());
        return ResultGenerator.genSuccessResult(modleMap);
    }


    @PostMapping("/basic_update")
    public Result update(@RequestBody Map<String,String> data) {
        User user = hostHolder.getUser();
        Student student = studentService.findBy("uid",user.getUid());
        student.setPhone(data.get("phone"));
        student.setName(data.get("name"));
        student.seteMail(data.get("eMail"));
        student.setQq(data.get("qq"));
        student.setImage(data.get("image"));
        student.setUpdateTime(new Date());
        studentService.update(student);
        return ResultGenerator.genSuccessResult("更新成功");
    }


    /*
    学生教育信息
     */

    @PostMapping("/education")
    public Result education(@RequestBody Map<String,Integer> data) {
        Student student = studentService.findBy("uid",data.get("uid"));
        if (student==null){
            return ResultGenerator.genFailResult("学生信息不存在");
        }
        Education education = educationService.findBy("sid",student.getSid());
        if (education == null){
            return ResultGenerator.genFailResult("教育信息不存在");
        }
        Map<String,Object> modleMap = new HashMap<>();
        modleMap.put("time_start",education.getTimeStart());
        modleMap.put("time_end",education.getTimeEnd());
        modleMap.put("school",education.getSchool());
        modleMap.put("major",education.getMajor());
        modleMap.put("sedu_dec",education.getSeduDec());
        return ResultGenerator.genSuccessResult(modleMap);
    }


    @PostMapping("/education_add")
    public Result educationAdd(@RequestBody Map<String,Object> data) {
        User user = hostHolder.getUser();
        Student student = studentService.findBy("uid",user.getUid());
        if (student==null){
            return ResultGenerator.genFailResult("学生信息不存在");
        }
        Education education = new Education();
        education.setSid(student.getSid());
        education.setSchool((String) data.get("school"));
        education.setMajor((String)data.get("major"));
        education.setSeduDec((String)data.get("sedu_dec"));
        education.setTimeStart(new Date((Long) data.get("time_start")));
        education.setTimeEnd(new Date((Long)data.get("time_end")));
        educationService.save(education);
        return ResultGenerator.genSuccessResult("新增成功");
    }


    @PostMapping("/education_update")
    public Result educationUpdate(@RequestBody Map<String,Object> data) {
        User user = hostHolder.getUser();
        Student student = studentService.findBy("uid",user.getUid());
        if (student==null){
            return ResultGenerator.genFailResult("学生信息不存在");
        }
        Education education = educationService.findBy("sid",student.getSid());
        education.setSchool((String) data.get("school"));
        education.setMajor((String)data.get("major"));
        education.setSeduDec((String)data.get("sedu_dec"));
        education.setTimeStart(new Date((Long) data.get("time_start")));
        education.setTimeEnd(new Date((Long)data.get("time_end")));
        educationService.update(education);
        return ResultGenerator.genSuccessResult("更新成功");
    }


    /*
    考研成绩
     */

    @PostMapping("/score")
    public Result score(@RequestBody Map<String,Integer> data) {
        Student student = studentService.findBy("uid",data.get("uid"));
        if (student==null){
            return ResultGenerator.genFailResult("学生信息不存在");
        }
       Results results = resultsService.findBy("sid",student.getSid());
        if (results == null){
            return ResultGenerator.genFailResult("教育信息不存在");
        }
        Map<String,Object> modleMap = new HashMap<>();
        modleMap.put("math",results.getMath());
        modleMap.put("english",results.getEnglish());
        modleMap.put("politics",results.getPolitics());
        modleMap.put("major",results.getMajor());
        modleMap.put("total_score",results.getTotalScore());
        modleMap.put("exam_type",results.getExamType());
        return ResultGenerator.genSuccessResult(modleMap);
    }


    @PostMapping("/score_add")
    public Result scoreAdd(@RequestBody Map<String,Object> data) {
        User user = hostHolder.getUser();
        Student student = studentService.findBy("uid",user.getUid());
        if (student==null){
            return ResultGenerator.genFailResult("学生信息不存在");
        }
        Results results = new Results();
        results.setSid(student.getSid());
        results.setMath((Integer) data.get("math"));
        results.setEnglish((Integer) data.get("english"));
        results.setPolitics((Integer) data.get("politics"));
        results.setMajor((Integer) data.get("major"));
        results.setTotalScore((Integer) data.get("total_score"));
        results.setExamType((String) data.get("exam_type"));
        resultsService.save(results);
        return ResultGenerator.genSuccessResult("新增成功");
    }

    @PostMapping("/score_update")
    public Result scoreUpdate(@RequestBody Map<String,Object> data) {
        User user = hostHolder.getUser();
        Student student = studentService.findBy("uid",user.getUid());
        if (student==null){
            return ResultGenerator.genFailResult("学生信息不存在");
        }
        Results results = resultsService.findBy("sid",student.getSid());
        results.setMath((Integer)data.get("math"));
        results.setEnglish((Integer) data.get("english"));
        results.setPolitics((Integer) data.get("politics"));
        results.setMajor((Integer) data.get("major"));
        results.setTotalScore((Integer) data.get("total_score"));
        results.setExamType((String) data.get("exam_type"));
        resultsService.update(results);
        return ResultGenerator.genSuccessResult("更新成功");
    }


    /*
    项目经历
     */
    @PostMapping("/project")
    public Result project(@RequestBody Map<String,Integer> data) {
        Example condition = new Condition(Project.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("uid",data.get("uid"));
        List<Project> projectList = projectService.findByCondition((Condition) condition);
        List<Map> returnList = new ArrayList<>();
        for (Project project:
             projectList) {
            Map<String,Object> elementmap = new HashMap<>();
            elementmap.put("name",project.getName());
            elementmap.put("time_start",project.getTimeStart());
            elementmap.put("time_end",project.getTimeEnd());
            elementmap.put("position",project.getPosition());
            elementmap.put("describes",project.getDescribes());
            returnList.add(elementmap);
        }
        return ResultGenerator.genSuccessResult(returnList);
    }

    @PostMapping("/project_add")
    public Result projectAdd(@RequestBody Map<String,Object> data){
        Project project = new Project();
        User user = hostHolder.getUser();
        project.setUid(user.getUid());
        project.setName((String) data.get("name"));
        project.setTimeStart(new Date((Long) data.get("time_start")));
        project.setTimeEnd(new Date((Long) data.get("time_end")));
        project.setPosition((String) data.get("position"));
        project.setDescribes((String) data.get("describes"));
        projectService.save(project);
        return ResultGenerator.genSuccessResult("新增成功");
    }

    @PostMapping("/project_update")
    public Result projectUpdate(@RequestBody Map<String,Object> data) {

        Project project = projectService.findById((Integer) data.get("pid"));
        if (project==null){
            return ResultGenerator.genFailResult("项目信息不存在");
        }
        project.setName((String) data.get("name"));
        project.setTimeStart(new Date((Long) data.get("time_start")));
        project.setTimeEnd(new Date((Long) data.get("time_end")));
        project.setPosition((String) data.get("position"));
        project.setDescribes((String) data.get("describes"));
        projectService.update(project);
        return ResultGenerator.genSuccessResult("更新成功");
    }

}
