package com.acme.orderserver.repository;

import com.acme.orderserver.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "from Order o where o.address like %:search%")
    Page<Order> findAllSearch(final Pageable pageable, @Param("search") String search);
}
