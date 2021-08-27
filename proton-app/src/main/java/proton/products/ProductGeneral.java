package proton.products;

/**
 * Проекция для отображения списка изделий заказчика в
 * {@link proton.customers.CustomerView}
 * и в Комбобокмах
 *
 */

public interface ProductGeneral {
    Long getId();
    String getName();
    String getDescription();
}
