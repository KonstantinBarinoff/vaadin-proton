package proton.scanned_docs;

import org.springframework.stereotype.Service;
import proton.base.BaseServiceImpl;

@Service
public class ScannedDocService extends BaseServiceImpl<ScannedDoc, ScannedDocRepository> {
    public ScannedDocService(ScannedDocRepository repository) {
        super(repository);
    }
}