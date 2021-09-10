package proton.scanned_docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import util.ProtonProperties;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
class FileStorageRepository {

    @Autowired
    private final ProtonProperties properties;

    FileStorageRepository(ProtonProperties properties) {
        this.properties = properties;
    }

//    String RESOURCES_DIR = FileSystemRepository.class.getResource("/").getPath();

    String save(byte[] content, String imageName) throws Exception {
        String RESOURCES_DIR = properties.getFileStoragePath();
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);
        Files.createDirectories(newFile.getParent());
        Files.write(newFile, content);

        return newFile.toAbsolutePath().toString();
    }

    FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }
}