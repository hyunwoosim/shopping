package com.hw.shopping.controller;

import com.hw.shopping.domain.Comment;
import com.hw.shopping.domain.Item;
import com.hw.shopping.repository.ItemRepository;
import com.hw.shopping.service.CommentService;
import com.hw.shopping.service.ItemService;
import com.hw.shopping.service.S3Service;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final S3Service s3Service;
    private final CommentService commentService;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemService.allItem();
        model.addAttribute("items", result);

        return "redirect:/list/page/1";
    }

    @GetMapping("/write")
    String write() {

        return "items/newItem.html";
    }

    @PostMapping("/newItem")
    String newItem(String title, int price, String imgUrl) {
        // 이미지가 없거나, 실패할때 기본값 넣기 ( DTO만들때 추가할꺼)
//        if (itemDto.getImgUrl() == null || itemDto.getImgUrl().isEmpty()) {
//            itemDto.setImgUrl("https://placehold.co/300"); // 기본값 설정
//        }
        itemService.saveItem(title, price, imgUrl);

        return "redirect:/list";
    }

    @GetMapping("/detail/{item_id}")
    String detail(@PathVariable Long item_id,
        Model model) {


        Optional<Item> result = itemService.detail(item_id);
        if (result.isPresent()) {
            model.addAttribute("items", result.get());

            List<Comment> comments = commentService.commentList(item_id);
            model.addAttribute("comments", comments);

//            Page<Comment> commentPage = commentService.listPage(item_id ,num);
//            model.addAttribute("commentPage", commentPage.getContent()); // 현재 페이지 아이템 목록
//            model.addAttribute("currentPage", num); // 현재 페이지
//            model.addAttribute("totalPages", commentPage.getTotalPages());


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
        @RequestParam int price,
        @RequestParam String imgUrl) {
        itemService.update(item_id, title, price, imgUrl);

        return "redirect:/list";
    }


    @DeleteMapping("/delete")
    ResponseEntity<String> deleteItem(@RequestParam Long item_id) {
        itemService.delete(item_id);

        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/list/page/{num}")
    String getListPage(Model model, @PathVariable int num) {

        Page<Item> result = itemService.listPage(num);
        model.addAttribute("items", result.getContent()); // 현재 페이지 아이템 목록
        model.addAttribute("currentPage", num); // 현재 페이지
        model.addAttribute("totalPages", result.getTotalPages()); // 전체 페이지 수

        return "items/list";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename) {
        String result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println("result = " + result);
        return result;
    }


}