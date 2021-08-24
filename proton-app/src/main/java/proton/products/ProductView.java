package proton.products;

import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.base.BaseDictView;
import proton.customers.CustomerService;
import proton.employees.EmployeeService;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "product-ciew", layout = MainView.class)
@PageTitle("Изделия")
public class ProductView extends BaseDictView<Product, ProductService> {

    EmployeeService employeeService;
    CustomerService customerService;

    public ProductView(ProductService productService, EmployeeService employeeService, CustomerService customerService) {
        this.service = productService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        editor = new ProductViewEditor(productService, employeeService, customerService);
    }

    @PostConstruct
    public void init() {
        setupView();
    }

    @Override
    public void setupGrid() {
        super.setupGrid();
        grid.getColumnByKey("description").setVisible(false);   // Скрыть дочернюю колонку
        grid.addColumn(Product::getNumber).setHeader("Количество").setFlexGrow(5);
        grid.addColumn(Product::getCoefficient).setHeader("Коэффициент").setFlexGrow(10);
        grid.addColumn(new LocalDateRenderer<>(Product::getDate, "dd.MM.yy")).setHeader("Дата").setFlexGrow(20);
        grid.addColumn(new LocalDateTimeRenderer<>(Product::getDateTime, "dd.MM.yy hh:mm")).setHeader("Дата/время").setFlexGrow(20);
        grid.addColumn(i -> i.getChecked() ? "Да" : "-").setHeader("Отметка").setFlexGrow(10);
        grid.addColumn(i -> i.getProduceEmployee().getId()).setHeader("Изг. Id").setFlexGrow(1);
        grid.addColumn(i -> i.getProduceEmployee().getName()).setHeader("Изготовил").setFlexGrow(1);
        grid.addColumn(i -> i.getCheckEmployee().getId()).setHeader("Пров. Id").setFlexGrow(1);
        grid.addColumn(i -> i.getCheckEmployee().getName()).setHeader("Проверил").setFlexGrow(1);
        grid.addColumn(i -> i.getCustomer().getName()).setHeader("Заказчик").setFlexGrow(1);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    @Override
    protected Product getNewItem() {
        return new Product();
    }

}
