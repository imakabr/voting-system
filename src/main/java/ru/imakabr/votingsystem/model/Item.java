package ru.imakabr.votingsystem.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Item extends AbstractNamedEntity{

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 500000)
    private int price;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    public Item() {
    }

    public Item(Integer id, String name, int price, LocalDateTime dateTime) {
        super(id, name);
        this.price = price;
        this.dateTime = dateTime;
    }

    public Item(Integer id, Item item) {
        super(id, item.name);
        this.price = price;
        this.dateTime = dateTime;
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
                ", name = " + name +
                ", date = " + dateTime +
                ", price = " + price +
                '}';
    }
}
