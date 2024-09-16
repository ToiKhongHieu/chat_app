package thi.nguyen.whats_app.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thi.nguyen.whats_app.exception.ChatException;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.Chat;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.repository.ChatRepository;
import thi.nguyen.whats_app.request.GroupChatRequest;
import thi.nguyen.whats_app.service.ChatService;
import thi.nguyen.whats_app.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer revUserId) throws UserException {
        User revUser = userService.findUserById(revUserId);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(revUser, reqUser);

        if (isChatExist != null) {
            return isChatExist;
        }
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(reqUser);
        chat.getUsers().add(revUser);
        chat.setGroup(false);
        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (!chat.isPresent()) {
            throw new ChatException("Chat not found with id " + chatId);
        }
        return chat.get();
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(user.getId());
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest request, User reqUser) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChat_image(request.getChat_image());
        group.setChat_name(request.getChat_name());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);

        for (Integer userId : request.getUserIds()) {
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }
        return group;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getAdmins().contains(reqUser)) {
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            throw new UserException("You can't remove another user !!!");
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getUsers().contains(reqUser)) {
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }
            throw new UserException("You are not member of this group");
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getAdmins().contains(reqUser) || chat.getUsers().contains(user)) {
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {
                if (user.getId().equals(reqUser)) {
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
            }
            throw new UserException("You can't remove another user !!!");
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            chatRepository.deleteById(chatId);
        }
    }
}
