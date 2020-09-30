package com.epam.shop;

import com.epam.shop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{orderIdentifier}/status")
    public String retrievePaymentStatus(@PathVariable String orderIdentifier) {
        return paymentService.findPaymentStatus(orderIdentifier);
    }
}
