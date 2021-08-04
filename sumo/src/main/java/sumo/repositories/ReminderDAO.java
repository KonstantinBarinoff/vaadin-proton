package sumo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sumo.entities.Reminder;
import sumo.entities.UserEquip;

@Slf4j
@Component
public class ReminderDAO {

    @Autowired
    @Qualifier("jdbcSumo")
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private ApplicationContext context;    
    
    
    public List<Reminder> getAll() {
	String sql = """
		SELECT reminder, reminderUser, reminderMailSent, description, reason
		FROM EQUIPMENTS 
		WHERE reminder IS NOT NULL
			AND reminder < GETDATE()
		""";
	log.debug(sql);
	log.debug("ApplicationContext: {}",context.getDisplayName());
	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reminder.class));
    }
    
}


