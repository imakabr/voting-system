package ru.imakabr.votingsystem.to;

import ru.imakabr.votingsystem.model.Restaurant;

import java.util.List;

public class RestaurantTO {
    Restaurant selected;
    List<Restaurant> restaurants;

    public RestaurantTO(List<Restaurant> restaurants, Restaurant selected) {
        this.restaurants = restaurants;
        this.selected = selected;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Restaurant getSelected() {
        return selected;
    }

    public void setSelected(Restaurant selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Restaurants{" +
                "restaurants=" + restaurants +
                ", selected='" + selected + '\'' +
                '}';
    }
}
