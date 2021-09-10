package proton.custom_dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomDictRepo extends JpaRepository<CustomDict, Long> {

    List<CustomDict> findAll();

    @Query("SELECT w FROM CustomDict w WHERE w.name LIKE %?1%")
    List<CustomDict> findByFilter(String filter);

    // JPQL
    @Query("SELECT w FROM CustomDict w WHERE w.name LIKE %:name%")
    List<CustomDict> findByName(@Param("name") String s);

    // Native SQL
    @Query(value = "SELECT * FROM CustomDict w WHERE w.name LIKE %:name%", nativeQuery = true)
    List<CustomDict> findByNameNative(@Param("name") String s);
}
