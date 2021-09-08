package proton.employees;

import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сотрудник (производства)
 */
@Entity
@Table(name = "Employees")
public class Employee extends BaseDict {

}
