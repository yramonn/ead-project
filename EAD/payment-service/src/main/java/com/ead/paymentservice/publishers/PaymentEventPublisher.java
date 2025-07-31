package com.ead.paymentservice.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ead.payment.dtos.PaymentEventDto;
@Component
public class PaymentEventPublisher {

    final RabbitTemplate rabbitTemplate;

    public PaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${ead.broker.exchange.paymentEventExchange}")
    private String exchangePaymentEvent;

    public void publishPaymentEvent(PaymentEventDto paymentEventDto) {
        rabbitTemplate.convertAndSend(exchangePaymentEvent, "", paymentEventDto);
    }
}
