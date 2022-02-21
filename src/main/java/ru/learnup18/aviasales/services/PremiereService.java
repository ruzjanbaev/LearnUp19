package ru.learnup18.aviasales.services;

import org.springframework.stereotype.Service;
import ru.learnup18.aviasales.events.EmailNotifierEvent;
import ru.learnup18.aviasales.model.Premiere;
import ru.learnup18.aviasales.services.interfaces.Logger;

import java.util.Arrays;
import java.util.HashMap;

@Service
public class PremiereService {

    private Logger logger;
    static private HashMap<String, Premiere> premiereMap =  new HashMap<String, Premiere>();

    //@Autowired
    public PremiereService(Logger logger) {
        this.logger = logger;
    }

    //Добавление
    public Premiere insPremiere(String name, String description, String ageCategory, Integer countFreeSeats) {
        logger.print("insPremiere: " + name);
        Premiere premiere = new Premiere(name, description, ageCategory, countFreeSeats, countFreeSeats);
        premiereMap.put(name, premiere);
        new EmailNotifierEvent(
                EmailNotifierEvent.Info.of(
                        "Анонс мероприятия " + name,
                        "Анонс мероприятия " + name + ", " + description + "."));
        return premiere;
    }

    //Удаление
    public boolean delPremiere(String name) {
        logger.print("delPremiere name: " + name);
        TicketService ticketService = new TicketService(logger);
        ticketService.delTicketPremiere(name);
        premiereMap.remove(name);
        return true;
    }

    //Обновление - Предпологаем что имя Премьеры, это уникальное имя
    public boolean updPremiere(String name, String description, String ageCategory) {
        logger.print("updPremiere: " + Arrays.toString(new String[]{name, description, ageCategory}));
        premiereMap.get(name).setDescription(description);
        premiereMap.get(name).setAgeCategory(ageCategory);
        new EmailNotifierEvent(
                EmailNotifierEvent.Info.of(
                        "Изменение мероприятия " + name,
                        "Изменение мероприятия " + name + ", " + description + "."));
        return true;
    }

    //При покупке или продаже билета, мы уменьшаем или увеличиваем реальное количество
    public boolean incSeatsRealPremiere(String name, Integer incTicket) {
        int freeSeats = premiereMap.get(name).getCountFreeSeats();
        int freeReal = premiereMap.get(name).getCountFreeReal();
        if ((freeReal + incTicket) < 1) {
            logger.print("Ошибка, невозможно сдать билет!");
        } else if ((freeReal + incTicket) > freeSeats) {
            logger.print("Ошибка, невозможно купить билет!");
        } else {
            logger.print("freeSeats: " + freeSeats + ", freeReal: " + freeReal + ", (freeReal + incTicket): " + (freeReal + incTicket));
            premiereMap.get(name).setCountFreeReal(freeReal + incTicket);
            return true;
        }
        return false;
    }

    public boolean allToStringPremiere() {
        for (Premiere s : premiereMap.values()) {
            oneToStringPremiere(s.getName(), false);
        }
        return true;
    }

    public boolean oneToStringPremiere(String name, boolean ticket) {
        //System.out.println("premiereMap: " + premiereMap.toString());
        System.out.println("--> Премьера(Событие) : " + premiereMap.get(name).getName() +
                ", описание : " + premiereMap.get(name).getDescription() +
                ", возрастная категория : " + premiereMap.get(name).getAgeCategory() +
                ", кол-о дост-х мест : " + premiereMap.get(name).getCountFreeReal() +
                ", изначальное кол-о дост-х мест : " + premiereMap.get(name).getCountFreeSeats());
        if (ticket) {
            TicketService ticketService = new TicketService(logger);
            ticketService.allToStringTicket(name);
        }
        return true;
    }
}
