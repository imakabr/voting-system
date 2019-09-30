package ru.imakabr.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@NamedQueries(
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id")
)
@FilterDef(name = "filterByDate", parameters = {@ParamDef(name = "date_time", type = "java.time.LocalDate")})
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    public static final String DELETE = "Restaurant.delete";

    @Filters({
            @Filter(name = "filterByDate", condition = ":date_time = date_time")
    })
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "rest_id",
//            nullable = false
//    )
//    @OrderBy("date_time, name asc")
    @BatchSize(size = 200)
//    @JsonIgnore
    @JsonManagedReference
    protected List<Item> items;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonIgnore
    protected List<Vote> votes;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.id, restaurant.name);
    }

    public Restaurant(Integer id, String name, List<Item> items) {
        super(id, name);
        this.items = items;
    }


}
