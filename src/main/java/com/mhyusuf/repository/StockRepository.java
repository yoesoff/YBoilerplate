package com.mhyusuf.repository;

import com.mhyusuf.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Variant, UUID> {

    @Transactional
    @Modifying
    @Query("UPDATE Variant v SET v.stock = :stock WHERE v.id = :variantId")
    void updateStock(UUID variantId, Integer stock);

    @Query("SELECT v.stock FROM Variant v WHERE v.id = :variantId")
    Integer getStock(UUID variantId);
}
