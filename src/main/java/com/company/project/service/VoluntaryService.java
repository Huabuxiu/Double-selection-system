package com.company.project.service;
import com.company.project.model.Voluntary;
import com.company.project.core.Service;
import com.company.project.model.VoluntaryVo;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;


/**
 * Created by Huabuxiu on 2020/03/20.
 */
public interface VoluntaryService extends Service<Voluntary> {

    List<VoluntaryVo> getVoList(List<Voluntary> list);

    List<Voluntary> findUnhandle( int tid);
}
