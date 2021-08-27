package proton.products;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import proton.base.BaseRepository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends BaseRepository<Product> {

    @Query(value = "SELECT d1 FROM Product d1 LEFT JOIN FETCH d1.produceEmployee d2 LEFT JOIN FETCH d1.customer d3")
    @Override
    List<Product> findAll();

//    List<ProductGeneral> findAll(Long id);

    List<ProductGeneral> findByCustomerId(Long id);


}
