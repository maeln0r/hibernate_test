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
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

}