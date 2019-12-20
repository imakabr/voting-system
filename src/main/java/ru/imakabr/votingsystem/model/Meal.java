package ru.imakabr.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "meals")
@Access(AccessType.FIELD)
public class Meal {

    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "MEAL_SEQ", sequenceName = "MEAL_SEQ", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEAL_SEQ")
    protected Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 500000)
    private int price;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rest_id")
    @BatchSize(size = 200)
    @JsonBackReference
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public Meal() {
    }

    public Meal(Integer id, Restaurant restaurant, String name, int price, LocalDate date) {
        this.id = id;
        this.name = name;
        this.restaurant = restaurant;
        this.price = price;
        this.date = date;
    }

    public Meal(Meal meal) {
        this.id = meal.id;
        this.name = meal.name;
        this.price = meal.price;
        this.date = meal.date;
        this.restaurant = meal.restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        Meal that = (Meal) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id = " + id +
                ", restaurant = " + restaurant +
                ", name = " + name +
                ", date = " + date +
                ", price = " + price +
                '}';
    }
}
