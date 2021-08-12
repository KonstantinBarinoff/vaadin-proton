package proton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proton.entities.SimpleDict2;

import java.util.List;

@Repository
public interface SimpleDict2Repo extends JpaRepository<SimpleDict2, Long> {

    List<SimpleDict2> findAll();

    @Query("SELECT w FROM SimpleDict2 w WHERE w.name LIKE %?1%")
    List<SimpleDict2> findByFilter(String filter);

    // JPQL
    @Query("SELECT w FROM SimpleDict2 w WHERE w.name LIKE %:name%")
    List<SimpleDict2> findByName(@Param("name") String s);

    // Native SQL
    @Query(value = "SELECT * FROM SimpleDict2 w WHERE w.name LIKE %:name%", nativeQuery = true)
    List<SimpleDict2> findByNameNative(@Param("name") String s);
}
