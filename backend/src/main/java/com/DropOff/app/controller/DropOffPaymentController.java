package com.DropOff.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.DropOff.app.model.PaymentGatewayRequest;
import com.DropOff.app.model.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DropOffPaymentController{

    /**
     * Charge credit card.
     *
     * @author Shivam Lakhanpal
     * @param jsonString json string.
     * @return response entity.
     */
    @PostMapping("/DropOffPay")
    public ResponseEntity<PaymentResponse> chargeCreditCard(@RequestBody String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentGatewayRequest paymentRequest = objectMapper.readValue(jsonString, PaymentGatewayRequest.class);

            PaymentResponse response = new PaymentResponse();
            response.setStatus("Completed");
            response.setMessage("Amount Received");
            response.setTransactionId(UUID.randomUUID().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}