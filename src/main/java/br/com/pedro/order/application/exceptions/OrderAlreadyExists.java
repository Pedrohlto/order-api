package br.com.pedro.order.application.exceptions;

public class OrderAlreadyExists extends RuntimeException {
    public OrderAlreadyExists(String message) {
        super(message);
    }
}
