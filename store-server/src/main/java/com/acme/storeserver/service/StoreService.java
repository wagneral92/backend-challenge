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

    @Autowired
    public StoreService(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Store create(Store store) {
        return repository.save(store);
    }

    @Override
    public Store update(Store store, Long id) {
        Optional<Store> optionalStore = repository.findById(id);

        if(optionalStore.isPresent()){
           Store storeBase = optionalStore.get();

           storeBase.setName(store.getName());
           storeBase.setAddress(store.getAddress());

           return repository.save(storeBase);
        }

        return null;
    }

    @Override
    public Optional<Store> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Store> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
