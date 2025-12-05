package rental.car.demo.notification;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import rental.car.demo.auto.application.AutoCreatedEvent;

@Component
public class AutoNotificationListener {

    @EventListener
    public void onAutoCreated(AutoCreatedEvent event) {
        System.out.println("Nuova auto creata: (ID= " + event.autoId() + " Casa Costruttrice: " + event.casaCostruttrice() + " Modello: " + event.modello() + " Anno Immatricolazione: " + event.annoImmatricolazione() + " Targa: " + event.targa() + " Tipo: " + event.autoType() +  ")");
    }
}
