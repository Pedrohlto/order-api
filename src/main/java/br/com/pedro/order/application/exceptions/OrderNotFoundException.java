package br.com.pedro.order.application.exceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException (){
        super("Order not found");
    }
}
