package com.DropOff.app.controller.Payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.DropOff.app.controller.Payment.PaymentController;
import com.DropOff.app.model.Payment.DropOffPaymentGateway;
import com.DropOff.app.model.Payment.DropOffPaymentResponse;
import com.DropOff.app.service.PaymentService;
import com.DropOff.app.utility.CustomException.CustomPaymentException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Test
    void testChargeCreditCard() throws JsonProcessingException, CustomPaymentException {
        // Setup
        DropOffPaymentGateway paymentRequest = new DropOffPaymentGateway();
        DropOffPaymentResponse paymentResponse = new DropOffPaymentResponse();

        PaymentService paymentServiceMock = Mockito.mock(PaymentService.class);
        when(paymentServiceMock.chargeCreditCard(Mockito.any(DropOffPaymentGateway.class))).thenReturn(paymentResponse);

        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        when(objectMapperMock.readValue(anyString(), eq(DropOffPaymentGateway.class))).thenReturn(paymentRequest);

        PaymentController controller = new PaymentController();
        controller.setPaymentService(paymentServiceMock);

        // Exercise
        String jsonString = "{}";
        ResponseEntity<?> responseEntity = controller.chargeCreditCard(jsonString);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentResponse, responseEntity.getBody());
    }

    @Test
    void testChargeCreditCardWithInvalidJson() throws JsonProcessingException {
        // Setup
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        when(objectMapperMock.readValue(anyString(), eq(DropOffPaymentGateway.class))).thenThrow(CustomPaymentException.class);

        PaymentController controller = new PaymentController();

        // Exercise
        String jsonString = "";
        ResponseEntity<?> responseEntity = controller.chargeCreditCard(jsonString);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testChargeCreditCardWithPaymentGatewayException() throws JsonProcessingException, CustomPaymentException {
        // Setup
        DropOffPaymentGateway paymentRequest = new DropOffPaymentGateway();

        PaymentService paymentServiceMock = Mockito.mock(PaymentService.class);
        when(paymentServiceMock.chargeCreditCard(paymentRequest)).thenThrow(CustomPaymentException.class);

        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        when(objectMapperMock.readValue(anyString(), eq(DropOffPaymentGateway.class))).thenReturn(paymentRequest);

        PaymentController controller = new PaymentController();
        controller.setPaymentService(paymentServiceMock);

        // Exercise
        String jsonString = "{InvalidJson}";
        ResponseEntity<?> responseEntity = controller.chargeCreditCard(jsonString);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}

