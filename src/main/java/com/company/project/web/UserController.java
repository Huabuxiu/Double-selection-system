package com.company.project.web;
import com.company.project.configurer.Log;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.HostHolder;
import com.company.project.model.Student;
import com.company.project.model.Teacher;
import com.company.project.model.User;
import com.company.project.service.StudentService;
import com.company.project.service.TeacherService;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        userService.deleteById(data.get("id"));
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
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
