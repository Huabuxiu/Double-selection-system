package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.*;
import com.company.project.service.ResultsService;
import com.company.project.service.StudentService;
import com.company.project.service.TeacherService;
import com.company.project.service.VoluntaryService;
import com.company.project.util.ProgessState;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by Huabuxiu on 2020/03/20.
*/
@RestController
@RequestMapping("/voluntary")
public class VoluntaryController {
    @Resource
    private VoluntaryService voluntaryService;

    @Resource
    private TeacherService teacherService;

    @Resource
    StudentService studentService;

    @Resource
    ResultsService resultsService;

    @Autowired
    HostHolder hostHolder;

    /*
    学生接口
     */

    @PostMapping("/add")
    public Result add(@RequestBody Map<String,Integer> data) {
        User user = hostHolder.getUser();
        if (user == null){
            return  ResultGenerator.genFailResult("用户不存在");
        }
        Student student =studentService.findBy("uid",user.getUid());

        Voluntary voluntary = null;
        List<Voluntary> voluntaryList = null;
        Example condition = new Condition(Voluntary.class);
        condition.createCriteria().andEqualTo("level",data.get("level"));
        condition.and().andEqualTo("sid",student.getSid());
        voluntaryList = voluntaryService.findByCondition((Condition) condition);
        if (voluntaryList.size()!=0){
            return  ResultGenerator.genFailResult("第"+data.get("level")+"志愿已存在");
        }
        condition.clear();
        condition.createCriteria().andEqualTo("tid",data.get("tid"));
        condition.and().andEqualTo("sid",student.getSid());
        voluntaryList = voluntaryService.findByCondition((Condition) condition);
        if (voluntaryList.size()!=0){
            return  ResultGenerator.genFailResult(teacherService.findById(data.get("tid")).getName()+"老师已经选择了");
        }
        voluntary = new Voluntary();
        voluntary.setDate(new Date());
        voluntary.setSid(student.getSid());
        voluntary.setRid(resultsService.findBy("sid",student.getSid()).getRid());
        voluntary.setTid(data.get("tid"));
        voluntary.setLevel(data.get("level"));
        voluntary.setProgress(ProgessState.getProgessCode("提交志愿"));
        voluntaryService.save(voluntary);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/studen_list")
    public Result list(@RequestBody Map<String,Integer> data) {

        Example condition = new Condition(Voluntary.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("sid",studentService.findBy("uid",data.get("uid")).getSid());
        List<Voluntary> list  = voluntaryService.findByCondition((Condition) condition);
        return ResultGenerator.genSuccessResult(voluntaryService.getVoList(list));
    }

    /*
    导师接口
     */


}
