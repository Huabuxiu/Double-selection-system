package com.company.project.web;
import com.company.project.configurer.Log;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.*;
import com.company.project.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
* Created by Huabuxiu on 2020/03/20.
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private ProjectService projectService;

    @Resource
    private StudentNumService studentNumService;

    @Resource
    private VoluntaryStateService voluntaryStateService;

    @Resource
    private VoluntaryService voluntaryService;

    @Resource
    private EducationService educationService;

    @Resource
    private ResultsService resultsService;


    @Autowired
    HostHolder hostHolder;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> data) {

        User user = userService.findBy("username",data.get("username"));
        if (user == null){
            return ResultGenerator.genFailResult("用户名不存在");
        }
        if(!user.getPassword().equals(data.get("password"))){
            return ResultGenerator.genFailResult("密码错误");
        }
        Map returnMap = new HashMap<String,String>();
        returnMap.put("token",user.getToken());
        return ResultGenerator.genSuccessResult(returnMap);
    }

    @PostMapping("/logout")
    public Result logout() {
        User user = hostHolder.getUser();
        if (user == null){
            return ResultGenerator.genFailResult("用户名不存在");
        }
        return ResultGenerator.genSuccessResult().setMessage("logout");
    }



    @PostMapping("/user_info")
    public Result user_info(@RequestBody Map<String,String> data) {
        log.info("user_info   "+"参数:"+data.get("token"));
        User user = userService.findBy("token",data.get("token"));
        if (user == null){
            return ResultGenerator.genFailResult("用户错误");
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("uid",user.getUid());
        returnMap.put("userRole",user.getUserRole());
        switch (user.getUserRole()){
            case 1: {
                Student student = studentService.findBy("uid", user.getUid());
                if (student!=null){
                    returnMap.put("image",student.getImage());
                    returnMap.put("name",student.getName());
                    returnMap.put("sid",student.getSid());
                }
                return ResultGenerator.genSuccessResult(returnMap);
            }
            case 2:{
                Teacher teacher = teacherService.findBy("uid",user.getUid());
                if (teacher!=null){
                    returnMap.put("image",teacher.getImage());
                    returnMap.put("name",teacher.getName());
                    returnMap.put("tid",teacher.getTid());
                }
                return ResultGenerator.genSuccessResult(returnMap);
            }
            default:
                return ResultGenerator.genSuccessResult(returnMap);
        }

    }

    @PostMapping("/add")
    public Result add(@RequestBody Map<String,String> data) {
        User user = new User();
        user.setUsername(data.get("username"));
        user.setPassword(data.get("password"));
        if (userService.findBy("username",data.get("username")) != null){
            return ResultGenerator.genFailResult("用户已存在");
        }
        user.setUserRole(Integer.parseInt(data.get("userRole")));
        String secret = "HAHA";//密钥，自己修改
        String token = DigestUtils.md5Hex(UUID.randomUUID() + secret);//混合密钥md
        user.setToken(token);
        userService.save(user);
        return ResultGenerator.genSuccessResult("新增成功");
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String,Integer> data) {
        User user = userService.findById(data.get("uid"));
        Condition condition = new Condition(Project.class);
        condition.createCriteria().andEqualTo("uid",user.getUid());
        List<Project> projectList = projectService.findByCondition(condition);
        if (projectList.size()!=0){
            for (Project p :
                    projectList) {
                projectService.deleteById(p.getPid());
            }
        }
        if(user.getUserRole()==2){
            Teacher teacher =  teacherService.findBy("uid",user.getUid());
            studentNumService.deleteById(teacher.getTid());
            condition = new Condition(Voluntary.class);
            condition.createCriteria().andEqualTo("tid",teacher.getTid());
            List<Voluntary> voluntaryList = voluntaryService.findByCondition(condition);
            if (voluntaryList.size()!=0){
                for (Voluntary vo :
                        voluntaryList) {
                    voluntaryStateService.deleteById(vo.getVid());
                    voluntaryService.deleteById(vo.getVid());
                }
            }
            teacherService.deleteById(teacher.getTid());
        }else if (user.getUserRole()==1){
            Student student = studentService.findBy("uid",user.getUid());
            condition = new Condition(Voluntary.class);
            condition.createCriteria().andEqualTo("sid",student.getSid());
            List<Voluntary> voluntaryList = voluntaryService.findByCondition(condition);
            if (voluntaryList.size()!=0){
                for (Voluntary vo :
                        voluntaryList) {
                    voluntaryStateService.deleteById(vo.getVid());
                    voluntaryService.deleteById(vo.getVid());
                }
            }
            resultsService.deleteById(resultsService.findBy("sid",student.getSid()).getRid());
            educationService.deleteById(educationService.findBy("sid",student.getSid()).getEid());
            studentService.deleteById(student.getSid());
        }
        userService.deleteById(data.get("uid"));
        return ResultGenerator.genSuccessResult("删除成功");
    }

    @PostMapping("/update")
    public Result update(User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        for (User user:list){
            user.setPassword(" ");
        }
        return ResultGenerator.genSuccessResult(list);
    }
}
