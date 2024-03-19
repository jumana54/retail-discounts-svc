package com.retaildiscountssvc.demo.api.controller;

import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class itemController {

    @Autowired
    private ItemService service;

    @PostMapping()
    public ResponseEntity<List<Item>> addItems(@RequestBody final List<Item> items) {
        return new ResponseEntity<>(service.saveItems(items), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<>(service.findAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable final long id) {
        Item item = service.findItemById(id);
        return new ResponseEntity<>(item, item == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
