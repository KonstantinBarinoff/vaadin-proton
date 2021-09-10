package proton.customers;

import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Заказчик (продукции)
 */
@Entity
@Table(name = "Customers")
//@SQLDelete(sql = "UPDATE customers SET deleted=1 WHERE id=? AND version=?")
//@Where(clause = "deleted=false")
public class Customer extends BaseDict {

}
