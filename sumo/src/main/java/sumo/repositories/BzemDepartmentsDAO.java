package sumo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sumo.entities.BzemDepartment;

@Slf4j
@Component
public class BzemDepartmentsDAO {

    @Autowired
    @Qualifier("jdbcOracle")    
    private JdbcTemplate jdbcTemplate;
    
    public List<BzemDepartment> getAll() {
	String sql = "SELECT departmentNumber, departmentParentNumber, departmentName FROM BZEM_DEPARTMENTS"; 
	log.debug(sql);
	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BzemDepartment.class));
    }

    public List<BzemDepartment> getAllDistincted() {
	String sql = "SELECT DISTINCT departmentName FROM BZEM_DEPARTMENTS"; 
	log.debug(sql);
	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BzemDepartment.class));
    }
  
  
}
