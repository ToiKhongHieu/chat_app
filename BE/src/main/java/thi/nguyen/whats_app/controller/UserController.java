package thi.nguyen.whats_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.request.UpdateUserRequest;
import thi.nguyen.whats_app.response.ApiResponse;
import thi.nguyen.whats_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest request, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfile(jwt);
        userService.udpateUser(user.getId(), request);
        ApiResponse response = new ApiResponse("User updated successfully", true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
    }
}
