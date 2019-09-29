package ru.imakabr.votingsystem.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity{

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id")
    @BatchSize(size = 200)
    protected User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rest_id")
    @BatchSize(size = 200)
    protected Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDateTime dateTime) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
