package ru.imakabr.votingsystem.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item extends AbstractNamedEntity{

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 500000)
    private int price;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rest_id")
    @BatchSize(size = 200)
    protected Restaurant restaurant;

    public Item() {
    }

    public Item(Integer id, Restaurant restaurant, String name, int price, LocalDateTime dateTime) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
        this.dateTime = dateTime;
    }

    public Item(Item item) {
        super(item.id, item.name);
        this.price = item.price;
        this.dateTime = item.dateTime;
        this.restaurant = item.restaurant;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id = " + id +
                ", restaurant = " + restaurant +
                ", name = " + name +
                ", date = " + dateTime +
                ", price = " + price +
                '}';
    }
}
