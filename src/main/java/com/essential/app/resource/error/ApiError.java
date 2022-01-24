package com.essential.app.resource.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
   private LocalDateTime timestamp;
   private HttpStatus status;
   private Object message;

   private ApiError() {
       timestamp = LocalDateTime.now();
   }

   public ApiError(HttpStatus status) {
       this();
       this.status = status;
   }

   public ApiError(HttpStatus status, Throwable ex) {
       this();
       this.status = status;
       this.message = ex.getLocalizedMessage();
   }

    public ApiError(HttpStatus status, Object message) {
        this();
        this.status = status;
        this.message = message;
    }

}