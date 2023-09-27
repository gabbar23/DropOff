package com.DropOff.app.service;

import com.DropOff.app.model.Payment.DropOffPaymentGateway;
import com.DropOff.app.model.Payment.DropOffPaymentResponse;
import com.DropOff.app.utility.CustomException.CustomPaymentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentGatewayClient paymentGatewayClient;

    /**
     * PaymentService constructor
     *
     * 
     * @param paymentGatewayClient PaymentGatewayClient
     */
    @Autowired
    public PaymentService(PaymentGatewayClient paymentGatewayClient) {
        this.paymentGatewayClient = paymentGatewayClient;
    }

    /**
     * chargeCreditCard method is used to charge the credit card
     *
     * 
     * @param paymentRequest payment request
     * @return PaymentResponse
     * @throws CustomPaymentException payment gateway exception
     */
    public DropOffPaymentResponse chargeCreditCard(DropOffPaymentGateway paymentRequest) throws CustomPaymentException {
        try {
            if(paymentRequest == null || !validatePaymentRequest(paymentRequest)) {
                throw new CustomPaymentException("Unable to fetch card details.");
            }
            DropOffPaymentResponse paymentGatewayResponse = paymentGatewayClient.chargeCreditCard(paymentRequest);

            return mapPaymentGatewayResponse(paymentGatewayResponse);
        } catch (CustomPaymentException e) {
            throw new CustomPaymentException("Error processing payment: " + e.getMessage());
        }
    }

    /**
     * validatePaymentRequest method is used to validate the payment request
     *
     * 
     * @param paymentRequest payment request
     * @return PaymentGatewayRequest
     */
    private Boolean validatePaymentRequest(DropOffPaymentGateway paymentRequest) {
        return !paymentRequest.hasEmptyFields();
    }

    /**
     * createPaymentGatewayRequest method is used to create the payment gateway request
     *
     * 
     * @param paymentGatewayResponse payment gateway response
     * @return PaymentGatewayRequest
     */
    private DropOffPaymentResponse mapPaymentGatewayResponse(DropOffPaymentResponse paymentGatewayResponse) {
        DropOffPaymentResponse paymentResponse = new DropOffPaymentResponse();
        paymentResponse.setStatus(paymentGatewayResponse.getStatus());
        paymentResponse.setMessage(paymentGatewayResponse.getMessage());
        paymentResponse.setTransactionId(paymentGatewayResponse.getTransactionId());

        return paymentResponse;
    }
}

