package com.hw.shopping.controller;

import com.hw.shopping.domain.Member;
import com.hw.shopping.domain.Order;
import com.hw.shopping.repository.OrdersRepository;
import com.hw.shopping.service.CustomUser;
import com.hw.shopping.service.OrdersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;
    private final OrdersRepository ordersRepository;

    @PostMapping("/order")
    public String orders(Authentication auth,
        @RequestParam int count,
        @RequestParam int totalPrice,
        @RequestParam Long item_id) {

        CustomUser customUser = (CustomUser) auth.getPrincipal();


        System.out.println("#################ORDER##################");
        System.out.println("@@@@@@@@@@Controller@@@@@@@@@@");
        System.out.println("orders = " + count);
        System.out.println("price = " + totalPrice);
        System.out.println("count = " + count);
        System.out.println("member = " + customUser);
        System.out.println("item = " + item_id);
        System.out.println("@@@@@@@@@@Controller@@@@@@@@@@");


        ordersService.add(customUser,totalPrice,count,item_id);

        return "redirect:/list";

    }

    @GetMapping("/order/all")
    String getOrderAll(Model model) {
        List<Order> orders = ordersService.AllList();

        model.addAttribute("order", orders);

        System.out.println("#################ORDER##################");
        System.out.println("@@@@@@@@@@Controller@@@@@@@@@@");
        System.out.println("@@@@@@@@@@Controller@@@@@@@@@@");

        return "orders/list.html";
    }

}