package ru.learnup18.aviasales.events;

import org.springframework.context.ApplicationEvent;
import ru.learnup18.aviasales.services.EmailNotifierService;

public class EmailNotifierEvent extends ApplicationEvent {

    public EmailNotifierEvent(Info info) {
        super(info);
    }

    public static class Info {
        public final String title;
        public final String info;

        private Info(String title, String info) {
            this.title = title;
            this.info = info;
        }

        public static Info of(String title, String info) {

            return new Info(title, info);
        }
    }
}
