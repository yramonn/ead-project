package com.ead.paymentservice.services.impl;

import com.ead.paymentservice.dtos.PaymentRequestRecordDto;
import com.ead.paymentservice.enums.PaymentControl;
import com.ead.paymentservice.models.CreditCardModel;
import com.ead.paymentservice.models.PaymentModel;
import com.ead.paymentservice.models.UserModel;
import com.ead.paymentservice.repositories.CreditCardRepository;
import com.ead.paymentservice.repositories.PaymentRepository;
import com.ead.paymentservice.repositories.UserRepository;
import com.ead.paymentservice.services.PaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

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

    @Transactional
    @Override
    public PaymentModel requestPayment(PaymentRequestRecordDto paymentRequestRecordDto, UserModel userModel) {

        var creditCardModel  = creditCardRepository
                .findByUser(userModel)
                        .orElseGet(CreditCardModel::new);

        BeanUtils.copyProperties(paymentRequestRecordDto, creditCardModel);
        creditCardModel.setUser(userModel);
        creditCardRepository.save(creditCardModel);

        var paymentModel = new PaymentModel();
        paymentModel.setPaymentControl(PaymentControl.REQUESTED);
        paymentModel.setPaymentRequestDate(LocalDateTime.now(ZoneId.of("UTC")));
        paymentModel.setPaymentExpirationDate(LocalDateTime.now(ZoneId.of("UTC")).plusMonths(12));
        paymentModel.setLastDigitsCreditCard(paymentRequestRecordDto.creditCardNumber().substring(paymentRequestRecordDto.creditCardNumber().length()-4));
        paymentModel.setValuePaid(paymentRequestRecordDto.valuePaid());
        paymentModel.setUser(userModel);
        paymentRepository.save(paymentModel);

        //send request to queue

        return paymentModel;
    }

    @Override
    public Optional<PaymentModel> findLastPaymentUser(UserModel userModel) {
       return paymentRepository.findTopByUserOrderByPaymentRequestDateDesc(userModel);
    }
}
