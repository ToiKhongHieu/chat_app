package thi.nguyen.whats_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestController
public class GlobalException {

    @ExceptionHandler(UserException.class)//Xử lý ngoại lệ khi UserException ném ra - WebRequest : Cung cấp yêu cầu hiện tại
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageException.class)//Xử lý ngoại lệ khi UserException ném ra - WebRequest : Cung cấp yêu cầu hiện tại
    public ResponseEntity<ErrorDetail> messageExceptionHandler(MessageException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatException.class)//Xử lý ngoại lệ khi UserException ném ra - WebRequest : Cung cấp yêu cầu hiện tại
    public ResponseEntity<ErrorDetail> chatExceptionHandler(ChatException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }
}
