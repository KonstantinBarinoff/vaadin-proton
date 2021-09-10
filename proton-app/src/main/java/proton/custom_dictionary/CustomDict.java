package proton.custom_dictionary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import proton.base.BaseDict;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMDICT")
@RequiredArgsConstructor
@Getter
@Setter
public class CustomDict extends BaseDict {

    @NotEmpty
    @NotNull
    private Integer number = 0;

    // MSSQL: DATETIME
    private LocalDate date;

    //MSSQL DECIMAL(18,2)
    private BigDecimal price;

    private String email;

    private Boolean checked = false;

    @Column(name = "image")
    private byte[] image;

    public boolean isImagePresent() {
        return image != null && image.length > 0;
    }

    public int isChecked() {
        if (checked == null)
            return -1;
        return checked ? 1 : 0;
    }

}
