package com.study.spring.cloud.catalogservice.domain.service;

import com.study.spring.cloud.catalogservice.domain.entity.Catalog;
import com.study.spring.cloud.catalogservice.domain.respotiroy.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<Catalog> find() {
        return catalogRepository.findAll();
    }

}
