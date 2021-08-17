package proton.repositories;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import proton.entities.TestDict2;

import javax.persistence.LockModeType;

@Service
public class TestDict2Service extends BaseServiceImpl<TestDict2, TestDict2Repository> {

    public TestDict2Service(TestDict2Repository repository) {
        super(repository);
    }



//    @Override
//    public List<TestDict2> findAll() {
//        return repository.findAll();
//    }
}