package ru.learnup18.aviasales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.learnup18.aviasales.model.Premiere;
import java.util.HashMap;

@Configuration
public class EmailNotifierConfig {

    @Bean
    public HashMap<String, Premiere> emailNotifierMap() {
        HashMap<String, Premiere> emailNotifierMap = new HashMap<>();
        return emailNotifierMap;
    }

}
