package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Conversation;

import java.util.List;

public interface ConversationMapper extends Mapper<Conversation> {

  public List<Conversation> getAllConversation(Integer uid);
}