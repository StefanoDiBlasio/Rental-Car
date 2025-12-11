package rental.car.project.notification;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import rental.car.project.auto.application.AutoCreatedEvent;
import rental.car.project.auto.application.AutoDeletedEvent;
import rental.car.project.auto.application.AutoUpdatedEvent;

@Component
public class AutoNotificationListener {

    @ApplicationModuleListener
    public void onAutoCreated(AutoCreatedEvent event) {
        System.out.println("Nuova auto creata: (ID= " + event.autoId() +
                " Casa Costruttrice: " + event.casaCostruttrice() +
                " Modello: " + event.modello() +
                " Anno di Immatricolazione: " + event.annoImmatricolazione() +
                " Targa: " + event.targa() +
                " Tipo: " + event.autoType() + ")");
    }

    @ApplicationModuleListener
    public void onAutoUpdated(AutoUpdatedEvent event) {
        System.out.println("Auto aggiornata: (ID= " + event.autoId() +
                ", Casa Costruttrice: " + event.casaCostruttrice() +
                ", Modello: " + event.modello() +
                ", Anno di Immatricolazione: " + event.annoImmatricolazione() +
                ", Targa: " + event.targa() +
                ", Tipo: " + event.autoType() + ")");
    }

    @ApplicationModuleListener
    public void onAutoDeleted(AutoDeletedEvent event) {
        System.out.println("Auto eliminata: (ID= " + event.autoId());
    }
}
