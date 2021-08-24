package proton.customers;

import proton.base.BaseDict;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Заказчик (продукции)
 */
@Entity
@Table(name = "Customers")
public class Customer extends BaseDict {
}
