package proton.products;

import lombok.Getter;
import lombok.Setter;
import proton.customers.Customer;
import proton.employees.Employee;
import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Производимые изделия
 */
@Entity
@Table(name = "Products")
@Getter
@Setter
public class Product extends BaseDict {

    private Double coefficient;         // MSSQL: Float

    private Integer number;             // MSSQL: Integer

    private LocalDate date;             // MSSQL: Date

    private LocalDateTime dateTime;     // MSSQL: Data type: DateTime       Column name: Date_Time

    private Boolean checked;            // MSSQL: Bit

//    @Column(name="Dict2_Id", insertable = false, updatable = false)  // TODO: Why doesnt work implicit dict2_id?
//    private Integer dict2Id;


    @ManyToOne()
    @JoinColumn(name="Produce_Employee_Id", referencedColumnName = "id")
    private Employee produceEmployee;

    @ManyToOne()
    @JoinColumn(name="Check_Employee_Id", referencedColumnName = "id")
    private Employee checkEmployee;

    @ManyToOne()
    @JoinColumn(name="Customer_Id", referencedColumnName = "id")
    private Customer customer;

}
