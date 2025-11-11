package com.mhyusuf.repository;

import com.mhyusuf.entity.Item;
import com.mhyusuf.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface VariantRepository extends JpaRepository<Variant, UUID> {

    List<Variant> findByItem(Item item);

    Optional<Variant> findByItemAndVariantName(Item item, String variantName);

    List<Variant> findByStockLessThanEqual(Integer stock);

    Optional<Variant> findByVariantNameAndItemId(String variantName, UUID itemId);
}
