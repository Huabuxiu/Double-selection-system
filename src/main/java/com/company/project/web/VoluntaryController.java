package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Voluntary;
import com.company.project.service.VoluntaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Huabuxiu on 2020/03/20.
*/
@RestController
@RequestMapping("/voluntary")
public class VoluntaryController {
    @Resource
    private VoluntaryService voluntaryService;

    @PostMapping("/add")
    public Result add(Voluntary voluntary) {
        voluntaryService.save(voluntary);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        voluntaryService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Voluntary voluntary) {
        voluntaryService.update(voluntary);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Voluntary voluntary = voluntaryService.findById(id);
        return ResultGenerator.genSuccessResult(voluntary);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Voluntary> list = voluntaryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
