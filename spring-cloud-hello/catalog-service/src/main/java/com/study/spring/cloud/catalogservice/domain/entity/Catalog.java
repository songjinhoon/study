package com.study.spring.cloud.catalogservice.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tn_catalog")
public class Catalog {

    @Id
    @GeneratedValue
    private Long catalogId;

    private String name;

    private Integer quantity;

    private Integer price;

    @Column(updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createDate;

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
