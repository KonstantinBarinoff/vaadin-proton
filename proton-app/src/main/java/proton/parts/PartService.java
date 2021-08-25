package proton.parts;

import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

@Service
public class PartService extends BaseServiceImpl<Part, PartRepository> {

    public PartService(PartRepository repository) {
        super(repository);
    }
}