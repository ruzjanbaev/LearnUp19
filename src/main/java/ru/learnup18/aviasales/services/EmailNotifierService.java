package ru.learnup18.aviasales.services;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import ru.learnup18.aviasales.model.EmailNotifier;
import ru.learnup18.aviasales.services.interfaces.Logger;
import java.util.HashMap;

@Service
public class EmailNotifierService implements ApplicationContextAware {
    private Logger logger;
    private ApplicationContext ctx;
    private HashMap<String, EmailNotifier> emailNotifierMap;

    @Autowired
    public EmailNotifierService(Logger logger, HashMap emailNotifierMap) {
        this.logger = logger;
        this.emailNotifierMap = emailNotifierMap;
    }

    public void insEmailNotifier(EmailNotifier emailNotifier) {
        logger.print("insEmailNotifier: " + emailNotifier.getName());
        emailNotifierMap.put(emailNotifier.getName(), emailNotifier);
    }

    //public void EmailNotifierSend(String title, String info) {
    public void EmailNotifierSend(String event) {
        if (emailNotifierMap.isEmpty()) {
            emailNotifierMap = ctx.getBean(EmailNotifierService.class).emailNotifierMap;
        }
        //System.out.println("event.toString() = " + event.toString());
        if (event == "PremiereIns") {
            PremiereService premiereService = ctx.getBean(PremiereService.class);
            for (EmailNotifier s : emailNotifierMap.values()) {
                premiereService.sendMailIns(s.getName(), s.getEmail());
            }
        } else if (event == "PremiereUpd") {
                PremiereService premiereService = ctx.getBean(PremiereService.class);
                for (EmailNotifier s : emailNotifierMap.values()) {
                    premiereService.sendMailUpd(s.getName(), s.getEmail());
                }
        } else if (event == "Ticket") {
            TicketService ticketService = ctx.getBean(TicketService.class);
            for (EmailNotifier s : emailNotifierMap.values()) {
                ticketService.sendMail(s.getName(), s.getEmail());
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }
}
