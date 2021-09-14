package proton.scanned_docs;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
class FileStorageRepository {

    String save(byte[] content, String fullPath) throws Exception {
        Path newFile = Paths.get(fullPath);
        Files.createDirectories(newFile.getParent());
        Files.write(newFile, content);

        return newFile.toAbsolutePath().toString();
    }

    FileSystemResource findByFullPath(String fullPath) {
        try {
            return new FileSystemResource(Paths.get(fullPath));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }

}