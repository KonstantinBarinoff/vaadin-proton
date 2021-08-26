package proton.parts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proton.base.BaseRepository;

import java.util.List;

@Repository
public interface PartRepository extends BaseRepository<Part> {

    @Query("select p from Part p where p.product.id = ?1")
    List<Part> findByProductId(Long id);
}
