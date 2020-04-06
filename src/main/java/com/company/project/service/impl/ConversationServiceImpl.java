package com.company.project.service.impl;

import com.company.project.dao.ConversationMapper;
import com.company.project.model.Conversation;
import com.company.project.service.ConversationService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Huabuxiu on 2020/04/05.
 */
@Service
@Transactional
public class ConversationServiceImpl extends AbstractService<Conversation> implements ConversationService {
    @Resource
    private ConversationMapper conversationMapper;

    @Override
    public List<Conversation> getAllConversation(Integer uid) {
        return conversationMapper.getAllConversation(uid);
    }
}
