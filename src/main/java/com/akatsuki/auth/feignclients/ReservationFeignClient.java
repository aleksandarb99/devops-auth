package com.akatsuki.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${core.services.reservation-url}", value = "reservation-feign-client")
public interface ReservationFeignClient {

    @GetMapping("/check-host-reservations/{hostId}")
    boolean checkIfHostCanBeDeleted(@PathVariable("hostId") Long hostId);

    @GetMapping("/check-guest-reservations/{guestId}")
    boolean checkIfGuestCanBeDeleted(@PathVariable("guestId") Long guestId);
}

