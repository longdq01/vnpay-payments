package com.payment.vnpay.service;

import com.payment.vnpay.config.Config;
import com.payment.vnpay.config.Constant;
import com.payment.vnpay.config.ParameterConstant;
import com.payment.vnpay.dto.request.CreatePaymentVNPAYReq;
import com.payment.vnpay.dto.response.CreatePaymentVNPAYRes;
import com.payment.vnpay.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final Config config;

    public ResponseEntity<CreatePaymentVNPAYRes> createPayment(CreatePaymentVNPAYReq request){
        log.info("Create payment send to vnpay with request: {}", request);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add(ParameterConstant.VNP_VERSION, Constant.VNP_VERSION);
        queryParams.add(ParameterConstant.VNP_COMMAND, Constant.VNP_COMMAND);
        queryParams.add(ParameterConstant.VNP_TMN_CODE, config.getVnpTmnCode());
        queryParams.add(ParameterConstant.VNP_AMOUNT, String.valueOf(request.getAmount() * 100));
        queryParams.add(ParameterConstant.VNP_BANK_CODE, request.getBankCode());
        queryParams.add(ParameterConstant.VNP_CREATE_DATE, Utils.currentPaymentCreateDate());
        queryParams.add(ParameterConstant.VNP_CURR_CODE, Constant.VNP_CURR_CODE);
        queryParams.add(ParameterConstant.VNP_IP_ADDR, "127.0.0.1");
        queryParams.add(ParameterConstant.VNP_LOCALE, request.getLocale());
        queryParams.add(ParameterConstant.VNP_ORDER_TYPE, request.getOrderType());
        queryParams.add(ParameterConstant.VNP_ORDER_INFO, request.getOrderInfo());
        queryParams.add(ParameterConstant.VNP_RETURN_URL, config.getVnpReturnUrl());
        queryParams.add(ParameterConstant.VNP_EXPIRE_DATE, Utils.currentPaymentExpireDate());
        queryParams.add(ParameterConstant.VNP_TXN_REF, Utils.getRandomNumber(8));
        // remove empty value because it will be ignored when sign data and should not represent in payment url
        queryParams.entrySet().removeIf(entry -> entry.getValue().isEmpty()
                || entry.getValue().getFirst() == null
                || entry.getValue().getFirst().isEmpty());
        queryParams.add(ParameterConstant.VNP_SECURE_HASH, Utils.signDataCreatePayment(queryParams, config.getVnpHashSecret()));

        String paymentUrl = UriComponentsBuilder.fromPath(config.getVnpUrl())
                .queryParams(queryParams)
                .toUriString();
        log.info("Create payment send to vnpay successfully with payment url: {}", paymentUrl);
        return ResponseEntity.ok(CreatePaymentVNPAYRes.builder()
                        .code("00")
                        .message("Success")
                        .data(paymentUrl)
                .build());
    }
}
