package com.company.project.service;
import com.company.project.model.Conversation;
import com.company.project.core.Service;

import java.util.List;


/**
 * Created by Huabuxiu on 2020/04/05.
 */
public interface ConversationService extends Service<Conversation> {
    public List<Conversation> getAllConversation(Integer uid);
}
