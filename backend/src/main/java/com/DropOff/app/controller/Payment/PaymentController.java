package com.DropOff.app.controller.Payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.DropOff.app.model.Payment.DropOffPaymentGateway;
import com.DropOff.app.model.Payment.DropOffPaymentResponse;
import com.DropOff.app.service.PaymentService;
import com.DropOff.app.utility.CustomException.CustomPaymentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {


    private PaymentService paymentService;

    /**
     * Set payment service.
     *
     * @param paymentService payment service.
     */
    @Autowired
    public void setPaymentService(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    /**
     * Charge credit card.

     * @param jsonString json string.
     * @return response entity.
     */
    @PostMapping("/charge")
    public ResponseEntity<?> chargeCreditCard(@RequestBody String jsonString) {
        try {
            if(jsonString.equals(""))
                throw new CustomPaymentException("Invalid Card Details");

            ObjectMapper objectMapper = new ObjectMapper();
            DropOffPaymentGateway paymentRequest = objectMapper.readValue(jsonString, DropOffPaymentGateway.class);

            DropOffPaymentResponse paymentResponse = paymentService.chargeCreditCard(paymentRequest);
            if(paymentResponse == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Failed");

            return ResponseEntity.ok(paymentResponse);
        } catch (CustomPaymentException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


