package thi.nguyen.whats_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thi.nguyen.whats_app.exception.ChatException;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.Message;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.request.SendMessageRequest;
import thi.nguyen.whats_app.response.ApiResponse;
import thi.nguyen.whats_app.service.MessageService;
import thi.nguyen.whats_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessagesHandler(@PathVariable("chatId") Integer chatId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User reqUser = userService.findUserProfile(jwt);

        List<Message> messages = messageService.getChatsMessages(chatId,reqUser);
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessagesHandler(@PathVariable("messageId") Integer messageId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User reqUser = userService.findUserProfile(jwt);

        messageService.getChatsMessages(messageId,reqUser);
        ApiResponse res = new ApiResponse("message deleted successfully",true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }
}
