package com.mhyusuf.auth.runner;

import com.mhyusuf.entity.*;
import com.mhyusuf.repository.ItemRepository;
import com.mhyusuf.repository.VariantRepository;
import com.mhyusuf.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DemoDataInitializer implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Fashion items
        Item tshirt = createOrGetItem("T-Shirt", "Cotton unisex t-shirt");
        Item jeans = createOrGetItem("Jeans", "Slim fit blue jeans");
        // Kitchen items
        Item fryingPan = createOrGetItem("Frying Pan", "Non-stick frying pan 24cm");
        Item mug = createOrGetItem("Ceramic Mug", "White ceramic mug 350ml");

        // Variants for fashion
        Variant tshirtRedM = createOrGetVariant(tshirt, "Red T-Shirt", "Red", "M", new BigDecimal("99.000"), 50);
        Variant tshirtBlueL = createOrGetVariant(tshirt, "Blue T-Shirt", "Blue", "L", new BigDecimal("105.000"), 30);
        Variant jeans32 = createOrGetVariant(jeans, "Jeans 32", "Blue", "32", new BigDecimal("250.000"), 20);

        // Variants for kitchen
        Variant panBlack = createOrGetVariant(fryingPan, "Black Pan", "Black", "24cm", new BigDecimal("180.000"), 40);
        Variant mugWhite = createOrGetVariant(mug, "White Mug", "White", "350ml", new BigDecimal("35.000"), 100);

        // Create a sample sale only if not exists (by customer name for demo)
        if (saleRepository.findAll().stream().noneMatch(s -> "Demo Customer".equals(s.getCustomerName()))) {
            Sale sale = Sale.builder()
                    .customerName("Demo Customer")
                    .totalAmount(BigDecimal.ZERO)
                    .createdAt(LocalDateTime.now())
                    .build();

            SaleItem saleItem1 = SaleItem.builder()
                    .sale(sale)
                    .variant(tshirtRedM)
                    .quantity(2)
                    .subtotal(tshirtRedM.getPrice().multiply(BigDecimal.valueOf(2)))
                    .build();

            SaleItem saleItem2 = SaleItem.builder()
                    .sale(sale)
                    .variant(mugWhite)
                    .quantity(1)
                    .subtotal(mugWhite.getPrice())
                    .build();

            sale.setItems(Arrays.asList(saleItem1, saleItem2));
            sale.setTotalAmount(saleItem1.getSubtotal().add(saleItem2.getSubtotal()));

            saleRepository.save(sale);
        }
    }

    private Item createOrGetItem(String name, String description) {
        return itemRepository.findByName(name)
                .orElseGet(() -> {
                    Item item = new Item();
                    item.setName(name);
                    item.setDescription(description);
                    item.setCreatedBy("system");
                    item.setUpdatedBy("system");
                    return itemRepository.save(item);
                });
    }

    private Variant createOrGetVariant(Item item, String variantName, String color, String size, BigDecimal price, int stock) {
        Optional<Variant> existing = variantRepository.findByVariantNameAndItemId(variantName, item.getId());
        if (existing.isPresent()) {
            return existing.get();
        }
        Variant variant = new Variant();
        variant.setItem(item);
        variant.setVariantName(variantName);
        variant.setColor(color);
        variant.setSize(size);
        variant.setPrice(price);
        variant.setStock(stock);
        variant.setCreatedBy("system");
        variant.setUpdatedBy("system");
        return variantRepository.save(variant);
    }
}
