package com.croco.dispatcherdbcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.croco.dispatcherdbcontroller.entity")
@EnableJpaRepositories(basePackages = {"com.croco.dispatcherdbcontroller.repository"})
public class DispatcherDbControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatcherDbControllerApplication.class, args);
    }

}
