package sumo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sumo.entities.WorkFolderEntity;

import java.util.List;

@Repository
public interface WorkFoldersRepository extends JpaRepository<WorkFolderEntity, Long> {

//	List<WorkFolderV> findByFirstNameLastNameStartsWithIgnoreCase(String lastName);
//	List<WorkFolderV> findByNameStartsWithIgnoreCase(String name);
    
    @Query("""
    		SELECT w 
    		FROM WorkFolderEntity w 
    		WHERE w.name LIKE %?1% 
    			OR w.purpose LIKE %?1% 
    			OR w.description LIKE %?1% 
    			OR w.ownerName LIKE %?1%
    		ORDER BY NAME
    			""")    
    List<WorkFolderEntity> findByFilter(String filter);

}
