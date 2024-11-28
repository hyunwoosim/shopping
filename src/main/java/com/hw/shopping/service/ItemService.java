package com.hw.shopping.service;

import com.hw.shopping.domain.Item;
import com.hw.shopping.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> allItem() {
       return itemRepository.findAll();
    }

    public void saveItem(String title, int price, String imgUrl) {

        Item item = new Item();
        item.createItem(title, price, imgUrl);
        itemRepository.save(item);
    }

    public Optional<Item> detail(Long item_id) {
        return itemRepository.findById(item_id);

    }

    @Transactional
    public void update(Long item_Id, String title, int price, String imgUrl) {
        Item item = itemRepository.findById(item_Id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

        item.updateItem(title,price,imgUrl);
    }

    public void delete(Long item_id) {
        itemRepository.deleteById(item_id);
    }

    public Page<Item> listPage(int num){
        return itemRepository.findPageBy(PageRequest.of(num - 1, 5));
    }

    public Page<Item> search(String searchText, int num) {
        System.out.println("#################Service");
        System.out.println("searchText = " + searchText);
        System.out.println("num = " + num);
        System.out.println("#################Service");

        PageRequest page = PageRequest.of(num - 1, 5);

        System.out.println("#################Service");
        System.out.println("searchText = " + searchText);
        System.out.println("num = " + num);
        System.out.println("page = " + page);
        System.out.println("#################Service");

        return itemRepository.rawQuery1(searchText,page);
    }

}