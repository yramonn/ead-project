package com.ead.notificationservice.adapters.configs;

import com.ead.notificationservice.NotificationServiceApplication;
import com.ead.notificationservice.core.ports.NotificationPersistencePort;
import com.ead.notificationservice.core.services.NotificationServicePortImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = NotificationServiceApplication.class)
public class BeanConfiguration {

    @Bean
    NotificationServicePortImpl notificationServicePort(NotificationPersistencePort persistence) {
        return new NotificationServicePortImpl(persistence);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
