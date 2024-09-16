package thi.nguyen.whats_app.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thi.nguyen.whats_app.exception.ChatException;
import thi.nguyen.whats_app.exception.MessageException;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.Chat;
import thi.nguyen.whats_app.model.Message;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.repository.MessageRepository;
import thi.nguyen.whats_app.request.SendMessageRequest;
import thi.nguyen.whats_app.service.ChatService;
import thi.nguyen.whats_app.service.MessageService;
import thi.nguyen.whats_app.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);

        if (!chat.getUsers().contains(reqUser)) {
            throw new UserException("You are not releted to this chat " + chat.getId());
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return null;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new MessageException("message not found with id " + messageId);
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
        Message message = findMessageById(messageId);
        if (message.getUser().getId().equals(reqUser.getId())) {
            messageRepository.deleteById(messageId);
        }
        throw new UserException("You can delete another user's messages " + reqUser.getFull_name());
    }
}
