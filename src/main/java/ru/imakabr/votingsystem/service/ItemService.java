package ru.imakabr.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.repository.ItemRepository;

import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ItemService {

    private ItemRepository repository;

    @Autowired
    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Item create(Item item) {
        Assert.notNull(item, "item must not be null");
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

    public void update(Item item) {
        Assert.notNull(item, "item must not be null");
        checkNotFoundWithId(repository.save(item), item.getId());
    }

}
