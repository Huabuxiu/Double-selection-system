package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Voluntary;

import java.util.List;

public interface VoluntaryMapper extends Mapper<Voluntary> {
    public List<Voluntary> findUnhandle(int tid);
}