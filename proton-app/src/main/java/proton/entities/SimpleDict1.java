package proton.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SIMPLEDICT1")
@Getter
@Setter
public class SimpleDict1 extends BaseDict {

    @Column(name = "Coefficient")
    private Double coefficient;

}
