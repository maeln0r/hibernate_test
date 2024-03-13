package ru.maelnor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@Entity
public class Purchase {
    @EmbeddedId
    private PurchaseListKey purchaseListKey;

    @Column(name = "good_name", insertable=false, updatable=false, length = 500)
    private String goodName;

    @Column(name = "user_name", insertable=false, updatable=false, length = 500)
    private String userName;

    @Getter
    @Setter
    @Embeddable
    private static class PurchaseListKey implements Serializable {

        @Column(name = "good_name", length = 500)
        private String goodName;

        @Column(name = "user_name", length = 500)
        private String userName;
    }
}