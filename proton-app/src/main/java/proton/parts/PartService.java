package proton.parts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

import java.util.List;

@Service
@Slf4j
public class PartService extends BaseServiceImpl<Part, PartRepository> {

    public PartService(PartRepository repository) {
        super(repository);
        log.debug("CONSTRUCTOR");
    }

    public List<Part> findByProductId(Long id, String filter) {
        if (filter == null || filter.isEmpty()) {
            return repository.findByProductId(id);
        } else {
            return repository.findByProductIdFilter(id, filter);
        }
    }

}