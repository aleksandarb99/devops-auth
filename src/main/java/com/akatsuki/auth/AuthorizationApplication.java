package com.akatsuki.auth;

import com.akatsuki.auth.feignclients.AccommodationFeignClient;
import com.akatsuki.auth.feignclients.ReservationFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = {AccommodationFeignClient.class, ReservationFeignClient.class})
public class AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
