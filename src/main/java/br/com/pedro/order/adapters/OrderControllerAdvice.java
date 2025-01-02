package br.com.pedro.order.adapters;

import br.com.pedro.order.application.domain.exceptions.InvalidProductQuantity;
import br.com.pedro.order.application.domain.exceptions.NoProductsIncluded;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(InvalidProductQuantity.class)
    public ResponseEntity<String> handleInvalidProductQuantity(InvalidProductQuantity ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NoProductsIncluded.class)
    public ResponseEntity<String> handleInvalidOrderException(NoProductsIncluded ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
