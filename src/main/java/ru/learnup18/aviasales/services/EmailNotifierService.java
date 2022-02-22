package ru.learnup18.aviasales.services;

import org.springframework.stereotype.Component;
import ru.learnup18.aviasales.model.EmailNotifier;

import java.util.HashMap;

@Component
public class EmailNotifierService {
    static private HashMap<String, EmailNotifier> emailNotifierMap =  new HashMap<String, EmailNotifier>();

    public EmailNotifier insEmailNotifier(String name, String email) {
        EmailNotifier emailNotifier = new EmailNotifier(name, email);
        emailNotifierMap.put(name, emailNotifier);
        return emailNotifier;
    }

    public void EmailNotifierSend(String title, String info) {
        for (EmailNotifier s : emailNotifierMap.values()) {
            System.out.println("@@@@@@> Отправляю сообщение : " + s.getName() + ", " + s.getEmail());
            System.out.println("@@@@@@@@@@@@> title, info : " + title + ", " + info);
        }
    }
}
