package sumo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sumo.entities.UserEquip;

@Slf4j
@Component
public class SupportEquipDAO {

    @Autowired
    @Qualifier("jdbcSumo")
    private JdbcTemplate jdbcTemplate;

//    public List<UserEquip> getAllForUser(String equipUserLogin) {
//	return jdbcTemplate.query(
//		"SELECT registrationNB, serialNB, groupName, modelName, locationName, userName "
//			+ "FROM VIEW_WEB_LIST_SUPPORT_EQUIP WHERE login = ?",
//			new Object[] { equipUserLogin }, new BeanPropertyRowMapper<>(UserEquip.class));
//    }

    public List<UserEquip> getAll() {
	String sql = """
		SELECT registrationNB, serialNB, groupName, modelName, locationName, userName, phone, title
		FROM 	VIEW_WEB_LIST_SUPPORT_EQUIP """;
	log.debug(sql);
	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEquip.class));
    }

    public List<UserEquip> getByFilter(String filter) {
	String sql = """
		SELECT registrationNB, serialNB, groupName, modelName, locationName, userName, phone, title
		FROM VIEW_WEB_LIST_SUPPORT_EQUIP
		WHERE registrationNB LIKE ?
			OR serialNB LIKE ?
			OR groupName LIKE ?
			OR modelName LIKE ?
			OR locationName LIKE ?
			OR userName LIKE ? """;
	filter = "%" + filter + "%";
	log.debug(sql);
	return jdbcTemplate.query(sql, new Object[] { filter, filter, filter, filter, filter, filter },
		new BeanPropertyRowMapper<>(UserEquip.class));
    }

}
