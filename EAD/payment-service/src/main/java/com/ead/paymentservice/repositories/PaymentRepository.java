package com.ead.paymentservice.repositories;

import com.ead.paymentservice.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentModel, UUID>, JpaSpecificationExecutor<PaymentModel> {
}
