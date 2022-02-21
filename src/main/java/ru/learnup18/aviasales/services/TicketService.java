package ru.learnup18.aviasales.services;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import ru.learnup18.aviasales.events.EmailNotifierEvent;
import ru.learnup18.aviasales.model.Ticket;
import ru.learnup18.aviasales.services.interfaces.Logger;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class TicketService implements ApplicationContextAware{

    private Logger logger;
    static private HashMap<String, Ticket> ticketMap = new HashMap<String, Ticket>();
    private ApplicationContext ctx;

    //@Autowired
    public TicketService(Logger logger) {
        this.logger = logger;
    }

    //Добавление - покупка
    public Ticket insTicket(String name, String premiereName) {
        logger.print("insTicket: " + name);
        Ticket ticket = null;
        PremiereService premiereService = new PremiereService(logger);
        // кргда покупк, билет минус один
        if (premiereService.incSeatsRealPremiere(premiereName, -1)) {
            ticket = new Ticket(name, premiereName);
            if (ticketMap == null) {
                ticketMap = new HashMap<String, Ticket>();
            }
            ticketMap.put(name, ticket);
            new EmailNotifierEvent(
                    EmailNotifierEvent.Info.of(
                            "Покупка билета " + name,
                            "Покупка билета " + name + ", на мероприятие " + premiereName + "."));
        }
        return ticket;
    }
    @PostConstruct
    public void insTicketPost() {
        System.out.println("insTicketPost = " + this.getClass().getSimpleName());
    }
    //Удаление - сдать билет
    public boolean delTicket(String name, String premiereName) {
        logger.print("delTicket: " + name);
        PremiereService premiereService = new PremiereService(logger);
        if (premiereService.incSeatsRealPremiere(premiereName, 1)) {
            ticketMap.remove(name);
        }
        return true;
    }
    public boolean delTicketPremiere(String premiereName) {
        logger.print("delTicket premiereName: " + premiereName);
        HashMap<String, Ticket> ticketMapTmp = new HashMap<String, Ticket>();
        ticketMapTmp = (HashMap) ticketMap.clone();
        for (Ticket s : ticketMapTmp.values()) {
            if (s.getPremiereName() == premiereName) {
                ticketMap.remove(s.getName());
            }
        }
        return true;
    }

    public boolean allToStringTicket() {
        //System.out.println("allToStringTicket: " + ticketMap.toString());
        for (Ticket s : ticketMap.values()) {
            System.out.println("--> Билет : " + s.getName() + ". Премьера(Событие) : " + s.getPremiereName());
        }
        return true;
    }

    public boolean allToStringTicket(String premiereName) {
        //System.out.println("allToStringTicket: " + ticketMap.toString());
        for (Ticket s : ticketMap.values()) {
            if (s.getPremiereName() == premiereName) {
                System.out.println("----> Билет : " + s.getName() + ". Премьера(Событие) : " + s.getPremiereName());
            }
        }
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = ctx;
    }
}
