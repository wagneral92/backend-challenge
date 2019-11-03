package com.acme.storeserver.service;

import com.acme.storeserver.model.Store;
import com.acme.storeserver.repository.StoreRepository;
import com.acme.storeserver.service.contracts.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreService implements IStoreService {

    private final StoreRepository repository;

    /**
     *
     * @param repository
     */
    @Autowired
    public StoreService(StoreRepository repository) {
        this.repository = repository;
    }

    /**
     *
     * @param store
     * @return
     */
    @Override
    public Store create(final Store store) {
        return repository.save(store);
    }

    /**
     *
     * @param store
     * @param id
     * @return
     */
    @Override
    public Store update(final Store store, final Long id) {
        Optional<Store> optionalStore = repository.findById(id);

        if (optionalStore.isPresent()) {
            Store storeBase = optionalStore.get();

            storeBase.setName(store.getName());
            storeBase.setAddress(store.getAddress());

            return repository.save(storeBase);
        }

        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Store> findById(final Long id) {
        return repository.findById(id);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Store> findAll(final Pageable pageable, final String search) {
        return repository.findAllSearch(pageable, search);
    }
}
