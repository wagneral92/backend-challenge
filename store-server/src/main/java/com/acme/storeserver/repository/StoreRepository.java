package com.acme.storeserver.repository;

import com.acme.storeserver.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
