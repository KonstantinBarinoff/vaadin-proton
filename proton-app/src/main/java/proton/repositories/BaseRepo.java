package proton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import proton.entities.BaseDict;

@NoRepositoryBean
public interface BaseRepo<E extends BaseDict> extends JpaRepository<E, Long> {

}