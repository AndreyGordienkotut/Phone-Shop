package com.CG.CookGame.Models;


import jakarta.persistence.*;
@Entity
@Table(name = "order_phone")
public class OrderPhone {
    @EmbeddedId
    private OrderPhoneId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("phoneId")
    @JoinColumn(name = "phone_id")
    private Phone phone;

    @Column
    private int count;

    @Column
    private int price;
    // Constructors, Getters and Setters

    public OrderPhone(Order order, Phone phone, int count, int price) {
        this.order = order;
        this.phone = phone;
        this.count = count;
        this.price = price;
        this.id = new OrderPhoneId(order.getId(), phone.getId());
    }
    public OrderPhone() {
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OrderPhoneId getId() {
        return id;
    }

    public void setId(OrderPhoneId id) {
        this.id = id;
    }
}