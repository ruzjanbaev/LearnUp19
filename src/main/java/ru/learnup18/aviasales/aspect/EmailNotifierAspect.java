package ru.learnup18.aviasales.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ru.learnup18.aviasales.model.Premiere;
import ru.learnup18.aviasales.services.EmailNotifierService;
import java.util.HashMap;

@Component
@Aspect
//public class EmailNotifierAspect extends ApplicationContextEvent {
public class EmailNotifierAspect implements ApplicationContextAware {

    private ApplicationContext ctx;
    private HashMap<String, Premiere> premiereMap;

    @Pointcut("execution(* ru.learnup18.aviasales.services.PremiereService.insPremiere (..))")
    public void insPremiere() {
    }

    @Pointcut("execution(* ru.learnup18.aviasales.services.PremiereService.updPremiere (..))")
    public void updPremiere() {
    }


    @Pointcut("execution(* ru.learnup18.aviasales.services.TicketService.insTicket (..))")
    public void insTicket() {
    }

    //Анонс мероприятия
    @After("insPremiere()")
    public void send_mailPremiere(JoinPoint joinPoint) {
        EmailNotifierService emailNotifierService = ctx.getBean(EmailNotifierService.class);
        emailNotifierService.EmailNotifierSend("PremiereIns");
    }

    //Изменение мероприятия
    @After("updPremiere()")
    public void send_mailPremiereUpd(JoinPoint joinPoint) {
        EmailNotifierService emailNotifierService = ctx.getBean(EmailNotifierService.class);
        emailNotifierService.EmailNotifierSend("PremiereUpd");
    }

    //Покупка билета
    @After("insTicket()")
    public void send_mailTicket(JoinPoint joinPoint) {
        EmailNotifierService emailNotifierService = ctx.getBean(EmailNotifierService.class);
        emailNotifierService.EmailNotifierSend("Ticket");
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

}
