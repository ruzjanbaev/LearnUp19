package ru.learnup18.aviasales.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmailNotifier {
    private String name;
    private String email;
}
