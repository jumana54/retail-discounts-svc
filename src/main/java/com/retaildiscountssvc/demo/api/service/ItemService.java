package com.retaildiscountssvc.demo.api.service;

import com.retaildiscountssvc.demo.api.dao.ItemRepository;
import com.retaildiscountssvc.demo.api.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repo;

    public List<Item> saveItems(final List<Item> items) {
        return repo.saveAll(items);
    }

    public List<Item> findAllItems() {
        return repo.findAll();
    }

    public Item findItemById(final long id) {
        return repo.findById(id).orElse(null);
    }
}
