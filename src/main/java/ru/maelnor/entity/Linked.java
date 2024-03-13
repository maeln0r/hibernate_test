package ru.maelnor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Linked {
    @EmbeddedId
    private LinkedKey linkedKey = new LinkedKey();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("goodId")
    @JoinColumn(name = "good_id", insertable = false, updatable = false, nullable = false)
    private Good good;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    private User user;

    /**
     * Конструктор, используемый в HQL запросе
     */
    public Linked(Integer goodId, Integer userId) {
        this.linkedKey.setGoodId(goodId);
        this.linkedKey.setUserId(userId);
    }

    @Getter
    @Setter
    @Embeddable
    public static class LinkedKey implements Serializable {
        @Column(name = "user_id", nullable = false, columnDefinition = "int UNSIGNED")
        private Integer userId;

        @Column(name = "good_id", nullable = false, columnDefinition = "int UNSIGNED")
        private Integer goodId;
    }
}
