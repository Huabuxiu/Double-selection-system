package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.*;
import com.company.project.service.*;
import com.company.project.util.ProgessState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//添加志愿
    @PostMapping("/add")
    public Result add(@RequestBody Map<String,Integer> data) {
        User user = hostHolder.getUser();
        if (user == null){
            return  ResultGenerator.genFailResult("用户不存在");
        }
        Student student =studentService.findBy("uid",user.getUid());
        if (student==null){
            return ResultGenerator.genFailResult("学生信息未添加，请先添加学生信息");
        }
        Voluntary voluntary = null;
        List<Voluntary> voluntaryList = null;
        Example condition = new Condition(Voluntary.class);

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
        //判断志愿开启状态
        if (voluntary.getLevel()==1){
            voluntaryState.setAlive("on");
        }else if (voluntary.getLevel()==2){
            condition.clear();
            condition.createCriteria().andEqualTo("sid",voluntary.getSid());
            condition.and().andEqualTo("level",voluntary.getLevel()-1);
            List<Voluntary> vol = voluntaryService.findByCondition((Condition)condition);
            if (vol.size()==0){
                voluntaryState.setAlive("off");
            }else if(vol.size()==1){
                if (voluntaryStateService.findById(vol.get(0).getVid()).getAlive().equals("on")){
                    voluntaryState.setAlive("off");
                }
            }else {
                voluntaryState.setAlive("on");
            }
        }else {
            condition.clear();
            condition.createCriteria().andEqualTo("sid",voluntary.getSid());
            List<Voluntary> vol = voluntaryService.findByCondition((Condition)condition);
            if (vol.size() == 0 ){ //前两个志愿都不存在
                voluntaryState.setAlive("off");
            }else if (vol.size() == 1 ){ //前两个志愿有一个存在
                voluntaryState.setAlive("off");
            } else if (vol.size() == 2){//前个都存在而且有一个是打开状态
                if (voluntaryStateService.findById(vol.get(0).getVid()).getAlive().equals("on")
                         ||
                       voluntaryStateService.findById(vol.get(1).getVid()).getAlive().equals("on")) {
                    voluntaryState.setAlive("off");
                }
           }else {  //前两个都存在而且都关闭
               voluntaryState.setAlive("on");
           }
        }
        voluntaryService.save(voluntary);
        condition.clear();
        condition.createCriteria().andEqualTo("tid",voluntary.getTid());
        condition.and().andEqualTo("sid",voluntary.getSid());
        Voluntary voluntary1 =  voluntaryService.findByCondition((Condition) condition).get(0);
        voluntaryState.setVid(voluntary1.getVid());
        if (ProgessState.getState(voluntary1.getProgress(),voluntaryState.getAlive()).equals("满员失败")) {
           //失败处理
            voluntaryState.setAlive("off");
            condition.clear();
            condition.createCriteria().andEqualTo("sid",voluntary.getSid());
            condition.and().andEqualTo("level",voluntary.getLevel()+1);
            Voluntary voluntarynextLevel = voluntaryService.findByCondition((Condition) condition).get(0);
            if (voluntarynextLevel !=null){
                VoluntaryState voluntaryStatenext = new VoluntaryState();
                voluntaryStatenext.setVid(voluntarynextLevel.getVid());
                voluntaryStatenext.setAlive("on");
                voluntaryStateService.update(voluntaryStatenext);
            }
        }
        voluntaryStateService.save(voluntaryState);
        return ResultGenerator.genSuccessResult();
    }



//    学生查看自己的志愿列表
    @PostMapping("/student_list")
    public Result list(@RequestBody Map<String,Integer> data) {
        Example condition = new Condition(Voluntary.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("sid",studentService.findBy("uid",data.get("uid")).getSid());
        condition.orderBy("level").asc();
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
        condition.or().andEqualTo("progress",ProgessState.getProgessCode("双选成功"));
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


//导师通过学生
    @PostMapping("/teacher_pass")
    public Result teacherPass(@RequestBody Map<String,Integer> data ) {
        User user = hostHolder.getUser();
        Teacher teacher = teacherService.findBy("uid",user.getUid());
        Example condition = new Condition(Voluntary.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("tid",teacher.getTid());
        condition.and().andEqualTo("sid",data.get("sid"));
        List<Voluntary> list  = voluntaryService.findByCondition((Condition) condition);
        Voluntary voluntary = list.get(0);
        voluntary.setProgress(ProgessState.getProgessCode("导师通过"));
        voluntaryService.update(voluntary);
        return ResultGenerator.genSuccessResult();
    }

//导师拒绝学生
    @PostMapping("/teacher_refuse")
    public Result teacher_refuse(@RequestBody Map<String,Integer> data ) {
        User user = hostHolder.getUser();
        Teacher teacher = teacherService.findBy("uid",user.getUid());
        Example condition = new Condition(Voluntary.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("tid",teacher.getTid());
        condition.and().andEqualTo("sid",data.get("sid"));
        List<Voluntary> list  = voluntaryService.findByCondition((Condition) condition);
        Voluntary voluntary = list.get(0);
        voluntary.setProgress(ProgessState.getProgessCode("导师拒绝"));
        voluntaryService.update(voluntary);
        //失败处理
        VoluntaryState voluntaryState = voluntaryStateService.findById(voluntary.getVid());
        voluntaryState.setAlive("off");
        voluntaryStateService.update(voluntaryState);   //更新当前志愿状态
        //查看下一志愿并开启下一志愿
        condition.clear();
        condition.createCriteria().andEqualTo("sid",voluntary.getSid());
        condition.and().andEqualTo("level",voluntary.getLevel()+1);
        List<Voluntary> list1 = voluntaryService.findByCondition((Condition) condition);
        if (list1.size()!=0){
            Voluntary voluntarynextLevel = list1.get(0);
            VoluntaryState voluntaryStatenext = new VoluntaryState();
            voluntaryStatenext.setVid(voluntarynextLevel.getVid());
            voluntaryStatenext.setAlive("on");
            voluntaryStateService.update(voluntaryStatenext);
        }
        return ResultGenerator.genSuccessResult();
    }

/*
    管理员接口
 */

    /*
   管理员查看待选学生列表
  */
    @PostMapping("/admin_unhandle_list")
    public Result adminlist() {

        Condition condition = new Condition(Voluntary.class);
        condition.createCriteria().andEqualTo("progress",2);
        List<Voluntary> list = voluntaryService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(voluntaryService.getVoList(list));
    }


    //管理员通过学生
    @PostMapping("/admin_pass")
    public Result adminPass(@RequestBody Map<String,Integer> data ) {
        Example condition = new Condition(Voluntary.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("progress",2);
        condition.and().andEqualTo("sid",data.get("sid"));
        List<Voluntary> list  = voluntaryService.findByCondition((Condition) condition);
        Voluntary voluntary = list.get(0);
        voluntary.setProgress(ProgessState.getProgessCode("双选成功"));
        voluntaryService.update(voluntary);
        return ResultGenerator.genSuccessResult();
    }

    //管理员拒绝学生
    @PostMapping("/admin_refuse")
    public Result admin_refuse(@RequestBody Map<String,Integer> data ) {
        Example condition = new Condition(Voluntary.class);
        //使用相等字段
        condition.createCriteria().andEqualTo("progress",2);
        condition.and().andEqualTo("sid",data.get("sid"));
        List<Voluntary> list  = voluntaryService.findByCondition((Condition) condition);
        Voluntary voluntary = list.get(0);
        voluntary.setProgress(ProgessState.getProgessCode("双选失败"));
        voluntaryService.update(voluntary);
        //失败处理
        VoluntaryState voluntaryState = voluntaryStateService.findById(voluntary.getVid());
        voluntaryState.setAlive("off");
        voluntaryStateService.update(voluntaryState);   //更新当前志愿状态
        //查看下一志愿并开启下一志愿
        condition.clear();
        condition.createCriteria().andEqualTo("sid",voluntary.getSid());
        condition.and().andEqualTo("level",voluntary.getLevel()+1);
        List<Voluntary> list1 = voluntaryService.findByCondition((Condition) condition);
        if (list1.size()!=0){
            Voluntary voluntarynextLevel = list1.get(0);
            VoluntaryState voluntaryStatenext = new VoluntaryState();
            voluntaryStatenext.setVid(voluntarynextLevel.getVid());
            voluntaryStatenext.setAlive("on");
            voluntaryStateService.update(voluntaryStatenext);
        }
        return ResultGenerator.genSuccessResult();
    }


//    查看所有学生
    @PostMapping("/all_student_list")
    public Result all_studen_list() {
        List<VoluntaryVo> voList= new ArrayList<>();
       Map<String,StudentListElement> returnmap  = new HashMap<>();
        Set<Integer> sidSet  = new HashSet<>();
        List<StudentVoluntary> studentListElements = voluntaryService.getStudentVoluntaryList();

        for (StudentVoluntary ele :
                studentListElements) {
            if (!sidSet.contains(ele.getSid())) {
                StudentListElement listElement = new StudentListElement();
                listElement.setVoluntarie(ele);
                sidSet.add(ele.getSid());
                returnmap.put(String.valueOf(ele.getSid()),listElement);
            }else {
                returnmap.get(String.valueOf(ele.getSid())).setVoluntarie(ele);
            }
        }
        returnmap.forEach((value,key)->{
            voList.add(getElement(key.voluntaries));
        });

//        没有提交列表
        List<Student> students = studentService.dontHaveVoluntary();
        if (students.size() !=0){
            voList.addAll(voluntaryService.getEpmtyVoList(students));
        }
        return ResultGenerator.genSuccessResult(voList);
    }




    public VoluntaryVo getElement(List<StudentVoluntary> voluntaries){
        Voluntary voluntary = new Voluntary();

//        正常状态
        for (StudentVoluntary ele:voluntaries){
            if (ele.getAlive().equals("on")){
                voluntary.setProgress(ele.getProgress());
                voluntary.setRid(ele.getRid());
                voluntary.setSid(ele.getSid());
                voluntary.setDate(ele.getDate());
                voluntary.setLevel(ele.getLevel());
                voluntary.setTid(ele.getTid());
                voluntary.setVid(ele.getVid());
                return voluntaryService.getVo( voluntary,"on");
            }
        }

        if (voluntaries.size()==1 ){
            if (voluntaries.get(0).getLevel()==1) {
                StudentVoluntary ele = voluntaries.get(0);
                voluntary.setProgress(ele.getProgress());
                voluntary.setRid(ele.getRid());
                voluntary.setSid(ele.getSid());
                voluntary.setDate(ele.getDate());
                voluntary.setLevel(ele.getLevel());
                voluntary.setTid(ele.getTid());
                voluntary.setVid(ele.getVid());
                return voluntaryService.getVo(voluntary, "只有第一志愿且失败");
            }else {
                StudentVoluntary ele = voluntaries.get(0);
                voluntary.setProgress(ele.getProgress());
                voluntary.setRid(ele.getRid());
                voluntary.setSid(ele.getSid());
                voluntary.setDate(ele.getDate());
                voluntary.setLevel(ele.getLevel());
                voluntary.setTid(ele.getTid());
                voluntary.setVid(ele.getVid());
                return voluntaryService.getVo( voluntary,"没有第一志愿，未进入流程");
            }
        }
        if (voluntaries.size()==2){
            if (voluntaries.get(0).getLevel()!=1){
                voluntary.setProgress(voluntaries.get(0).getProgress());
                voluntary.setRid(voluntaries.get(0).getRid());
                voluntary.setSid(voluntaries.get(0).getSid());
                voluntary.setDate(voluntaries.get(0).getDate());
                voluntary.setLevel(voluntaries.get(0).getLevel());
                voluntary.setTid(voluntaries.get(0).getTid());
                voluntary.setVid(voluntaries.get(0).getVid());
                return voluntaryService.getVo( voluntary,"没有第一志愿，未进入流程");
            }else if (voluntaries.get(1).getLevel()==3){
                voluntary.setProgress(voluntaries.get(1).getProgress());
                voluntary.setRid(voluntaries.get(1).getRid());
                voluntary.setSid(voluntaries.get(1).getSid());
                voluntary.setDate(voluntaries.get(1).getDate());
                voluntary.setLevel(voluntaries.get(1).getLevel());
                voluntary.setTid(voluntaries.get(1).getTid());
                voluntary.setVid(voluntaries.get(1).getVid());
                return voluntaryService.getVo( voluntary,"第二志愿缺失，暂时未进入流程");
            }
        }
        if (voluntaries.size()==3){
            voluntary.setProgress(voluntaries.get(2).getProgress());
            voluntary.setRid(voluntaries.get(2).getRid());
            voluntary.setSid(voluntaries.get(2).getSid());
            voluntary.setDate(voluntaries.get(2).getDate());
            voluntary.setLevel(voluntaries.get(2).getLevel());
            voluntary.setTid(voluntaries.get(2).getTid());
            voluntary.setVid(voluntaries.get(2).getVid());
            return voluntaryService.getVo( voluntary,"三大志愿都失败");
        }
        return null;
    }




}
