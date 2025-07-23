package com.ead.paymentservice.services;

import com.ead.paymentservice.models.CreditCardModel;
import com.ead.paymentservice.models.PaymentModel;

public interface PaymentStripeService {

    PaymentModel processStripePayment(PaymentModel paymentModel, CreditCardModel creditCardModel);
}
