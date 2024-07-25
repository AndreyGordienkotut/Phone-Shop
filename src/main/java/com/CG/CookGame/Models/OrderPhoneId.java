package com.CG.CookGame.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderPhoneId implements Serializable {
    @Column(name = "Order_id")
    private Long orderId;

    @Column(name = "Phone_id")
    private Long phoneId;

    public OrderPhoneId() {}

    public OrderPhoneId(Long orderId, Long phoneId) {
        this.orderId = orderId;
        this.phoneId = phoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPhoneId that = (OrderPhoneId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(phoneId, that.phoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, phoneId);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }
}