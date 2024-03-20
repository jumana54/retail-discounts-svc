package com.retaildiscountssvc.demo.api.service;

import com.retaildiscountssvc.demo.api.dao.ItemRepository;
import com.retaildiscountssvc.demo.api.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repo;

    ItemService(@Autowired ItemRepository repo) {
        this.repo = repo;
    }

    public List<Item> saveItems(final List<Item> items) {
        return repo.saveAll(items);
    }

    public List<Item> findAllItems() {
        return repo.findAll();
    }

    public List<Item> findAllItemsById(final List<Integer> ids) {
        return repo.findAllById(ids);
    }

    public Item findItemById(final int id) {
        return repo.findById(id).orElse(null);
    }
}
