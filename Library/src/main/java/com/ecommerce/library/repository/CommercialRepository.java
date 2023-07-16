package com.ecommerce.library.repository;

import com.ecommerce.library.model.Commercial;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercialRepository extends JpaRepository<Commercial, Long> {
    Commercial findByUsername(String username);
    List<Commercial> findAll();
}
