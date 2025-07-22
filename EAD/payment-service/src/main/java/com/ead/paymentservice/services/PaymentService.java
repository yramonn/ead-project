package com.ead.paymentservice.services;

import com.ead.paymentservice.dtos.PaymentRequestRecordDto;
import com.ead.paymentservice.models.PaymentModel;
import com.ead.paymentservice.models.UserModel;

import java.util.Optional;

public interface PaymentService {

    PaymentModel requestPayment(PaymentRequestRecordDto paymentRequestRecordDto, UserModel userModel);

    Optional<PaymentModel> findLastPaymentUser(UserModel userModel);
}
