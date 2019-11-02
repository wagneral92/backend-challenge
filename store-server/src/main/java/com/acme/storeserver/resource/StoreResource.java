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

    /**
     *
     * @param store
     * @return
     */
    @PostMapping
    public ResponseEntity<Store> create(@RequestBody @Valid final Store store) {
        return ResponseEntity.ok(this.service.create(store));
    }

    /**
     *
     * @param store
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Store> update(@RequestBody @Valid Store store, @PathVariable final Long id) {

        if (!this.service.findById(id).isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(this.service.update(store, id));
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Store> getId(@PathVariable final Long id) {

        Optional<Store> optionalStore = this.service.findById(id);

        if (!optionalStore.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optionalStore.get());
    }

    /***
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Store>> getAll(final Pageable pageable) {
        return ResponseEntity.ok(this.service.findAll(pageable));
    }
}
