package com.acme.orderserver.serviceAgents.fallBack;

import com.acme.orderserver.serviceAgents.StoreService;
import com.acme.orderserver.serviceAgents.model.Store;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StoreServiceFallBack implements StoreService {
    @Override
    public Store getStoreById(Long id) {
        return null;
    }
}
