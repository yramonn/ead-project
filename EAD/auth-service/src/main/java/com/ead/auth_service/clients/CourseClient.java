package com.ead.auth_service.clients;

import com.ead.auth_service.dtos.CourseRecordDto;
import com.ead.auth_service.dtos.ResponsePageDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component

public class CourseClient {

    Logger logger = LogManager.getLogger(CourseClient.class);

    @Value("${ead.api.url.course}")
    String baseUrlCourse;

    final RestClient restClient;

    public CourseClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

//    @Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseRecordDto> getAllCoursesByUser(UUID userId, Pageable pageable, String token){
        String url = baseUrlCourse + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
        logger.debug("Request URL: {} ", url);

        try {
            return restClient.get()
                    .uri(url)
                    .header("Authorization", token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDto<CourseRecordDto>>() {});

        } catch (HttpStatusCodeException e) {
            logger.error("Error Request RestClient with status: {}, cause: {} ", e.getStatusCode(), e.getMessage());
           switch(e.getStatusCode()) {
               case FORBIDDEN -> throw new AccessDeniedException("Forbidden");
               default -> throw new RuntimeException("Error Request RestClient", e);
           }
        } catch (RestClientException e) {
            logger.error("Error Request RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }

    }

    public Page<CourseRecordDto> retryFallback(UUID userId, Pageable pageable, Throwable t) {
        logger.error("Inside retryFallback, cause - {}", t.toString());
        List<CourseRecordDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult); //TODO improvement fallBack method
    }

    public Page<CourseRecordDto> circuitbreakerFallback(UUID userId, Pageable pageable, Throwable t) {
        logger.error("Inside circuitbreakerFallback, cause - {}", t.toString());
        List<CourseRecordDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult); //TODO improvement fallBack method
    }
}
