package ru.maelnor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Goods")
public class Good {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Integer id;

    @Column(name = "name", length = 500)
    private String name;

}