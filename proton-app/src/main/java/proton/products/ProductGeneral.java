package proton.products;

/**
 * Проекция для отображения списка изделий заказчика в
 * {@link proton.customers.CustomerView}
 *
 */

public interface ProductGeneral {
    Long getId();
    String getName();
    String getDescription();
}
