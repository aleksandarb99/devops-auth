package com.akatsuki.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${core.services.accommodation-url}", value = "accommodation-feign-client")
public interface AccommodationFeignClient {

    //    TODO: Send tokens and fix other microservices than
    @DeleteMapping("/by-host-id/{id}")
    void deleteAccommodationsByHostId(@PathVariable Long id);
}

