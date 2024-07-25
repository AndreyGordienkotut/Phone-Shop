package com.CG.CookGame.Models;

import jakarta.persistence.*;

@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column
    private String name;
    @Column
    private int amount;
    @Column
    private int price;
    @Column
    private String color;
    @Column
    private String descr;


    public Phone(){}

    public Phone(Long id, String name, int amount, int price, String color, String descr) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.color = color;
        this.descr = descr;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

}
