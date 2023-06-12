package com.akatsuki.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${core.services.reservation-url}", value = "reservation-feign-client")
public interface ReservationFeignClient {


}

