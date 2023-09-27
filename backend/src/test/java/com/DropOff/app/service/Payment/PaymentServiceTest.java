package com.DropOff.app.service;

import com.DropOff.app.model.Payment.DropOffPaymentGateway;
import com.DropOff.app.model.Payment.DropOffPaymentResponse;
import com.DropOff.app.utility.CustomException.CustomPaymentException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentGatewayClient paymentGatewayClient;

    private PaymentService paymentService;

    private final int TEST_AMOUNT = 1223;

    final private int TEST_CARD_EXPIRY_MONTH = 10;
    final private int TEST_CARD_EXPIRY_YEAR = 2028;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentGatewayClient);
    }

    @Test
    void testChargeCreditCard_Success() throws CustomPaymentException {
        // Arrange
        DropOffPaymentGateway paymentRequest = new DropOffPaymentGateway();
        paymentRequest.setCardHolderName("Stuart Clark");
        paymentRequest.setCardNumber("4111111111221111");
        paymentRequest.setCardCvv("457");
        paymentRequest.setCurrency("100.0");
        paymentRequest.setAmount(new BigDecimal(TEST_AMOUNT));
        paymentRequest.setCardExpiryMonth(TEST_CARD_EXPIRY_MONTH);
        paymentRequest.setCardExpiryYear(TEST_CARD_EXPIRY_YEAR);
        DropOffPaymentResponse paymentGatewayResponse = new DropOffPaymentResponse();
        paymentGatewayResponse.setStatus("success");
        paymentGatewayResponse.setMessage("Payment processed successfully");
        paymentGatewayResponse.setTransactionId("123456789");

        when(paymentGatewayClient.chargeCreditCard(paymentRequest)).thenReturn(paymentGatewayResponse);

        // Act
        DropOffPaymentResponse paymentResponse = paymentService.chargeCreditCard(paymentRequest);

        // Assert
        assertNotNull(paymentResponse);
        assertEquals("success", paymentResponse.getStatus());
        assertEquals("Payment processed successfully", paymentResponse.getMessage());
        assertEquals("123456789", paymentResponse.getTransactionId());
    }

    @Test
    void testChargeCreditCard_NullRequest() throws CustomPaymentException {
        // Arrange
        DropOffPaymentGateway paymentRequest = null;

        // Act & Assert
        assertThrows(CustomPaymentException.class, () -> paymentService.chargeCreditCard(paymentRequest));
    }

    @Test
    void testChargeCreditCard_EmptyRequestFields() throws CustomPaymentException {
        // Arrange
        DropOffPaymentGateway paymentRequest = new DropOffPaymentGateway();

        // Act & Assert
        assertThrows(CustomPaymentException.class, () -> paymentService.chargeCreditCard(paymentRequest));
    }

    @Test
    void testChargeCreditCard_FailedPaymentWithInvalidCardDetails() throws CustomPaymentException {
        // Arrange
        DropOffPaymentGateway paymentRequest = new DropOffPaymentGateway();
        paymentRequest.setCardHolderName("John Doe");
        paymentRequest.setCardNumber("4111111111111111");
        paymentRequest.setCardCvv("123");
        paymentRequest.setAmount(new BigDecimal("10.0"));
        DropOffPaymentResponse paymentGatewayResponse = new DropOffPaymentResponse();
        paymentGatewayResponse.setStatus("failure");
        paymentGatewayResponse.setMessage("Payment failed");

        when(paymentGatewayClient.chargeCreditCard(paymentRequest)).thenReturn(paymentGatewayResponse);

        // Act & Assert
        assertThrows(CustomPaymentException.class, () -> paymentService.chargeCreditCard(paymentRequest));
    }

    @Test
    void testChargeCreditCard_ExceptionThrownByPaymentGatewayClient() throws CustomPaymentException {
        // Arrange
        DropOffPaymentGateway paymentRequest = new DropOffPaymentGateway();
        paymentRequest.setCardHolderName("Stuart Clark");
        paymentRequest.setCardNumber("4111111111221111");
        paymentRequest.setCardCvv("457");
        paymentRequest.setCurrency("100.0");
        paymentRequest.setAmount(new BigDecimal(TEST_AMOUNT));
        paymentRequest.setCardExpiryMonth(TEST_CARD_EXPIRY_MONTH);
        paymentRequest.setCardExpiryYear(TEST_CARD_EXPIRY_YEAR);

        when(paymentGatewayClient.chargeCreditCard(paymentRequest)).thenThrow(new CustomPaymentException("Something went wrong"));

        // Act & Assert
        assertThrows(CustomPaymentException.class, () -> {
            DropOffPaymentResponse paymentResponse = paymentService.chargeCreditCard(paymentRequest);
        });
    }
}