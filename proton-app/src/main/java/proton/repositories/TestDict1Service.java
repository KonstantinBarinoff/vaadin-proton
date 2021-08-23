package proton.repositories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proton.entities.TestDict1;

@Service
@Transactional
public class TestDict1Service extends BaseServiceImpl<TestDict1, TestDict1Repository> {

    public TestDict1Service(TestDict1Repository repository) {
        super(repository);
    }

//    @Override
//    public List<TestDict1> findAll() {
//        return repository.findAll();
//    }
}