package com.hw.shopping.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(length = 1000)
    private String content;
    @Column(name = "parent_id")
    private Long parentId;

    public void saveComment(String username, String content, Long parentId) {
        this.username = username;
        this.content = content;
        this.parentId = parentId;
    }
}