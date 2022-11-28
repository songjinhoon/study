package com.study.spring.cloud.catalogservice.domain.dto;

import com.study.spring.cloud.catalogservice.domain.entity.Catalog;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CatalogDto {

    private String id;

    private String name;

    private Integer stock;

    private Integer price;

    public static CatalogDto of(Catalog catalog) {
        return new ModelMapper().map(catalog, CatalogDto.class);
    }

    public static List<CatalogDto> of(List<Catalog> catalogs) {
        return catalogs.stream().map(CatalogDto::of).collect(Collectors.toList());
    }

}
