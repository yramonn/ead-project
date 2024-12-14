package com.ead.auth_service.specifications;

import com.ead.auth_service.models.UserCourseModel;
import com.ead.auth_service.models.UserModel;
import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

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

        public static Specification<UserModel> userCourseId(final UUID courseId) {
                return (root, query, cb) -> {
                        query.distinct(true);
                        Join<UserModel, UserCourseModel> userJoin = root.join("usersCourses");
                        return cb.equal(userJoin.get("courseId"), courseId);
                };
        }
}
