package thi.nguyen.whats_app.service;

import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.request.UpdateUserRequest;

import java.util.List;


public interface UserService {
    public User findUserById(Integer id) throws UserException;
    public User findUserProfile(String jwt) throws UserException;
    public User udpateUser(Integer id, UpdateUserRequest req) throws UserException;
    public List<User> searchUser(String query);
}
