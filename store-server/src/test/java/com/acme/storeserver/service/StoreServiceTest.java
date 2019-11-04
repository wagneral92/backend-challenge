package com.acme.storeserver.service;

import com.acme.storeserver.model.Store;
import com.acme.storeserver.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
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
        Store responseStore = new Store(1L, "loja 01", "address 01");

        Mockito.lenient().when(service.create(this.store)).thenReturn(responseStore);
        Assertions.assertNotNull(responseStore.getId());
        Assertions.assertEquals(responseStore.getName(), "loja 01");
        Assertions.assertEquals(responseStore.getAddress(), "address 01");
    }

    @Test
    public void updateStoreSuccess(){
        Store responseStore = new Store(1L, "loja 01", "address 01");

        Optional<Store> optionalStore = Optional.of(new Store(1L, "loja 01", "address 01"));

        Mockito.lenient().when(this.repository.findById(1L)).thenReturn(optionalStore);
        Mockito.lenient().when(service.create(this.store)).thenReturn(responseStore);
        Assertions.assertNotNull(responseStore.getId());
        Assertions.assertEquals(responseStore.getName(), "loja 01");
        Assertions.assertEquals(responseStore.getAddress(), "address 01");
    }

    @Test
    public void updateStoreNotPresent(){
        Store responseStore = new Store(1L, "loja 01", "address 01");

        Mockito.lenient().when(service.create(this.store)).thenReturn(responseStore);
        Assertions.assertNotNull(responseStore);
    }

    @Test
    public void getStoreByIdIsPresent() {
        Optional<Store> responseStore = Optional.of(new Store(1L, "loja 01", "teste 01"));
        Mockito.lenient().when(service.findById(1L)).thenReturn(responseStore);
        Assertions.assertTrue(responseStore.isPresent());
    }
}
