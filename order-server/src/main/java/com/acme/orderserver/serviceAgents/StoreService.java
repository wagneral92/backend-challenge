package com.acme.orderserver.serviceAgents;

import com.acme.orderserver.serviceAgents.model.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "store-server")
public interface StoreService {

    @RequestMapping("/store/{id}")
    Store getStoreById(@PathVariable Long id);
}
