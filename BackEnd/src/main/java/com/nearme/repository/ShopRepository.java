package com.nearme.repository;

import com.nearme.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Page<Shop> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}
