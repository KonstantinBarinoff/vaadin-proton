package proton.products;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.base.BaseDictView;
import proton.customers.CustomerService;
import proton.employees.EmployeeService;
import proton.parts.PartService;
import proton.parts.PartView;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "product-view", layout = MainView.class)
@PageTitle("Изделия")
public class ProductView extends BaseDictView<Product, ProductService> {




//    private final EmployeeService employeeService;
//    private final CustomerService customerService;
    private final PartService partService;
    private final ProductService productService;

    /** Дочерний форма для отображения Деталей выбранного Изделия **/
    PartView    partView;

    public ProductView(ProductService productService, EmployeeService employeeService, CustomerService customerService, PartService partService) {
        this.service = productService;
        this.productService = productService;
//        this.employeeService = employeeService;
//        this.customerService = customerService;
        this.partService = partService;
        editor = new ProductViewEditor(productService, employeeService, customerService);
    }

    @PostConstruct
    public void init() {
        //TODO: Вынести непосредственно в место создания. (Вариант не вызывать конструкттор напрямую, но инжектить бин)
        partView = new PartView(partService, productService);
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

    @Override
    protected void onGridSelectionEvent(SelectionEvent<Grid<Product>, Product> e) {
        super.onGridSelectionEvent(e);
        if (e.getFirstSelectedItem().isPresent()) {
            partView.refreshProductFilter(e.getFirstSelectedItem().get());
        } else {
            partView.refreshProductFilter(null);
        }
    }


    @Override
    public void setupView() {
        super.setupView();
        partView.initFiltered(null);
        add(partView);
    }
}
