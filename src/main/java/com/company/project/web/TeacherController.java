package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.*;
import com.company.project.service.ProjectService;
import com.company.project.service.TeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
* Created by Huabuxiu on 2020/03/28.
*/
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @Autowired
    HostHolder hostHolder;

    @Resource
    private ProjectService projectService;

    /*
    老师基本信息
     */

    @PostMapping("/basic_add")
    public Result add(@RequestBody Map<String,String> data) {
        User user = hostHolder.getUser();
        if (user==null){
            return ResultGenerator.genFailResult("用户不存在");
        }
        Teacher teacher= new Teacher();
        teacher.setUid(user.getUid());
        teacher.setName(data.get("name"));
        teacher.setTitle(data.get("title"));
        teacher.setResearchDirection(data.get("research_direction"));
        teacher.setImage(data.get("image"));
        teacher.setResearchFindings(data.get("research_findings"));
        teacher.seteMail(data.get("e_mail"));
        teacherService.save(teacher);
        return ResultGenerator.genSuccessResult().setMessage("新增成功");
    }

    @PostMapping("/basic")
    public Result basic(@RequestBody Map<String,Integer> data) {
        Teacher teacher = teacherService.findBy("uid",data.get("uid"));
        if (teacher==null){
            return ResultGenerator.genFailResult("导师信息不存在");
        }
        Map<String,Object> modleMap = new HashMap<>();
        modleMap.put("name",teacher.getName());
        modleMap.put("title",teacher.getTitle());
        modleMap.put("research_direction",teacher.getResearchDirection());
        modleMap.put("research_findings",teacher.getResearchFindings());
        modleMap.put("image",teacher.getImage());
        modleMap.put("e_mail",teacher.geteMail());
        return ResultGenerator.genSuccessResult(modleMap);
    }


    @PostMapping("/basic_update")
    public Result update(@RequestBody Map<String,String> data) {
        User user = hostHolder.getUser();
        Teacher teacher = teacherService.findBy("uid",user.getUid());
        teacher.setName(data.get("name"));
        teacher.seteMail(data.get("eMail"));
        teacher.setImage(data.get("image"));
        teacher.setTitle(data.get("title"));
        teacher.setResearchDirection(data.get("research_direction"));
        teacher.setResearchFindings(data.get("research_findings"));
        teacherService.update(teacher);
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
            elementmap.put("pid",project.getPid());
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



    @PostMapping("/list")
    public Result list() {
        List<Teacher> list = teacherService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }
}
