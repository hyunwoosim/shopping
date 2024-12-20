package com.hw.shopping.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "order_id")
   private Long id;


    private int price;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
   private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "item_id")
   private Item item;


   private LocalDateTime created;

    public void saveOrders(int price, int count, Member member, Item item, LocalDateTime created) {
        this.price = price;
        this.count = count;
        this.member = member;
        this.item = item;
        this.created = created;
    }
}