package proton.employees;

import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сотрудник (производства)
 */
@Entity
@Table(name = "Employees")
//@SQLDelete(sql = "UPDATE Employees SET deleted=1 WHERE id=? AND version=?")
//@Where(clause = "deleted=false")
public class Employee extends BaseDict {

}
