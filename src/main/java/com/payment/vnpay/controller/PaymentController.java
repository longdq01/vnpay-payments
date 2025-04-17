package com.payment.vnpay.controller;

import com.payment.vnpay.dto.request.CreatePaymentVNPAYReq;
import com.payment.vnpay.dto.response.CreatePaymentVNPAYRes;
import com.payment.vnpay.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<CreatePaymentVNPAYRes> doPayment(@Valid @RequestBody CreatePaymentVNPAYReq request) {
        return paymentService.createPayment(request);
    }
}
