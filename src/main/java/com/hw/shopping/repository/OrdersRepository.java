package com.hw.shopping.repository;

import com.hw.shopping.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order,Long> {

}