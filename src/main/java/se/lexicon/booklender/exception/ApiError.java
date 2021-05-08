package se.lexicon.booklender.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class ApiError {
    private LocalDateTime timestamp;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private String message;
    private String debugMessage;
    private List<ApiValidationError> subErrors;
}

