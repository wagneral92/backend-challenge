package com.acme.storeserver.service;

import com.acme.storeserver.model.Store;
import com.acme.storeserver.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository repository;

    @InjectMocks
    private StoreService service;

    private Store store;

    public StoreServiceTest(){
        this.store = new Store(null, "loja 01", "address 01");
    }

    @Test
    public void createStoreSuccess() {
        final Store responseStore = new Store(1L, "loja 01", "address 01");

        Mockito.when(repository.save(this.store)).thenReturn(responseStore);
        final Store storeCreated = service.create(this.store);
        Assertions.assertNotNull(storeCreated.getId());
        Assertions.assertEquals(storeCreated.getName(), "loja 01");
        Assertions.assertEquals(storeCreated.getAddress(), "address 01");
    }

    @Test()
    public void updateStoreSuccess(){
        final Optional<Store> optionalStore = Optional.of(new Store(1L, "loja 01", "address 01"));

        Mockito.lenient().when(this.repository.findById(1L)).thenReturn(optionalStore);
        Mockito.lenient().when(this.repository.save(optionalStore.get())).thenReturn(optionalStore.get());
        Store store = service.update(optionalStore.get(),1L);
        Assertions.assertNotNull(store.getId());
        Assertions.assertEquals(store.getName(), "loja 01");
        Assertions.assertEquals(store.getAddress(), "address 01");
    }

    @Test
    public void updateStoreNotPresent(){
        final Store store = service.update(this.store, 1L);
        Assertions.assertNull(store);
    }

    @Test
    public void getStoreByIdIsPresent() {
        final Optional<Store> responseStore = Optional.of(new Store(1L, "loja 01", "teste 01"));
        Mockito.lenient().when(repository.findById(1L)).thenReturn(responseStore);
        Optional<Store> store = service.findById(1L);
        Assertions.assertTrue(store.isPresent());
    }
}
