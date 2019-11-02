package com.acme.storeserver.resource;

import com.acme.storeserver.model.Store;
import com.acme.storeserver.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("store")
public class StoreResource {

    private final StoreService service;

    public StoreResource(StoreService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Store> create(@RequestBody @Valid Store store) {
        return ResponseEntity.ok(this.service.create(store));
    }

    @PutMapping("{id}")
    public ResponseEntity<Store> update(@RequestBody @Valid Store store, @PathVariable Long id) {

        if (!this.service.findById(id).isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(this.service.update(store, id));
    }

    @GetMapping("{id}")
    public ResponseEntity<Store> getId(@PathVariable Long id) {

        Optional<Store> optionalStore = this.service.findById(id);

        if (!optionalStore.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optionalStore.get());
    }

    @GetMapping
    public ResponseEntity<Page<Store>> getAll(Pageable pageable) {
        return ResponseEntity.ok(this.service.findAll(pageable));
    }
}
