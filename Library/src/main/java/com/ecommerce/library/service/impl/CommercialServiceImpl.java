package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CommercialDto;
import com.ecommerce.library.model.Commercial;
import com.ecommerce.library.repository.CommercialRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.CommercialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CommercialServiceImpl implements CommercialService {
    private final CommercialRepository commercialRepository;
    private final RoleRepository roleRepository;


    @Override
    public Commercial save(CommercialDto commercialDto) {
        Commercial commercial = new Commercial();
        commercial.setFirstName(commercialDto.getFirstName());
        commercial.setLastName(commercialDto.getLastName());
        commercial.setUsername(commercialDto.getUsername());
        commercial.setPassword(commercialDto.getPassword());
        commercial.setRoles(Arrays.asList(roleRepository.findByName("commercial")));
        return commercialRepository.save(commercial);
    }

    @Override
    public Commercial findByUsername(String username) {
        return commercialRepository.findByUsername(username);
    }
}
