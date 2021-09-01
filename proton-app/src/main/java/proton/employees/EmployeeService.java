package proton.employees;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

@Service
@Slf4j
public class EmployeeService extends BaseServiceImpl<Employee, EmployeeRepository> {


    public EmployeeService(EmployeeRepository repository) {
        super(repository);
        log.debug("CONSTRUCTOR");


    }
}