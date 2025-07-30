package com.ead.paymentservice.services.impl;

import com.ead.paymentservice.enums.PaymentControl;
import com.ead.paymentservice.models.CreditCardModel;
import com.ead.paymentservice.models.PaymentModel;
import com.ead.paymentservice.services.PaymentStripeService;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class PaymentStripeProdServiceImpl implements PaymentStripeService {

    Logger logger = LogManager.getLogger(PaymentStripeProdServiceImpl.class);

    @Value(value = "${ead.stripe.secretKey.prod}")
    private String secretKeyStripe;

    @Override
    public PaymentModel processStripePayment(PaymentModel paymentModel, CreditCardModel creditCardModel) {
        Stripe.apiKey = secretKeyStripe;
        String paymentIntentId = null;

        try {
            // Step 01: Payment Intent
            var paramsPaymentIntent = PaymentIntentCreateParams.builder()
                    .setAmount(paymentModel.getValuePaid().multiply(new BigDecimal("100")).longValue())
                    .setCurrency("brl")
                    .addPaymentMethodType("card")
                    .build();
            var paymentIntent = PaymentIntent.create(paramsPaymentIntent);
            paymentIntentId = paymentIntent.getId();

            // Step 02: Payment Method
            var paramsPaymentMethod = PaymentMethodCreateParams.builder()
                    .setType(PaymentMethodCreateParams.Type.CARD)
                    .setCard(
                            PaymentMethodCreateParams.CardDetails.builder()
                                    .setNumber(creditCardModel.getCreditCardNumber().replaceAll(" ", ""))
                                    .setExpMonth(Long.parseLong(creditCardModel.getExpirationDate().split("/")[0]))
                                    .setExpYear(Long.parseLong(creditCardModel.getExpirationDate().split("/")[1]))
                                    .setCvc(creditCardModel.getCvvCode())
                                    .build()
                    )
                    .setBillingDetails(
                            PaymentMethodCreateParams.BillingDetails.builder().setName(creditCardModel.getCardHolderFullName()).build()
                    )
                    .build();
            var paymentMethod = PaymentMethod.create(paramsPaymentMethod);

            // Step 03: Payment Confirm
            var paramsPaymentConfirm = PaymentIntentConfirmParams.builder()
                    .setPaymentMethod(paymentMethod.getId())
                    .build();
            var confirmPaymentIntent = paymentIntent.confirm(paramsPaymentConfirm);

            if(confirmPaymentIntent.getStatus().equals("succeeded")) {
                paymentModel.setPaymentControl(PaymentControl.EFFECTED);
                paymentModel.setPaymentMessage("payment effected - paymentIntent: " + paymentIntentId);
                paymentModel.setPaymentCompletionDate(LocalDateTime.now(ZoneId.of("UTC")));
            } else{
                paymentModel.setPaymentControl(PaymentControl.ERROR);
                paymentModel.setPaymentMessage("payment error v1 - paymentIntent: " + paymentIntentId);
            }

        } catch (CardException cardException) {
            logger.error("A payment error occurred: {}", cardException.getMessage());
            try {
                paymentModel.setPaymentControl(PaymentControl.REFUSED);
                var paymentIntent = PaymentIntent.retrieve(paymentIntentId);
                paymentModel.setPaymentMessage("payment refused v1 - paymentIntent: " + paymentIntentId +
                        ", cause: " + paymentIntent.getLastPaymentError().getCode() +
                        ", message: " + paymentIntent.getLastPaymentError().getMessage());
            } catch (Exception exception) {
                logger.error("Another problem occurred, maybe unrelated to Stripe, with cause: {} ", exception.getMessage());
                paymentModel.setPaymentMessage("payment refused v2 - paymentIntent: " + paymentIntentId);
            }
        } catch (Exception exception) {
            logger.error("Another problem occurred, maybe unrelated to Stripe, with cause: {} ", exception.getMessage());
            paymentModel.setPaymentControl(PaymentControl.ERROR);
            paymentModel.setPaymentMessage("payment error v2 - paymentIntent: " + paymentIntentId);

        }

        return paymentModel;
    }
}
