package proton.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TESTDICT1")
@Getter
@Setter
public class TestDict1 extends BaseDict {

    @Column(name = "Coefficient")
    private Double coefficient;

}
