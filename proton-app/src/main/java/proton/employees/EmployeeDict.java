package proton.employees;

import proton.entities.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Employees")
public class EmployeeDict extends BaseDict {
}
