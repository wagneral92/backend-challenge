package com.acme.storeserver.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("store")
public class StoreResource {

    @GetMapping
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("ok");
    }
}
