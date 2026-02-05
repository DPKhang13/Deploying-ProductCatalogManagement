package com.example.project.config;

import com.example.project.dto.response.ApiResponse;
import com.example.project.exception.category.CategoryAlreadyExistsException;
import com.example.project.exception.category.CategoryInUseException;
import com.example.project.exception.category.CategoryNotFoundException;
import com.example.project.exception.category.UnauthorizedException;
import com.example.project.exception.product.DuplicateProductException;
import com.example.project.exception.product.InvalidProductDataException;
import com.example.project.exception.product.ProductNotFoundException;
import com.example.project.exception.user.BadRequestException;
import com.example.project.exception.user.DuplicateUserException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err ->
                        errors.put(err.getField(), err.getDefaultMessage())
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(errors, "VALIDATION_ERROR")
                );
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(v ->
                errors.put(
                        v.getPropertyPath().toString(),
                        v.getMessage()
                )
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(errors, "VALIDATION_ERROR")
                );
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateUser(DuplicateUserException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "DUPLICATE_USER"
                ));
    }



    // Auth
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "UNAUTHORIZED"
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "ACCESS_DENIED"
                ));
    }

    // Category
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCategoryNotFound(CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "CATEGORY_NOT_FOUND"
                ));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleCategoryAlreadyExists(CategoryAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "CATEGORY_ALREADY_EXISTS"
                ));
    }

    @ExceptionHandler(CategoryInUseException.class)
    public ResponseEntity<ApiResponse<Void>> handleCategoryInUse(CategoryInUseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "CATEGORY_IN_USE"
                ));
    }


    //Product
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "PRODUCT_NOT_FOUND"
                ));
    }

    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidProductData(InvalidProductDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "INVALID_PRODUCT_DATA"
                ));
    }

    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateProduct(DuplicateProductException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "DUPLICATE_PRODUCT"
                ));
    }

    /* ================= COMMON ================= */

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "BAD_REQUEST"
                ));
    }

    /* ================= FALLBACK ================= */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        "Internal server error",
                        "INTERNAL_SERVER_ERROR"
                ));
    }
}
