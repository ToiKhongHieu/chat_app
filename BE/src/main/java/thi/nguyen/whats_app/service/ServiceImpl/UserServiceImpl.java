package thi.nguyen.whats_app.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import thi.nguyen.whats_app.Config.TokenProvider;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.repository.UserRepository;
import thi.nguyen.whats_app.request.UpdateUserRequest;
import thi.nguyen.whats_app.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id " + id);
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email = tokenProvider.getEmailFromToken(jwt);
        if (email == null) {
            throw new BadCredentialsException("received invalid token---");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("user not found with email " + email);
        }
        return user;
    }

    @Override
    public User udpateUser(Integer id, UpdateUserRequest req) throws UserException {
        User user = findUserById(id);
        if(req.getFull_name() != null){
            user.setFull_name(req.getFull_name());
        }
        if(req.getProfile_picture()!=null){
            user.setProfile_picture(req.getProfile_picture());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users;
    }
}
