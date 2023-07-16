package com.ecommerce.library.repository;

import com.ecommerce.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select o from Order o where o.customer.id=?1 and o.orderStatus='Accepted' ORDER BY o.id DESC")
    List<Order>getCuustomerInvoices(Long id);
    @Query("select o from Order o ORDER BY o.id DESC")
    List<Order> findAll();
    @Query(value = "select Count(o) from Order o where o.orderStatus='Accepted' ")
    int nbrOrders();
    @Query("SELECT SUM(totalPrice) FROM Order o where o.orderStatus='Accepted'")
    Double earning();
}
