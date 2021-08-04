package sumo.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sumo.entities.BzemDepartment;

/**
 * Класс, содержащий методы для работы с сущностями BzemDepartment<p>
 * 
 * @author Овсянников Сергей
 * @author Жигалов Александр
 * 
 * @see BzemDepartment
 *
 */
@Slf4j
@Component
public class BzemDepartmentsDAO {

    @Autowired
    @Qualifier("jdbcOracle")    
    private JdbcTemplate jdbcTemplate;
    
    /**
     * @return Список всех департаментов для использования методами класса
     */
    private List<BzemDepartment> getAll() {
        String sql = "SELECT departmentNumber, departmentParentNumber, departmentName FROM BZEM_DEPARTMENTS"; 
	log.debug(sql);
	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BzemDepartment.class));
    }
    
    /**
     * @return Список всех департаментов
     */
    public List<BzemDepartment> getAllDepartments() {
        List<BzemDepartment> departments = getAll();
        
        // Назначение родительских департаметов
        departments.forEach(department -> {
            for (BzemDepartment parent : departments) {
                if (department.getDepartmentParentNumber().equals(parent.getDepartmentNumber())) {
                    department.setParent(parent);
                }
            }
        });      
        
        return departments;
    }

    /**
     * Построение ветки департаментов типа: ChildDepartment - ParentDepartment - ParentDepartment ... - RootDepartment <p> 
     * @param childDepartment Департамент от которого строится ветка
     * @param departmentBranch Список для заполнения департаментами
     * @return Список департаментов
     */
    public List<BzemDepartment> getDepartmentBranch(BzemDepartment childDepartment, List<BzemDepartment> departmentBranch) {
        departmentBranch.add(childDepartment);
        BzemDepartment parentDepartment = childDepartment.getParent();
        if (parentDepartment == null) {
            return departmentBranch;
        }
        return getDepartmentBranch(childDepartment.getParent(), departmentBranch);
    }
    
    /**
     * Список департаментов, для которых parentDepartment, является родителем<p>
     * 
     * @param parentDepartment Департамент - родитель
     * @return Список департаментов
     */
    public List<BzemDepartment> getChildDepartments(BzemDepartment parentDepartment) {
        List<BzemDepartment> childDepartments = new ArrayList<>();
        
        for (BzemDepartment department : getAllDepartments()) {
            if (department.getParent() != null && department.getParent().equals(parentDepartment) ) {
                childDepartments.add(department);
            }
        }        
        return childDepartments;
    }
}

