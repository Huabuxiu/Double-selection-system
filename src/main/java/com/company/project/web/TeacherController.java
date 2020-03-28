package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Teacher;
import com.company.project.service.TeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by Huabuxiu on 2020/03/20.
*/
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @PostMapping("/basic_add")
    public Result add(@RequestBody Map<String,String> data) {
        Teacher teacher= new Teacher();
        teacher.setName(data.get("name"));
        teacher.setTitle(data.get("title"));
        teacher.setResearchDirection(data.get("research_direction"));
        teacher.setImage(data.get("image"));
        teacher.setResearchFindings(data.get("research_findings"));
        teacherService.save(teacher);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        teacherService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Teacher teacher) {
        teacherService.update(teacher);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Teacher teacher = teacherService.findById(id);
        return ResultGenerator.genSuccessResult(teacher);
    }

    @RequestMapping("/list")
    public Result list() {
        List<Teacher> list = teacherService.findAll();
        return ResultGenerator.genSuccessResult(list).setMessage("teacher list");
    }
}
