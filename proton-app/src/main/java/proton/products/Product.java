package proton.products;

import lombok.Getter;
import lombok.Setter;
import proton.base.BaseDict;
import proton.customers.Customer;
import proton.employees.Employee;

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

    /** Пример поля с дробным числом */
    private Double coefficient;         // MSSQL: Float

    /** Пример целого поля */
    private Integer number;             // MSSQL: Integer

    /** Пример поля с датой */
    private LocalDate date;             // MSSQL: Date

    /** Пример поля с датой и временем*/
    private LocalDateTime dateTime;     // MSSQL: Data type: DateTime       Column name: Date_Time

    /** Пример поля с отметкой */
    private Boolean checked;            // MSSQL: Bit

    /** Сотрудник изготовивший изделие */
    @ManyToOne()
    @JoinColumn(name="Produce_Employee_Id", referencedColumnName = "id")
    private Employee produceEmployee;

    /** Сотрудник проверивший изделие */
    @ManyToOne()
    @JoinColumn(name="Check_Employee_Id", referencedColumnName = "id")
    private Employee checkEmployee;

    /** Заказчик изделия */
    @ManyToOne()
    @JoinColumn(name="Customer_Id", referencedColumnName = "id")
    private Customer customer;

}
