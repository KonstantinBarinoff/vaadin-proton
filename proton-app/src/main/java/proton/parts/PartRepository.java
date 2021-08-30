package proton.parts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proton.base.BaseRepository;

import java.util.List;

@Repository
public interface PartRepository extends BaseRepository<Part> {

    List<Part> findByProductId(Long id);

    /**
     * Выборка вложенных @ManyToOne сущностей с помошью одного запроса с вложенными LEFT JOIN FETCH
     * Отключение аннотации @Query приводит к 5-ти отдельным запросам
     */
    @Query(value = """
                SELECT part FROM Part part
                LEFT JOIN FETCH part.product product
                    LEFT JOIN FETCH product.produceEmployee
                    LEFT JOIN FETCH product.checkEmployee
                    LEFT JOIN FETCH product.customer
            """)
    @Override
    List<Part> findAll();

}
