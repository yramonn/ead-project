package com.ead.paymentservice.specifications;

import com.ead.paymentservice.models.PaymentModel;
import com.ead.paymentservice.models.UserModel;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;


public class SpecificationTemplate {

        @And({
                @Spec(path = "paymentControl", spec = Equal.class),
                @Spec(path = "valuePaid", spec = Equal.class),
                @Spec(path = "lastDigitsCreditCard", spec = Like.class),
                @Spec(path = "paymentMessage", spec = Like.class)
        })
        public interface PaymentSpec extends Specification<PaymentModel> {}

        public static Specification<PaymentModel> paymentUserId(final UUID userId) {
                return (root, query, cb) -> {
                        query.distinct(true);
                        Root<PaymentModel> payment = root;
                        Root<UserModel> user = query.from(UserModel.class);
                        Expression<Collection<PaymentModel>> usersPayments = user.get("payments");
                        return cb.and(cb.equal(user.get("userId"), userId), cb.isMember(payment, usersPayments));
                };
        }



}

