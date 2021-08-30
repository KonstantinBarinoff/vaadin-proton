package proton.products;

/**
 * Проекция для отображения списка изделий заказчика в
 * {@link proton.customers.CustomerView} и в Комбобокcах
 *
 */

public interface ProductSummary {
    Long getId();
    String getName();
    String getDescription();
}
