package com.acme.storeserver.service.contracts;

import com.acme.storeserver.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IStoreService {

    Store create(final Store store);

    Store update(final Store store, final Long id);

    Optional<Store> findById(final Long id);

    Page<Store> findAll(final Pageable pageable, final String search);
}
