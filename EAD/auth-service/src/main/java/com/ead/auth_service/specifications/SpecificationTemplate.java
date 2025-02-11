package com.ead.auth_service.specifications;

import com.ead.auth_service.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

        @And({
                @Spec(path = "userType", spec = Equal.class),
                @Spec(path = "userStatus", spec = Equal.class),
                @Spec(path = "email", spec = Like.class),
                @Spec(path = "username", spec = Like.class),
                @Spec(path = "fullName", spec = LikeIgnoreCase.class)
        })
        public interface UserSpec extends Specification<UserModel> {
        }
}
