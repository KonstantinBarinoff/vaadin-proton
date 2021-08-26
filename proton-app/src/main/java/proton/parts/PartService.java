package proton.parts;

import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

import java.util.List;

@Service
public class PartService extends BaseServiceImpl<Part, PartRepository> {

    public PartService(PartRepository repository) {
        super(repository);
    }

    public List<Part> findByProductId(Long id) {
        return repository.findByProductId(id);
    }
}