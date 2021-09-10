package proton.scanned_docs;

import lombok.Getter;
import lombok.Setter;
import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Scanned_Docs")
@Getter
@Setter
public class ScannedDoc extends BaseDict {

    private String filePath;

    @Lob
    byte[] content;

}
