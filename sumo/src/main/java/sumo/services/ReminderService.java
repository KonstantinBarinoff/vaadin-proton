package sumo.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sumo.entities.Reminder;
import sumo.repositories.ReminderDAO;

@Slf4j
@Component
public class ReminderService {

    private ReminderDAO reminderDAO;

    @Autowired
    public ReminderService(ReminderDAO reminderDAO) {
	this.reminderDAO = reminderDAO;
    }
    
    @PostConstruct
    public void checkReminders() {
	List <Reminder> reminders = reminderDAO.getAll();
	for (Reminder reminder : reminders) {
	    log.debug("remider: {} {}", reminder.getReason(), reminder.getReminder());
	}
	
    }

}
