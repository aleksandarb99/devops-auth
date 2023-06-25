package com.akatsuki.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${core.services.accommodation-url}", value = "accommodation-feign-client")
public interface AccommodationFeignClient {

    @DeleteMapping("/by-host-id")
    void deleteAccommodationsByHostId(@RequestHeader("Authorization") final String token);
}

