package proton.employees;

import org.springframework.stereotype.Repository;
import proton.repositories.BaseRepo;

@Repository
public interface EmployeeRepository extends BaseRepo<EmployeeDict> {
}
