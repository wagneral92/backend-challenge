package com.acme.storeserver.repository;

import com.acme.storeserver.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(value = "from Store s where s.name like %:search% or s.address like %:search%")
    Page<Store> findAllSearch(Pageable pageable, @Param("search") String search);
}
