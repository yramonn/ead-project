package com.ead.paymentservice.services;

import com.ead.paymentservice.dtos.PaymentCommandRecordDto;
import com.ead.paymentservice.dtos.PaymentRequestRecordDto;
import com.ead.paymentservice.models.PaymentModel;
import com.ead.paymentservice.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public interface PaymentService {

    PaymentModel requestPayment(PaymentRequestRecordDto paymentRequestRecordDto, UserModel userModel);

    Optional<PaymentModel> findLastPaymentUser(UserModel userModel);

    Page<PaymentModel> findAllByUser(Specification<PaymentModel> and, Pageable pageable);

    Optional<PaymentModel> findPaymentByUser(UUID userId, UUID paymentId);
    void makePayment(PaymentCommandRecordDto paymentCommandRecordDto);
}
