package sumo.repositories;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import sumo.entities.BzemDepartment;


/**
 * Класс, содержащий методы для работы с сущностями BzemDepartment
 * 
 * @author Овсянников Сергей
 * @author Жигалов Александр
 * @see BzemDepartment
 *
 */
@Slf4j
@Component
public class BzemDepartmentsDAO {

    @Autowired
    @Qualifier("jdbcOracle")
    private JdbcTemplate jdbcTemplate;
    private List<BzemDepartment> departmentList;


    /**
     * Сеттер для departmentList (c назначенными родительскими департаментами)
     */
    @Autowired
    private void setDepartmentList() {
        String sql = "SELECT departmentNumber, departmentParentNumber, departmentName FROM bzem_departments";
        log.debug(sql);
        List<BzemDepartment> departmentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BzemDepartment.class));
        departmentList.forEach(department -> {
            for (BzemDepartment parent : departmentList) {
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
    private static BzemDepartment getGrandRootDepartment(BzemDepartment department) {
        BzemDepartment parentDepartment = department.getParent();
        if (parentDepartment == null) {
            return department;
        }
        return getGrandRootDepartment(department.getParent());
    }

    /**
     * Получение департаметов в качестве корневых, для которых департамент самого верхнгего уровня является родителем
     * @return Список корневых департаметов
     */
    public List<BzemDepartment> getRootDepartments() {
        BzemDepartment grandRoot = getGrandRootDepartment(departmentList.get(0));
        return departmentList.stream()
                .filter(department -> department.getParent() != null && department.getParent().equals(grandRoot))
                .collect(Collectors.toList());

    }

    /**
     * Получение департаметов-детей
     * @param parent Родительский департамент
     * @return Список департаметов-детей
     */
    public List<BzemDepartment> getChildDepartments(BzemDepartment parent) {
        return departmentList.stream()
                .filter(department -> Objects.equals(department.getParent(), parent))
                .collect(Collectors.toList());

    }
}