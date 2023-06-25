package com.akatsuki.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${core.services.reservation-url}", value = "reservation-feign-client")
public interface ReservationFeignClient {

    @GetMapping("/check-host-reservations")
    boolean checkIfHostCanBeDeleted(@RequestHeader("Authorization") final String token);

    @GetMapping("/check-guest-reservations")
    boolean checkIfGuestCanBeDeleted(@RequestHeader("Authorization") final String token);
}

