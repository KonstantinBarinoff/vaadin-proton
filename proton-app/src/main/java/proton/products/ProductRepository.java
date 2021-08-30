package proton.products;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import proton.base.BaseRepository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends BaseRepository<Product> {

    @Query(value = """
        SELECT p FROM Product p
            LEFT JOIN FETCH p.produceEmployee
            LEFT JOIN FETCH p.checkEmployee
            LEFT JOIN FETCH p.customer
        """)
    @Override
    List<Product> findAll();

//    List<ProductGeneral> findAll(Long id);

    List<ProductSummary> findByCustomerId(Long id);


}
