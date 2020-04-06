package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.*;
import com.company.project.service.ConversationService;
import com.company.project.service.StudentService;
import com.company.project.service.TeacherService;
import com.company.project.service.UserService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
* Created by Huabuxiu on 2020/04/05.
*/
@RestController
@RequestMapping("/conversation")
public class ConversationController {
    @Resource
    private ConversationService conversationService;

    @Resource
    private UserService userService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;



    @PostMapping("/unread")
    public Result unread(@RequestBody Map<String,String> data) {
        Condition condition = new Condition(Conversation.class);
        condition.createCriteria().andEqualTo("toid",data.get("uid"));
        condition.and().andEqualTo("state","未读");
        List<Conversation> conversations = conversationService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(conversations.size());
    }

    @PostMapping("/user_list")
    public Result list(@RequestBody Map<String,Integer> data) {
        Condition condition = new Condition(Conversation.class);
        List<Conversation> list = conversationService.getAllConversation(data.get("uid"));
        List<ConversationVo> returnList = new ArrayList<>();
        //去获取数据
        for (Conversation ele :
                list) {
            condition.clear();
            Integer userId = (ele.getFromid() == data.get("uid")) ? ele.getToid(): ele.getFromid();
            condition.createCriteria().andEqualTo("conid",ele.getConid());
            condition.orderBy("date").desc();
            List<Conversation> list1 = conversationService.findByCondition(condition);
            User user = userService.findById(userId);
            ConversationVo conversationVo = new ConversationVo();
            conversationVo.setToId(ele.getToid());
            conversationVo.setConversationId(ele.getConid());
            conversationVo.setFromId(list1.get(0).getFromid());
            conversationVo.setDate(list1.get(0).getDate());
            conversationVo.setMessage(list1.get(0).getMessage());
            if (user.getUserRole() == 1){
                Student  student = studentService.findBy("uid",user.getUid());
                conversationVo.setUserName(student.getName());
                conversationVo.setUrl(student.getImage());
            }else if (user.getUserRole() == 2){
                Teacher  teacher = teacherService.findBy("uid",user.getUid());
                conversationVo.setUrl(teacher.getImage());
                conversationVo.setUserName(teacher.getName());
            }
            returnList.add(conversationVo);
        }
        //按日期排序
        returnList.sort(new Comparator<ConversationVo>() {
            @Override
            public int compare(ConversationVo conversationVo, ConversationVo t1) {
                int x =  conversationVo.getDate().compareTo(t1.getDate());
                if (x == -1){
                    return 1;
                }else if (x==1)
                {
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        return ResultGenerator.genSuccessResult(returnList);
    }


    @PostMapping("/history")
    public Result history(@RequestBody Map<String,String> data) {
        Condition condition = new Condition(Conversation.class);
        condition.createCriteria().andEqualTo("conid",data.get("conversationId"));
        condition.orderBy("date").asc();
        List<Conversation> list = conversationService.findByCondition(condition);
        List<MessageVo> messageList = new ArrayList<>();
        for (Conversation ele : list){
            if (ele.getState().equals("未读")){
                ele.setState("已读");
                conversationService.update(ele);
            }
            messageList.add(new MessageVo(ele));
        }
        return ResultGenerator.genSuccessResult(messageList);
    }

}
