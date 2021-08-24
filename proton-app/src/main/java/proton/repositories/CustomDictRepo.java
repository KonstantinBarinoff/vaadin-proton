package proton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proton.base.CustomDictionary;

import java.util.List;

@Repository
public interface CustomDictRepo extends JpaRepository<CustomDictionary, Long> {

    List<CustomDictionary> findAll();

    @Query("SELECT w FROM CustomDictionary w WHERE w.name LIKE %?1%")
    List<CustomDictionary> findByFilter(String filter);

    // JPQL
    @Query("SELECT w FROM CustomDictionary w WHERE w.name LIKE %:name%")
    List<CustomDictionary> findByName(@Param("name") String s);

    // Native SQL
    @Query(value = "SELECT * FROM CustomDict w WHERE w.name LIKE %:name%", nativeQuery = true)
    List<CustomDictionary> findByNameNative(@Param("name") String s);
}
