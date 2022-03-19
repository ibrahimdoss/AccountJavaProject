package com.SpringBoot.account.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundExceptionHandler(CustomerNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

/*validation da api ye bağlı oluypr. @Valid eklediğimizde requestlerin hepsinde validasyon işlemleri başlar
validasyon işlemine ters durum olursa RestControllerAdvice burada devreye giriyor tekrar. Yine akışı keserek aynı
işlemleri valid ile kuruyor.*/


/*@ExceptionHandler vermiş olduğumuz exception durumu oluştuğunda normal şartlarda service de alınan bi hata ilerleyerek
controller kısmı hatayı döner ama burada restcontrolleradvice flowu yani akışı keserek hatayı alıyor controllera uğramadan
kullanıcıya mevcut durumdaki http status kodunu dönderiyor. Pcdeki exception image gibi.*/

//@RestControllerAdvice uygulama içerisinde throw ettiğimiz bütün exceptionları yakalayıp http response üretiyor.