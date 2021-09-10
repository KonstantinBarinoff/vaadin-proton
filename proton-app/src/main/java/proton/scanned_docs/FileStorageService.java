package proton.scanned_docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
class FileStorageService {

    @Autowired
    FileStorageRepository fileSystemRepo;
    @Autowired
    ScannedDocRepository scannedDocRepo;

    //  TODO: initFileStorage(int width) - создание двухуровневой структуры папок width * width
    //      L1, L2 int - номера подпапок уровней сохраняются в базе
    //      check() - сканирование папок, проверка соответствия таблицы содержимому файлового хранилища

    Long save(byte[] bytes, String imageName) throws Exception {
        String filePath = fileSystemRepo.save(bytes, imageName);
        return scannedDocRepo.save(new ScannedDoc(imageName, filePath)).getId();
    }

    FileSystemResource find(Long imageId) {
        ScannedDoc image = scannedDocRepo.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return fileSystemRepo.findInFileSystem(image.getFilePath());
    }

}