package com.ead.paymentservice.services.impl;

import com.ead.paymentservice.repositories.CreditCardRepository;
import com.ead.paymentservice.repositories.PaymentRepository;
import com.ead.paymentservice.repositories.UserRepository;
import com.ead.paymentservice.services.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    final PaymentRepository paymentRepository;
    final UserRepository userRepository;
    final CreditCardRepository creditCardRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, CreditCardRepository creditCardRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
    }
}
