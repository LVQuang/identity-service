package dev.quang.identity_service.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.quang.identity_service.Entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {}