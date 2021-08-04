//package proton.workfolders;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Repository
//public interface WorkFoldersRepository extends JpaRepository<WorkFolders, Long> {
//
////	List<WorkFolderV> findByFirstNameLastNameStartsWithIgnoreCase(String lastName);
////	List<WorkFolderV> findByNameStartsWithIgnoreCase(String name);
//
//    @Query("SELECT w FROM WorkFolders w WHERE w.name LIKE %?1% OR w.purpose LIKE %?1% "
//    	+ "OR w.description LIKE %?1% OR w.ownerName LIKE %?1%")
//    List<WorkFolders> findByFilter(String filter);
//    
//}
