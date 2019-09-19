package ru.imakabr.votingsystem.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "items")
public class Item extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 500000)
    private int price;

    public Item() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                "name=" + name +
                "price=" + price +
                '}';
    }
}
