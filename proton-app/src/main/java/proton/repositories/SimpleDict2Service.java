package proton.repositories;

import org.springframework.stereotype.Service;
import proton.entities.SimpleDict2;

import java.util.List;

@Service
public class SimpleDict2Service extends AbstractService<SimpleDict2, SimpleDict2Repository> {

    public SimpleDict2Service(SimpleDict2Repository repository) {
        super(repository);
    }

    @Override
    public List<SimpleDict2> findAll() {
        return repository.findAll();
    }
}