package com.hw.shopping.service;

import com.hw.shopping.domain.Item;
import com.hw.shopping.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, int price) {

        Item item = new Item();
        item.createItem(title, price);
        itemRepository.save(item);
    }
}