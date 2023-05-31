package com.racme.e06_persistencia_master;

public class Produto {

    private Integer id;
    private String name;
    private Double price;


    public Produto(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString(){
        return this.id + " - " + this.name.toString() + " | $ " + this.price.toString();
    }
}
