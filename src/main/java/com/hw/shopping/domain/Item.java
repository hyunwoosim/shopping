package com.hw.shopping.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String title;
    private int price;
    private String imgUrl;

    public void createItem(String title, int price, String imgUrl) {
        this.title = title;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public void updateItem(String title, int price, String imgUrl) {
        this.title = title;
        this.price = price;
        this.imgUrl = imgUrl;
    }

}