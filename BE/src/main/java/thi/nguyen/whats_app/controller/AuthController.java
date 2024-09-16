package thi.nguyen.whats_app.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thi.nguyen.whats_app.Config.TokenProvider;
import thi.nguyen.whats_app.exception.UserException;
import thi.nguyen.whats_app.model.User;
import thi.nguyen.whats_app.repository.UserRepository;
import thi.nguyen.whats_app.request.LoginRequest;
import thi.nguyen.whats_app.response.AuthResponse;
import thi.nguyen.whats_app.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserService customUserService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String full_name = user.getFull_name();
        String password = user.getPassword();

        User isUser = userRepository.findByEmail(email);
        if (isUser != null) {
            throw new UserException("Email is used with another account " + email);
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFull_name(full_name);
        createdUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(jwt, true);
        return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws UserException {
        String email = req.getEmail();
        String password = req.getPassword();
        Authentication auth =  authentication(email,password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.generateToken(auth);
        AuthResponse res = new AuthResponse(jwt,true);
        return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
    }

    public Authentication authentication(String username, String password) throws UserException {
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UserException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password or username !!!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
