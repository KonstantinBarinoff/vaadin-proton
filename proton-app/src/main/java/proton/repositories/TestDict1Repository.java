package proton.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import proton.entities.TestDict1;

import java.util.List;

@Repository
@Transactional
public interface TestDict1Repository extends BaseRepo<TestDict1> {

    @Query(value = "SELECT d1 FROM TestDict1 d1 LEFT JOIN FETCH d1.employeeDict d2")
    List<TestDict1> findAll();


}
