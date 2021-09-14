package proton.scanned_docs;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Scanned_Docs")
@Getter
@Setter
public class ScannedDoc extends BaseDict {

    ScannedDoc(long id, String fileName, int width) {
        setId(id);
        setName(fileName);
        this.guid = UUID.randomUUID() + ".blob";
        this.fileName = fileName;
        this.extension = FilenameUtils.getExtension(fileName);

        int l1 = (int) (id / width);
        while (l1 / width > width) {
            l1 = l1 / width;
        }
        int l2 = (int) (id % width);
        this.level1 = l1 + 1;
        this.level2 = l2 + 1;
    }

    /** Оригинальное имя загруженного файла */
    private String fileName;

    /** Подпапка 1-го уровня [1..file_storage_width] */
    private int level1;

    /** Подпапка 2-го уровня [1..file_storage_width] */
    private int level2;

    /** Имя файла в хранилище заменяется на guid.blob (напр. 702e6113-d24b-4aea-9d98-2fcc9e932433.blob) */
    private String  guid;

    /** оригинальное расширение загруженного файла */
    private String extension;

    public ScannedDoc() {
    }

    /** Путь и имя файла относительно file_storage_path */
    public String getFilePathName() {
        return "/" + String.valueOf(level1) + "/" + String.valueOf(level2) + "/" + guid;
    }


}
