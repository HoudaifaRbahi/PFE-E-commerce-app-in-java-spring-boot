package com.ecommerce.library.service;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.model.Commercial;

import java.util.List;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);
    void deleteCommercialById(Long id);
     List<Commercial> findAll();


}
