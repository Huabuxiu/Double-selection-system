package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.*;
import com.company.project.service.*;
import com.company.project.service.impl.VoluntaryServiceImpl;
import com.company.project.util.ProgessState;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource
    StudentNumService studentNumService;

    @Resource
    VoluntaryStateService voluntaryStateService;

    @Autowired
    HostHolder hostHolder;

    private final Logger logger = LoggerFactory.getLogger(VoluntaryController.class);

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

        //        志愿满足3个且都失败，删除旧失败的志愿
        condition.createCriteria().andEqualTo("sid",student.getSid());
        voluntaryList = voluntaryService.findByCondition((Condition) condition);
        if (voluntaryList.size()==3){
            int flag = 0;
            for (Voluntary elem:
                 voluntaryList) {
                if (ProgessState.getState(elem.getProgress()).equals("导师拒绝")
                        || ProgessState.getState(elem.getProgress()).equals("满员失败")
                        || ProgessState.getState(elem.getProgress()).equals("双选失败")){
                    flag++;
                }
            }
            if (flag == 3){//删除现在的志愿
                for (Voluntary elem:
                        voluntaryList) {
                    voluntaryStateService.deleteById(elem.getVid());
                    voluntaryService.deleteById(elem.getVid());
                }
            }
        }
//        判断志愿重叠
        condition.clear();
        condition.createCriteria().andEqualTo("level",data.get("level"));
        condition.and().andEqualTo("sid",student.getSid());
        voluntaryList = voluntaryService.findByCondition((Condition) condition);
        if (voluntaryList.size()!=0){
            return  ResultGenerator.genFailResult("第"+data.get("level")+"志愿已存在");
        }
//        判断老师重叠
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
        //满员检测
        condition.clear();
        condition.createCriteria().andEqualTo("tid",voluntary.getTid());
        int num = voluntaryService.findByCondition((Condition) condition).size();
        int studentnum = studentNumService.findById(voluntary.getTid()).getNum();
        if (num == studentnum ) {
            voluntary.setProgress(ProgessState.getProgessCode("满员失败"));
        }else {
            voluntary.setProgress(ProgessState.getProgessCode("提交志愿"));
        }
        VoluntaryState voluntaryState = new VoluntaryState();
        if (voluntary.getLevel()==1){
            voluntaryState.setAlive("on");
        }else {
            voluntaryState.setAlive("off");
        }
        voluntaryService.save(voluntary);
        condition.clear();
        condition.createCriteria().andEqualTo("tid",voluntary.getTid());
        condition.and().andEqualTo("sid",voluntary.getSid());
        Voluntary voluntary1 =  voluntaryService.findByCondition((Condition) condition).get(0);
        voluntaryState.setVid(voluntary1.getVid());
        if (ProgessState.getState(voluntary1.getProgress()).equals("满员失败")) {
            voluntaryState.setAlive("off");
        }
        voluntaryStateService.save(voluntaryState);
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

    /*
      导师查看确定学生列表
     */
    @PostMapping("/teacher_handled_list")
    public Result teacherHandledList() {
        User user = hostHolder.getUser();
        Teacher teacher = teacherService.findBy("uid",user.getUid());
        Example condition = new Condition(Voluntary.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("tid",teacher.getTid());
        condition.and().andEqualTo("progress",ProgessState.getProgessCode("导师通过"));
        List<Voluntary> list  = voluntaryService.findByCondition((Condition) condition);
        return ResultGenerator.genSuccessResult(voluntaryService.getVoList(list));
    }

    /*
  导师查看待选学生列表
 */
    @PostMapping("/teacher_unhandle_list")
    public Result teacher_list() {
        User user = hostHolder.getUser();
        Teacher teacher = teacherService.findBy("uid",user.getUid());
        List<Voluntary> list  = voluntaryService.findUnhandle(teacher.getTid());
        return ResultGenerator.genSuccessResult(voluntaryService.getVoList(list));
    }




}
