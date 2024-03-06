package com.foodapp.foodorderingapp.entity;

import java.util.Objects;
import java.util.UUID;
import lombok.*;

import jakarta.persistence.*;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_address")
@Entity
public class Address {
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String street;
    private String ward;
    private String district;
    private String province;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
