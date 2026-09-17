package com.amazonai.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.amazonai.backend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    Cart findByUserIdAndProductId(Long userId, Long productId);

    @Modifying
    @Query("delete from Cart c where c.userId = ?1")
    void deleteByUserId(Long userId);

}