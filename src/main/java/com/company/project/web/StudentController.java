package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.HostHolder;
import com.company.project.model.Student;
import com.company.project.model.User;
import com.company.project.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by Huabuxiu on 2020/03/20.
*/
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Autowired
    HostHolder hostHolder;

    @PostMapping("/add")
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

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        studentService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Student student) {
        studentService.update(student);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Student student = studentService.findById(id);
        return ResultGenerator.genSuccessResult(student);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Student> list = studentService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
