package sumo.repositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import sumo.entities.SapDepartment;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Класс, содержащий методы для работы с сущностями BzemDepartment
 *
 * @author Овсянников Сергей
 * @author Жигалов Александр
 * @see SapDepartment
 *
 */
@Slf4j
@Component
public class SapDepartmentsDAO {

    @Autowired
    @Qualifier("jdbcSumo")
    private JdbcTemplate jdbcTemplate;
    private List<SapDepartment> departmentList;


    /**
     *  Построение departmentList (c назначенными родительскими департаментами)
     */
    @Autowired
    private void setupDepartmentList() {
        String sql = "SELECT departmentNumber, departmentParentNumber, departmentName FROM SapDepartments";
        log.debug(sql);
        List<SapDepartment> departmentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SapDepartment.class));
        departmentList.forEach(department -> {
            for (SapDepartment parent : departmentList) {
                if (department.getDepartmentParentNumber().equals(parent.getDepartmentNumber())) {
                    department.setParent(parent);
                }
            }
        });
        this.departmentList = departmentList;
    }

    /**
     * Получение корневого департамета (предприятие, завод и т.д.)
     * @param department Департамент с которого начинается рекурсивный обход
     * @return Корневой департамент
     */
    private static SapDepartment getGrandRootDepartment(SapDepartment department) {
        SapDepartment parentDepartment = department.getParent();
        if (parentDepartment == null) {
            return department;
        }
        return getGrandRootDepartment(department.getParent());
    }

    /**
     * Получение департаметов в качестве корневых, для которых департамент самого верхнгего уровня является родителем
     * @return Список корневых департаметов
     */
    public List<SapDepartment> getRootDepartments() {
        SapDepartment grandRoot = getGrandRootDepartment(departmentList.get(0));
        return departmentList.stream()
                .filter(department -> department.getParent() != null && department.getParent().equals(grandRoot))
                .collect(Collectors.toList());

    }

    /**
     * Получение департаметов-детей
     * @param parent Родительский департамент
     * @return Список департаметов-детей
     */
    public List<SapDepartment> getChildDepartments(SapDepartment parent) {
        return departmentList.stream()
                .filter(department -> Objects.equals(department.getParent(), parent))
                .collect(Collectors.toList());

    }
}