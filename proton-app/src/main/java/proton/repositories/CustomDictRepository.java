package proton.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import proton.entities.CustomDict;

@Repository
public interface CustomDictRepository extends JpaRepository<CustomDict, Long> {

    List<CustomDict> findAll();

    @Query("SELECT w FROM CustomDict w WHERE w.name LIKE %?1%")
    List<CustomDict> findByFilter(String filter);

    @Query("SELECT w FROM CustomDict w WHERE w.name LIKE %:name%") // JPQL
    List<CustomDict> findByName(@Param("name") String s);

    @Query(value = "SELECT * FROM CustomDict w WHERE w.name LIKE %:name%", nativeQuery = true) // Native SQL
    List<CustomDict> findByNameNative(@Param("name") String s);
}
