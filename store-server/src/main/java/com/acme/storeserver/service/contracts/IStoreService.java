package com.acme.storeserver.service.contracts;

import com.acme.storeserver.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IStoreService {

    Store create(Store store);

    Store update(Store store, Long id);

    Optional<Store> findById(Long id);

    Page<Store> findAll(Pageable pageable);
}
