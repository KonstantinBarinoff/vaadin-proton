package proton.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMDICT")
@RequiredArgsConstructor
@Getter
@Setter
public class CustomDict extends BaseDict {

    @NotEmpty
    @NotNull
    private Integer number;

    private LocalDate date;
    
    private BigDecimal price;	//MSSQL DECIMAL(18,2)
    
    private String email;

    private Boolean checked = false;

    @Column(name = "image")
    private byte[] image;

    public int isChecked() {
	if (checked == null)
	    return -1;
	return checked ? 1 : 0;
    }

}
