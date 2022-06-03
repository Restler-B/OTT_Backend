//package com.example.demo.exception;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.validation.ConstraintViolationException;
//
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class Exception_Handler {
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public  Map<String, String> errorArgument(MethodArgumentNotValidException exception){
//		Map<String, String> errorMap= new HashMap<>();
//		exception.getBindingResult().getFieldErrors().forEach(error ->{errorMap.put(error.getField(), error.getDefaultMessage());});
//	
//		return errorMap;
//	};
//	
//	@ExceptionHandler(ConstraintViolationException.class)
//	public  Map<String, String> errorArgument(ConstraintViolationException exception){
//		Map<String, String> errorMap= new HashMap<>();
//		exception.getConstraintViolations().forEach(error ->{errorMap.put(error.toString(), error.getMessage());});
//	
//		return errorMap;
//	};
//	
//	@ExceptionHandler(MovieNotFoundException.class)
//	public  Map<String, String> errorArgument(MovieNotFoundException exception){
//		Map<String, String> errorMap= new HashMap<>();
//		errorMap.put("errorMessage", exception.getMessage());
//		
//		return errorMap;
//	}
//}
