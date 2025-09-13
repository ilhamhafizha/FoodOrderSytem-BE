package org.fos.foodordersystem.repository.transaction;

import org.fos.foodordersystem.entitiy.transaction.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByPembeliIdOrderByCreatedDateDesc(String pembeliId);
    List<Order> findAllByOrderByCreatedDateDesc();

}
