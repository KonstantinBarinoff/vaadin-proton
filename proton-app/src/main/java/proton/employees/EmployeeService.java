package proton.employees;

import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

@Service
public class EmployeeService extends BaseServiceImpl<Employee, EmployeeRepository> {


    public EmployeeService(EmployeeRepository repository) {
        super(repository);

    }
}