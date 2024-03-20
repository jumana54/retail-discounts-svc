package com.retaildiscountssvc.demo.api.dao;

import com.retaildiscountssvc.demo.api.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, Integer> {
}
