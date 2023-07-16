package com.ecommerce.library.service;

import com.ecommerce.library.dto.CommercialDto;
import com.ecommerce.library.model.Commercial;

public interface CommercialService {
    Commercial save(CommercialDto commercialDto);

    Commercial findByUsername(String username);
}
