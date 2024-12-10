package com.hw.shopping.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();

    @Column(unique = true)
    private String username;
    private String password;
    private String displayName;


    public void createMember(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }
}