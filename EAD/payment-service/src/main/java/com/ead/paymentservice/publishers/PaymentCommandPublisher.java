package com.ead.paymentservice.publishers;

import com.ead.paymentservice.dtos.PaymentCommandRecordDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

public class PaymentCommandPublisher {
    final RabbitTemplate rabbitTemplate;

    public PaymentCommandPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${ead.broker.exchange.paymentCommandExchange}")
    private String paymentCommandExchange;

    @Value(value = "${ead.broker.key.paymentCommandKey}")
    private String paymentCommandKey;

    public void publishPaymentCommand(PaymentCommandRecordDto paymentCommandRecordDto) {
        rabbitTemplate.convertAndSend(paymentCommandExchange, paymentCommandKey, paymentCommandRecordDto);
    }


}
