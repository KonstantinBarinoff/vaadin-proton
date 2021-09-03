package proton.base;

import java.util.List;
import java.util.Optional;

// TODO: Проверить механизм работы со Stored Procedures
public interface BaseService<E extends BaseDict> {

    List<E> findAll();

    List<E> findAll(String filter);

    Optional<E> findById(Long id);

    E save(E entity);

    void delete(E entity);

    void deleteById(Long id);

    boolean existsById(Long id);

}

