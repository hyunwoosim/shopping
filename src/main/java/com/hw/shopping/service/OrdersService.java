package com.hw.shopping.service;

import com.hw.shopping.domain.Item;
import com.hw.shopping.domain.Member;
import com.hw.shopping.domain.Order;
import com.hw.shopping.repository.ItemRepository;
import com.hw.shopping.repository.MemberRepository;
import com.hw.shopping.repository.OrdersRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public void add(CustomUser customUser, int price, int count, Long item_id) {

        String username = customUser.getUsername();

        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid member_id"));

        Item item = itemRepository.findById(item_id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));


        Order order = new Order();
        order.saveOrders(price, count, member, item, LocalDateTime.now());

        System.out.println("@@@@@@@@@@Service@@@@@@@@@@");
        System.out.println("orders = " + order);
        System.out.println("price = " + price);
        System.out.println("count = " + count);
        System.out.println("member = " + member);
        System.out.println("item = " + item);
        System.out.println("@@@@@@@@@@Service@@@@@@@@@@");

        ordersRepository.save(order);
    }


}