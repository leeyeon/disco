package com.startup.disco.service;

import com.startup.disco.model.ProductDTO;

import java.util.List;

public interface discoService {

    void insertProduct(ProductDTO productDTO);

    List<String> allSelectProduct() throws Exception;
    
}
