package com.DropOff.app.controller.Payment;

import com.DropOff.app.model.Payment.DropOffPaymentGateway;
import com.DropOff.app.model.Payment.DropOffPaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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
     * @param jsonString json string.
     * @return response entity.
     */
    @PostMapping("/DropOffPay")
    public ResponseEntity<DropOffPaymentResponse> chargeCreditCard(@RequestBody String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DropOffPaymentGateway paymentRequest = objectMapper.readValue(jsonString, DropOffPaymentGateway.class);

            DropOffPaymentResponse response = new DropOffPaymentResponse();
            response.setStatus("Completed");
            response.setMessage("Amount Received");
            response.setTransactionId(UUID.randomUUID().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}