package rental.car.project.notification;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import rental.car.project.prenotazione.application.PrenotazioneCreatedEvent;
import rental.car.project.prenotazione.application.PrenotazioneDeletedEvent;
import rental.car.project.prenotazione.application.PrenotazioneUpdatedEvent;

@Component
public class PrenotazioneNotificationListener {

    @ApplicationModuleListener
    public void onPrenotazioneCreated(PrenotazioneCreatedEvent event) {
        System.out.println("Nuova prenotazione creata: (ID= " + event.prenotazioneId() +
                " User ID: " + event.userId() +
                " Auto ID: " + event.autoId() +
                " Data inizio prenotazione: " + event.inizioPrenotazione() +
                " Data fine prenotazione: " + event.finePrenotazione() + ")");
    }

    @ApplicationModuleListener
    public void onPrenotazioneUpdated(PrenotazioneUpdatedEvent event) {
        System.out.println("Prenotazione aggiornata: (ID= " + event.prenotazioneId() +
                ", Data inizio prenotazione: " + event.inizioPrenotazione() +
                ", Data fine prenotazione: " + event.finePrenotazione() + ")");
    }

    @ApplicationModuleListener
    public void onPrenotazioneDeleted(PrenotazioneDeletedEvent event) {
        System.out.println("Prenotazione eliminata: (ID= " + event.prenotazioneId());
    }

}
