package com.ecommerce.library.repository;

import com.ecommerce.library.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    @Query("SELECT COUNT(c) FROM Customer c")
    int nbrCustomers();
    @Query("SELECT c.city,Count(c.city) FROM Customer c INNER JOIN c.orders o GROUP BY c.city ORDER BY COUNT(c.city) DESC")
    List<Object[]> findCitiesWithOrdersOrderByOrderCountDesc();
}
