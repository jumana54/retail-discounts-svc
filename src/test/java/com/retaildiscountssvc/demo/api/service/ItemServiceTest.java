package com.retaildiscountssvc.demo.api.service;

import com.retaildiscountssvc.demo.api.dao.ItemRepository;
import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository repo;
    @InjectMocks
    private ItemService itemService;
    private Item item2;
    private Item item3;
    private List<Item> items;

    @BeforeEach
    public void init() {
        final Item item1 = Item.builder().id(1).price(7).type(ItemType.GROCERY).build();
        item2 = Item.builder().id(2).price(90).type(ItemType.ROOM).build();
        item3 = Item.builder().id(3).price(57.5).type(ItemType.YARD).build();
        items = asList(item1, item2, item3);
    }

    @Test
    public void ItemService_SaveItems_ReturnSavedItems() {
        when(repo.saveAll(any())).thenReturn(items);
        assertEquals(items, itemService.saveItems(items));
    }

    @Test
    public void ItemService_FindAllItems_ReturnAllItems() {
        when(repo.findAll()).thenReturn(items);
        assertEquals(items, itemService.findAllItems());
    }

    @Test
    public void ItemService_FindAllItemsById_ReturnAllItemsForIds() {
        when(repo.findAllById(any())).thenReturn(asList(item2, item3));
        final List<Item> result = itemService.findAllItemsById(asList(2, 3));
        assertEquals(2, result.size());
        assertEquals(item2.getId(), result.get(0).getId());
        assertEquals(item3.getId(), result.get(1).getId());
    }

    @Test
    public void ItemService_FindItemById_ReturnItemForId() {
        when(repo.findById(anyInt())).thenReturn(Optional.of(item2));
        assertEquals(item2.getId(), itemService.findItemById(2).getId());
    }
}
