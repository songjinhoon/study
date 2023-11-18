package com.study.spring.cloud.catalogservice.domain.controller;


import com.study.spring.cloud.catalogservice.domain.dto.CatalogDto;
import com.study.spring.cloud.catalogservice.domain.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/catalog-service")
@RestController
public class CatalogController {

    private final Environment environment;

    private final CatalogService catalogService;

    /**** CHECK *****/
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", environment.getProperty("local.server.port"));
    }
    /***** CHECK *****/

    /**
     * 일괄 조회
     */
    @GetMapping
    public ResponseEntity<?> find() {
        return ResponseEntity.ok().body(CatalogDto.of(catalogService.find()));
    }

}
