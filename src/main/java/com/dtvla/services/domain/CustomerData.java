package com.dtvla.services.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerData.
 */
@Entity
@Table(name = "CustomerData")
public class CustomerData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Integer CustomerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return CustomerId;
    }

    public CustomerData CustomerId(Integer CustomerId) {
        this.CustomerId = CustomerId;
        return this;
    }

    public void setCustomerId(Integer CustomerId) {
        this.CustomerId = CustomerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerData customerData = (CustomerData) o;
        if (customerData.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerData{" +
            "id=" + id +
            ", CustomerId='" + CustomerId + "'" +
            '}';
    }
}
