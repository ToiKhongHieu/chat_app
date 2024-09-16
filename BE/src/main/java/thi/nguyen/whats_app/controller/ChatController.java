package thi.nguyen.whats_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thi.nguyen.whats_app.exception.ChatException;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.Chat;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.request.GroupChatRequest;
import thi.nguyen.whats_app.request.SingleChatRequest;
import thi.nguyen.whats_app.response.ApiResponse;
import thi.nguyen.whats_app.service.ChatService;
import thi.nguyen.whats_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);
        Chat group = chatService.createGroup(groupChatRequest, reqUser);

        return new ResponseEntity<Chat>(group, HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        Chat chat = chatService.findChatById(chatId);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfile(jwt);

        List<Chat> chats = chatService.findAllChatByUserId(user.getId());

        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);

        Chat chat = chatService.addUserToGroup(userId, chatId, user);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromHandler(@PathVariable Integer chatId, @PathVariable Integer userId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);

        Chat chat = chatService.removeFromGroup(userId, chatId, user);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);

        chatService.deleteChat(chatId, user.getId());

        ApiResponse res = new ApiResponse("Chat is deleted successfully", true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
