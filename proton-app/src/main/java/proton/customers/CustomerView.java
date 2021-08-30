package proton.customers;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.base.BaseDictView;
import proton.products.ProductSummary;
import proton.products.ProductService;
import proton.views.MainView;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Route(value = "customers-view", layout = MainView.class)
@PageTitle("Заказчики")
public class CustomerView extends BaseDictView<Customer, CustomerService> {

    ProductService productService;

    protected final Grid<ProductSummary> productGrid = new Grid<>();

    public CustomerView(CustomerService service, ProductService productService) {
        this.productService = productService;
        this.service = service;
        editor = new CustomerViewEditor(service);
    }

    @PostConstruct
    public void init() {
        setupView();
        productGrid.addColumn(ProductSummary::getId).setKey("id").setHeader("Код изделия").setFlexGrow(1);
        productGrid.addColumn(ProductSummary::getName).setKey("name").setHeader("Наименование изделия").setFlexGrow(1);
        productGrid.addColumn(ProductSummary::getDescription).setKey("description").setHeader("Примечание").setFlexGrow(1);
    }

    @Override
    public void setupView() {
        super.setupView();
        add(productGrid);
    }

    @Override
    protected Customer getNewItem() {
        return new Customer();
    }

    @Override
    protected void onGridSelectionEvent(SelectionEvent<Grid<Customer>, Customer> e) {
        super.onGridSelectionEvent(e);
        if (e.getFirstSelectedItem().isPresent()) {
            productGrid.setItems(productService.findByCustomerId(e.getFirstSelectedItem().get().getId()));
        } else {
            productGrid.setItems(List.of());
        }
    }
}
