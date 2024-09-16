package thi.nguyen.whats_app.service;

import thi.nguyen.whats_app.exception.ChatException;
import thi.nguyen.whats_app.exception.MessageException;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.Message;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;

    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException;

    public Message findMessageById(Integer messageId) throws MessageException;

    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;
}
