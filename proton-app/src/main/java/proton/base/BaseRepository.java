package proton.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<E extends BaseDict> extends JpaRepository<E, Long> {

    @Query("""
    		SELECT e 
    		FROM #{#entityName} e 
    		WHERE e.name LIKE %?1% 
    			OR e.description LIKE %?1% 
    			""")
//	ORDER BY e.name
    <E extends BaseDict> List<E> findByFilter(String filter);
}