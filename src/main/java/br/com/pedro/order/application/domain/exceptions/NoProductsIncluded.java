package br.com.pedro.order.application.domain.exceptions;

public class NoProductsIncluded extends RuntimeException{

    public NoProductsIncluded() {
        super("No products included in the order");
    }
}
