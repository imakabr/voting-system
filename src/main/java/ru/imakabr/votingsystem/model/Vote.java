package ru.imakabr.votingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote {
    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "user_id")
        protected Integer userId;
        @Column(name = "rest_id")
        protected Integer restId;

        public Id() {
        }

        public Id(Integer categoryId, Integer itemId) {
            this.userId = categoryId;
            this.restId = itemId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.userId.equals(that.userId)
                        && this.restId.equals(that.restId);
            }
            return false;
        }

        public int hashCode() {
            return userId.hashCode() + restId.hashCode();
        }
    }

    @EmbeddedId
    protected Id id = new Id();

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            insertable = false, updatable = false)
    protected User user;
    @ManyToOne
    @JoinColumn(
            name = "rest_id",
            insertable = false, updatable = false)
    protected Restaurant restaurant;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, LocalDateTime dateTime) {
        this.user = user;
        this.restaurant = restaurant;
        this.dateTime = dateTime;
        this.id.userId = user.getId();
        this.id.restId = restaurant.getId();
        restaurant.getVotes().add(this);
        user.getVotes().add(this);
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
