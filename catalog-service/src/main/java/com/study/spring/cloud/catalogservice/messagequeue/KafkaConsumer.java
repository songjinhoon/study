package com.study.spring.cloud.catalogservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.spring.cloud.catalogservice.domain.entity.Catalog;
import com.study.spring.cloud.catalogservice.domain.respotiroy.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;

    @Transactional
    @KafkaListener(topics = "example-catalog-topic")
    public void updateQuantity(String message) {
        log.info("Kafka message ==>" + message);
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            map = objectMapper.readValue(message, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        Catalog catalog = catalogRepository.findById(Long.parseLong(map.get("catalogId").toString())).orElseThrow(IllegalArgumentException::new);
        catalog.updateQuantity(catalog.getQuantity() - (Integer) map.get("quantity"));
    }

}
