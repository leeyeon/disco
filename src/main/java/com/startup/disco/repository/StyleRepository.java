package com.startup.disco.repository;

import com.startup.disco.model.StyleDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleRepository extends JpaRepository<StyleDTO, String> {

    List<StyleDTO> findAll();

}