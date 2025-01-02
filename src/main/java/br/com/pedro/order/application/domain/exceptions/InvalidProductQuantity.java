package br.com.pedro.order.application.domain.exceptions;

public class InvalidProductQuantity extends RuntimeException{

    public InvalidProductQuantity(){
        super("Invalid product quantity");
    }
}
