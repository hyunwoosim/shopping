package com.hw.shopping.controller;

import com.hw.shopping.domain.Item;
import com.hw.shopping.repository.ItemRepository;
import com.hw.shopping.service.ItemService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemService.allItem();
        model.addAttribute("items", result);

        return "items/list.html";
    }

    @GetMapping("/write")
    String write() {

        return "items/newItem.html";
    }

    @PostMapping("/newItem")
    String newItem(String title, int price) {

        itemService.saveItem(title, price);

        return "redirect:/list";
    }

    @GetMapping("/detail/{item_id}")
    String detail(@PathVariable Long item_id, Model model) {

        Optional<Item> result = itemService.detail(item_id);
        if (result.isPresent()) {
            model.addAttribute("items", result.get());
            return "items/detail.html";
        } else {
            return "redirect:/list";
        }

    }

    @GetMapping("/edit/{item_id}")
    String edit(@PathVariable Long item_id, Model model) {
        Optional<Item> result = itemService.detail(item_id);
        if (result.isPresent()) {
            model.addAttribute("item", result.get());
            return "items/edit.html";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/update/{item_id}")
    String update(@PathVariable Long item_id,
        @RequestParam String title,
        @RequestParam int price) {
        itemService.update(item_id, title, price);

        return "redirect:/list";
    }

}