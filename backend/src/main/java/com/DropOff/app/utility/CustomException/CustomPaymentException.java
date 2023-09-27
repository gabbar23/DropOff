package com.DropOff.app.utility.CustomException;

public class CustomPaymentException extends RuntimeException {

    /**
     * Constructor for PaymentGatewayException.
     *
     * 
     * @param message message.
     */
    public CustomPaymentException(String message) {
        super(message);
    }
}

