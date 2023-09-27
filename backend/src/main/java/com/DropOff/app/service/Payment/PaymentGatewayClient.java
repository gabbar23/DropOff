package com.DropOff.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.DropOff.app.model.Payment.DropOffPaymentGateway;
import com.DropOff.app.model.Payment.DropOffPaymentResponse;
import com.DropOff.app.utility.CustomException.CustomPaymentException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentGatewayClient {
    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;
    private ResponseEntity<String> responseEntity;
    private RestTemplate restTemplate;

    /**
     * setRestTemplate method is used to set the restTemplate
     *
     * 
     * @param restTemplate RestTemplate
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * setPaymentGatewayUrl method is used to set the payment gateway url
     *
     * 
     * @param paymentGatewayUrl payment gateway url
     */
    public void setPaymentGatewayUrl(String paymentGatewayUrl) {
        this.paymentGatewayUrl = paymentGatewayUrl;
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
        DropOffPaymentGateway paymentGatewayRequest = createPaymentGatewayRequest(paymentRequest);
        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(paymentGatewayRequest);
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
            HttpMethod httpMethod = HttpMethod.POST;
            responseEntity = restTemplate.exchange(paymentGatewayUrl, httpMethod, requestEntity, String.class);
            DropOffPaymentResponse paymentResponse = objectMapper.readValue(responseEntity.getBody(), DropOffPaymentResponse.class);
            return paymentResponse;
        } catch (Exception e) {
            throw new CustomPaymentException("Error processing payment: " + e.getMessage());
        }
    }

    /**
     * createPaymentGatewayRequest method is used to create the payment gateway request
     *
     * 
     * @param paymentRequest payment request
     * @return PaymentGatewayRequest
     */
    private DropOffPaymentGateway createPaymentGatewayRequest(DropOffPaymentGateway paymentRequest) {
        DropOffPaymentGateway paymentGatewayRequest = new DropOffPaymentGateway();
        paymentGatewayRequest.setAmount(paymentRequest.getAmount());
        paymentGatewayRequest.setCurrency(paymentRequest.getCurrency());
        paymentGatewayRequest.setCardNumber(paymentRequest.getCardNumber());
        paymentGatewayRequest.setCardExpiryMonth(paymentRequest.getCardExpiryMonth());
        paymentGatewayRequest.setCardExpiryYear(paymentRequest.getCardExpiryYear());
        paymentGatewayRequest.setCardCvv(paymentRequest.getCardCvv());
        paymentGatewayRequest.setCardHolderName(paymentRequest.getCardHolderName());
        return paymentGatewayRequest;
    }
}
