package com.acme.storeserver.resource;

import com.acme.storeserver.dto.StoreDTO;
import com.acme.storeserver.model.Store;
import com.acme.storeserver.service.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("store")
public class StoreResource {

    private final StoreService service;
    private final ModelMapper modelMapper;

    /**
     *
     * @param service
     * @param modelMapper
     */
    public StoreResource(StoreService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    /**
     *
     * @param store
     * @return
     */
    @PostMapping
    public ResponseEntity<Store> create(@RequestBody @Valid final StoreDTO store) {
        return ResponseEntity.ok(this.service.create(this.modelMapper.map(store, Store.class)));
    }

    /**
     *
     * @param store
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Store> update(@RequestBody @Valid final StoreDTO store, @PathVariable final Long id) {

        if (!this.service.findById(id).isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(this.service.update(this.modelMapper.map(store, Store.class), id));
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

    /**
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Store>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") final int page,
                                              @RequestParam(value = "size",required = false, defaultValue = "10") final int size,
                                              @RequestParam(value = "search",required = false, defaultValue = "") final String search) {
        return ResponseEntity.ok(this.service.findAll(PageRequest.of(page, size), search));
    }
}
