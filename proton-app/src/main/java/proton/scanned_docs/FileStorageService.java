package proton.scanned_docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import util.ProtonProperties;

@Service
class FileStorageService {

    @Autowired
    private FileStorageRepository fileSystemRepo;
    @Autowired
    private ScannedDocRepository scannedDocRepo;
    @Autowired
    private ProtonProperties properties;


    ScannedDoc save(byte[] bytes, String imageName) throws Exception {

        ScannedDoc scannedDoc = new ScannedDoc(scannedDocRepo.getNextId(), imageName, properties.getFileStorageWidth());

        String storagePath = org.apache.commons.lang3.StringUtils.removeEnd(properties.getFileStoragePath(), "/");
        String fullPathName = storagePath + scannedDoc.getFilePathName();

        fileSystemRepo.save(bytes, fullPathName);

        return scannedDoc;
    }

    FileSystemResource findById(Long id) {
        String storagePath = org.apache.commons.lang3.StringUtils.removeEnd(properties.getFileStoragePath(), "/");
        ScannedDoc scannedDoc = scannedDocRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return fileSystemRepo.findByFullPath(storagePath + "/" + scannedDoc.getFilePathName());
    }

}