package thi.nguyen.whats_app.service;

import thi.nguyen.whats_app.exception.ChatException;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.Chat;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    public Chat createChat(User reqUser, Integer revUserId) throws UserException;
    public Chat findChatById(Integer chatId) throws ChatException;
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
    public Chat createGroup(GroupChatRequest request,User reqUser) throws UserException;
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException;
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException;
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException;
    public void deleteChat(Integer chatId, Integer userId) throws UserException, ChatException;
}
