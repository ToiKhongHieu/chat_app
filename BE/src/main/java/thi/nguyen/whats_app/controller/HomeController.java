package thi.nguyen.whats_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> HomeController(){
        return new ResponseEntity<String>("Welcome to our websapp api using springboot", HttpStatus.OK);
    }
}
