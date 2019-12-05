package ru.imakabr.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.repository.ItemRepository;
import ru.imakabr.votingsystem.repository.RestaurantRepository;
import ru.imakabr.votingsystem.util.ValidationUtil;

import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ItemService {

    private ItemRepository repository;

    private RestaurantRepository restaurantRepository;

    @Autowired
    public ItemService(ItemRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public Item create(Item item, int restaurantId) {
        Assert.notNull(item, "item must not be null");
        item.setRestaurant(restaurantRepository.getOne(restaurantId));
        return repository.save(item);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Item get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Item> getAll() {
        return repository.findAll();
    }

    public void update(Item item, int restaurantId, int itemId) {
        Assert.notNull(item, "item must not be null");
        ValidationUtil.assureItemIdConsistent(item, itemId);
        item.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(repository.save(item), item.getId());
    }

}
