package com.hw.shopping.service;

import com.hw.shopping.domain.Item;
import com.hw.shopping.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> allItem() {
       return itemRepository.findAll();
    }

    public void saveItem(String title, int price) {

        Item item = new Item();
        item.createItem(title, price);
        itemRepository.save(item);
    }

    public Optional<Item> detail(Long item_id) {
        return itemRepository.findById(item_id);

    }

    @Transactional
    public void update(Long item_Id, String title, int price) {
        Item item = itemRepository.findById(item_Id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

        item.updateItem(title,price);
    }
}