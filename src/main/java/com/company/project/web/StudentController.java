package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.HostHolder;
import com.company.project.model.Project;
import com.company.project.model.Student;
import com.company.project.model.User;
import com.company.project.service.EducationService;
import com.company.project.service.ProjectService;
import com.company.project.service.StudentService;
import com.company.project.service.UserService;
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
    private UserService userService;

    @Autowired
    HostHolder hostHolder;

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

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        studentService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/project")
    public Result project(@RequestBody Map<String,Integer> data) {
        Student student = studentService.findBy("uid",data.get("uid"));
        //使用条件查询
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


}
