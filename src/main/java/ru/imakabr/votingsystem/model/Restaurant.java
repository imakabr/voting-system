package ru.imakabr.votingsystem.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;


@NamedQueries(
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id")
)
@FilterDef(name = "filterByDate", parameters = {@ParamDef(name = "date_time", type = "java.time.LocalDateTime")})
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    public static final String DELETE = "Restaurant.delete";

    @Filters({
            @Filter(name = "filterByDate", condition = ":date_time = date_time")
    })
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "rest_id",
            nullable = false
    )
    @OrderBy("date_time, name asc")
    @BatchSize(size = 200)
//    @JsonIgnore
    protected List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Restaurant() {
    }

    public Restaurant(int id, String name) {
        super(id, name);
    }

    public Restaurant(int id, String name, List<Item> items) {
        super(id, name);
        this.items = items;
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.id, restaurant.name);
    }

}
