//package proton.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import proton.entities.TestDict2;
//
//import java.util.List;
//
//@Repository
//public interface TestDict2CustomRepo extends JpaRepository<TestDict2, Long> {
//
//    List<TestDict2> findAll();
//
//    @Query("SELECT w FROM TestDict2 w WHERE w.name LIKE %?1%")
//    List<TestDict2> findByFilter(String filter);
//
//    // JPQL
//    @Query("SELECT w FROM TestDict2 w WHERE w.name LIKE %:name%")
//    List<TestDict2> findByName(@Param("name") String s);
//
//    // Native SQL
//    @Query(value = "SELECT * FROM SimpleDict2 w WHERE w.name LIKE %:name%", nativeQuery = true)
//    List<TestDict2> findByNameNative(@Param("name") String s);
//}
