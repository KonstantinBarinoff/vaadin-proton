package proton.repositories;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import proton.entities.TestDict1;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface TestDict1Repository extends BaseRepo<TestDict1> {


}
