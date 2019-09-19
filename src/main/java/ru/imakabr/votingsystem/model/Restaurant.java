package ru.imakabr.votingsystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.List;

@Entity(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menus")
//    @OrderBy("dateTime DESC")
//    @JsonIgnore
//    protected List<Menu> menus;

}
