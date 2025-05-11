package aiss.bitbucketminer1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class BitBucketMinerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        Map<String, List<String>> res = new HashMap<>();
        res.put("errors", errors);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    // Project not found exception
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Project not found")
    public static class ProjectNotFoundException extends RuntimeException {}

    // Commit not found exception
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Commit not found")
    public static class CommitNotFoundException extends RuntimeException {}

    // Comment not found exception
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Comment not found")
    public static class CommentNotFoundException extends RuntimeException {}

    // Issue not found exception
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Issue not found")
    public static class IssueNotFoundException extends Exception {}
}