package proton.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TESTDICT1")
@Getter
@Setter
public class TestDict1 extends BaseDict {

    private Double coefficient;         // MSSQL: Float

    private Integer number;             // MSSQL: Integer

    private LocalDate date;             // MSSQL: Date

    private LocalDateTime dateTime;     // MSSQL: Data type: DateTime       Column name: Date_Time

    private Boolean checked;            // MSSQL: Bit

    @Column(name="Dict2_Id", insertable = false, updatable = false)  // TODO: Why doesnt work implicit dict2_id?
    private Integer dict2Id;

    @OneToOne
    @JoinColumn(name="Dict2_Id", referencedColumnName = "id")
    private TestDict2 testDict2;
}
