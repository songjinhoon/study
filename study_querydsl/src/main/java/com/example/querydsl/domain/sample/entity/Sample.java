package com.example.querydsl.domain.sample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Sample {

    @GeneratedValue
    @Id
    private Long id;

}
