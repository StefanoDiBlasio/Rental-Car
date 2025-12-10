package rental.car.project.notification;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import rental.car.project.user.application.UserCreatedEvent;
import rental.car.project.user.application.UserDeletedEvent;
import rental.car.project.user.application.UserUpdatedEvent;

@Component
public class UserNotificationListener {

    @ApplicationModuleListener
    public void onUserCreated(UserCreatedEvent event) {
        System.out.println("Nuovo utente creato: (ID= " + event.userId() +
                ", Username= " + event.username() +
                ", Nome= " + event.firstName() +
                ", Cognome= " + event.lastName() +
                ", Data di nascita= " + event.birthDate() +
                ", Ruolo= " + event.roleType() + ")");
    }

    @ApplicationModuleListener
    public void onUserUpdated(UserUpdatedEvent event) {
        System.out.println("Utente aggiornato: (ID= " + event.userId() +
                ", Nome= " + event.firstName() +
                ", Cognome= " + event.lastName() +
                ", Data di nascita= " + event.birthDate());
    }

    @ApplicationModuleListener
    public void onUserDeleted(UserDeletedEvent event) {
        System.out.println("Utente eliminato: (ID= " + event.userId());
    }
}
