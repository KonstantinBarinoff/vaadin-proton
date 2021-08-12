//package proton.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import proton.entities.SimpleDict1;
//
//import java.util.List;
//
//@Repository
//public interface SimpleDict1Repo extends JpaRepository<SimpleDict1, Long> {
//
//    List<SimpleDict1> findAll();
//
//    @Query("SELECT w FROM SimpleDict1 w WHERE w.name LIKE %?1%")
//    List<SimpleDict1> findByFilter(String filter);
//
//    // JPQL
//    @Query("SELECT w FROM SimpleDict1 w WHERE w.name LIKE %:name%")
//    List<SimpleDict1> findByName(@Param("name") String s);
//
//    // Native SQL
//    @Query(value = "SELECT * FROM SimpleDict1 w WHERE w.name LIKE %:name%", nativeQuery = true)
//    List<SimpleDict1> findByNameNative(@Param("name") String s);
//}
