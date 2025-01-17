package com.ead.courseservice.clients;

import com.ead.courseservice.dtos.CourseUserRecordDto;
import com.ead.courseservice.dtos.ResponsePageDto;
import com.ead.courseservice.dtos.UserRecordDto;
import com.ead.courseservice.exceptions.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
public class AuthUserClient {

    Logger logger = LogManager.getLogger(AuthUserClient.class);

    @Value("${ead.api.url.authuser}")
    String baseUrlAuthUser;

    final RestClient restClient;

    public AuthUserClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Page<UserRecordDto> getAllUsersByCourse(UUID courseId, Pageable pageable){
        String url = baseUrlAuthUser + "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
        logger.debug("Request URL: {} ", url);

        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDto<UserRecordDto>>() {});

        } catch (RestClientException e) {
            logger.error("Error Request RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }

    }

    public ResponseEntity<UserRecordDto> getOneUserById(UUID userId) {
        String url = baseUrlAuthUser + "/users/" + userId;
        logger.debug("Request URL: {} ", url);

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(status -> status.value() == 404, (request, response) -> {
                        logger.error("Error> User not found: {} ", userId);
                        throw new NotFoundException("Error: User not found.");
                    })
                    .toEntity(UserRecordDto.class);
    }

    public void postSubscriptionUserInCourse(UUID userId, UUID courseId) {
        String url = baseUrlAuthUser + "/users/" + userId + "/courses/subscription";
        logger.debug("Request URL: {} ", url);

        try {
            var courseUserRecordDto = new CourseUserRecordDto(courseId, userId);
             restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(courseUserRecordDto)
                    .retrieve()
                     .toBodilessEntity();

        } catch(RestClientException e) {
            logger.error("Error Request POST RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request POST RestClient", e);
        }
    }

    public void deleteCourseUserInAuthUser(UUID courseId) {
        String url = baseUrlAuthUser + "/users/courses/" + courseId;
        logger.debug("Request URL: {} ", url);

        try {
            restClient.delete()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity();
        } catch(RestClientException e) {
            logger.error("Error Request DELETE RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request DELETE RestClient", e);
        }
    }
}
