package com.retaildiscountssvc.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection  = "user")
public class User {
    @Id
    private int id;
    private String name;
    private UserType type;
    private LocalDate joinDate;
}
