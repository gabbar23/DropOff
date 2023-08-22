package com.DropOff.app.model;

/**
 * PaymentGatewayRequest model.
 *
 * @author Shivam Lakhanpal
 */
public class PaymentResponse {

    private String status;
    private String transactionId;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
