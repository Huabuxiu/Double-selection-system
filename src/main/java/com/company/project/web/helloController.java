package com.company.project.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;


/**
* Created by Huabuxiu on 2020/01/17.
 *
 docker run -itd --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
*/
@RestController
@RequestMapping("/hello")
public class helloController {

    private static final Logger log = LoggerFactory.getLogger(helloController.class);

    @RequestMapping(path = {"/test"})
    public @ResponseBody String login() {
        log.info("helloController:login");
        return "hello world";
    }


}
