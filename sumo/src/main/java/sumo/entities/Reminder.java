	package sumo.entities;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reminder {
    private Date 	reminder;
    private String	reminderUser;
    private Integer	reminderMailSent;
    
    private String	description;
    private String 	reason;
}
