package proton.repositories;

import proton.entities.BaseDict;

import java.util.List;

public interface BaseService<E extends BaseDict>{

//    E save(E entity);

      List<E> findAll(); // you might want a generic Collection if u prefer
//
//    Optional<E> findById(Long entityId);
//    E update(E entity);
//    E updateById(E entity, Long entityId);
//    void delete(E entity);
//    void deleteById(Long entityId);

    // other methods u might need to be generic

}