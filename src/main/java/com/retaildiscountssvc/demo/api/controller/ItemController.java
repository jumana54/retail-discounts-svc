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
public class ItemController {

    private final ItemService itemService;

    ItemController(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping()
    public ResponseEntity<List<Item>> addItems(@RequestBody final List<Item> items) {
        return new ResponseEntity<>(itemService.saveItems(items), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<>(itemService.findAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable final int id) {
        Item item = itemService.findItemById(id);
        return new ResponseEntity<>(item, item == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
