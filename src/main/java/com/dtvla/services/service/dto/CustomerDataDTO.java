package com.dtvla.services.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustomerData entity.
 */
public class CustomerDataDTO implements Serializable {

    private Long id;

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

        CustomerDataDTO customerDataDTO = (CustomerDataDTO) o;

        if ( ! Objects.equals(id, customerDataDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerDataDTO{" +
            "id=" + id +
            ", CustomerId='" + CustomerId + "'" +
            '}';
    }
}
