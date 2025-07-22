package com.ead.paymentservice.dtos;

import java.util.UUID;

public record PaymentCommandRecordDto(UUID userId,
                                      UUID paymentId,
                                      UUID cardId) {
}
