package com.ecommerce.library.repository;

import com.ecommerce.library.model.Admin;
import com.ecommerce.library.model.Commercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
    @Query("delete from Commercial where id=?1")
    void deleteCommercialById(Long id);

}
