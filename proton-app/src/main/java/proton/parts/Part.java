package proton.parts;

import lombok.Getter;
import lombok.Setter;
import proton.base.BaseDict;
import proton.products.Product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Детали (изделия)
 */
@Entity
@Table(name = "Parts")
@Getter
@Setter
public class Part extends BaseDict {

    /** Изделие, которому принадлежит данная деталь */
    @ManyToOne()
    @JoinColumn(name="Product_Id", referencedColumnName = "id")
    private Product product;

}
