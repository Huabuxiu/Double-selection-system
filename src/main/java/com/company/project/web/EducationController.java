package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Education;
import com.company.project.model.Student;
import com.company.project.service.EducationService;
import com.company.project.service.StudentService;
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
public class EducationController {
    @Resource
    private EducationService educationService;

    @Resource
    private StudentService studentService;

    @PostMapping("/add")
    public Result add(Education education) {
        educationService.save(education);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        educationService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Education education) {
        educationService.update(education);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/education")
    public Result education(@RequestBody Map<String,Integer> data) {
        Student student =studentService.findBy("uid",data.get("uid"));
        Education education = educationService.findBy("sid",student.getSid());
        return ResultGenerator.genSuccessResult(education);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Education> list = educationService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
