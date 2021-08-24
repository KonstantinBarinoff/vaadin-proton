package proton.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<E extends BaseDict> {

    List<E> findAll();

    Optional<E> findById(Long id);

    E save(E entity);

    void delete(E entity);

    void deleteById(Long id);

    boolean existsById(Long id);

}

