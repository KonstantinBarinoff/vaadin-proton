package proton.scanned_docs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Scanned_Docs")
@Getter
@Setter
@NoArgsConstructor
public class ScannedDoc extends BaseDict {

    private String filePath;

    @Lob
    byte[] content;

    public ScannedDoc(String imageName, String filePath) {
        this.name = imageName;
        this.filePath = filePath;
    }
}
