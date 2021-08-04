package util;

import com.vaadin.flow.component.notification.Notification;

public class ProtonNotification extends Notification {

    public static Notification showWarning(String text) {
        Notification notification = new Notification(text, 1000, Position.MIDDLE);
        notification.open();
        return notification;
    }
    
}
