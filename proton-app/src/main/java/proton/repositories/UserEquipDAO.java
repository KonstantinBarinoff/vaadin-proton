//package proton.repositories;
//
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//import proton.entities.UserEquip;
//
//@Slf4j
//@Component
//public class UserEquipDAO {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    // @Override
//    public List<UserEquip> getAllForUser(String equipUserLogin) {
//	log.warn("SELECT registrationNB, serialNB, groupName, modelName, locationName, userName "
//		+ "FROM VIEW_WEB_LIST_EQUIPMENTS");
//
//	return jdbcTemplate.query(
//		"SELECT registrationNB, serialNB, groupName, modelName, locationName, userName "
//			+ "FROM VIEW_WEB_LIST_EQUIPMENTS WHERE login = ?",
//		new Object[] { equipUserLogin }, new BeanPropertyRowMapper<>(UserEquip.class));
//    }
//
//    @PostConstruct
//    public void postConstruct() {
//	log.warn("POSTCONSTRUCT : {}", this.getClass().getName());
//    }
//}
