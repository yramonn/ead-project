package com.ead.paymentservice.repositories;

import com.ead.paymentservice.models.CreditCardModel;
import com.ead.paymentservice.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCardModel, UUID>, JpaSpecificationExecutor<CreditCardModel> {
    List<CreditCardModel> user(UserModel user);

    Optional<CreditCardModel> findByUser(UserModel userModel);
}
