package proton.employees;

import org.springframework.stereotype.Service;
import proton.repositories.BaseServiceImpl;

@Service
public class EmployeeService extends BaseServiceImpl<EmployeeDict, EmployeeRepository> {

    public EmployeeService(EmployeeRepository repository) {
        super(repository);
    }



//    @Override
//    public List<TestDict2> findAll() {
//        return repository.findAll();
//    }
}