package com.retaildiscountssvc.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "item")
public class Item {
    @Id
    private long id;
    private ItemType type;
    private double price;
}
