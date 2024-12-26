package com.richlife.topic.RichLife_Pharma.exception;

import com.richlife.topic.RichLife_Pharma.dto.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Bắt các RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseData<?>> handleRuntimeException(RuntimeException ex) {
        // Tạo đối tượng ResponseData với thông điệp lỗi chi tiết từ RuntimeException
        ResponseData<?> responseData = ResponseData.error(ex.getMessage());
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    // Bắt các lỗi liên quan đến các lỗi validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Lấy tất cả các lỗi từ BindingResult
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        // Duyệt qua tất cả các lỗi từ BindingResult và lưu vào Map
        for (FieldError fieldError : result.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Tạo ResponseData với các lỗi chi tiết
        ResponseData<?> responseData = ResponseData.error("Validation failed", errors);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }


    // Bắt các ngoại lệ chung (Exception)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<?>> handleGeneralException(Exception ex) {
        // Trả về thông điệp lỗi chung cho tất cả các ngoại lệ không xác định trước
        ResponseData<?> responseData = ResponseData.error("An error occurred", ex.getMessage());
        return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}