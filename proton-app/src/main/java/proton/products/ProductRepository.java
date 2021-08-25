package proton.products;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import proton.base.BaseRepository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends BaseRepository<Product> {

    @Query(value = "SELECT d1 FROM Product d1 LEFT JOIN FETCH d1.produceEmployee d2 LEFT JOIN FETCH d1.customer d3")
    @NotNull
    @Override
    List<Product> findAll();

    List<ProductGeneral> findByCustomerId(Long id);


}
