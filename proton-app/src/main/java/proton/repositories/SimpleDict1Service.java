package proton.repositories;

import org.springframework.stereotype.Service;
import proton.entities.SimpleDict1;

import java.util.List;

@Service
public class SimpleDict1Service extends BaseServiceImpl<SimpleDict1, SimpleDict1Repository> {

    public SimpleDict1Service(SimpleDict1Repository repository) {
        super(repository);
    }

    @Override
    public List<SimpleDict1> findAll() {
        return repository.findAll();
    }
}