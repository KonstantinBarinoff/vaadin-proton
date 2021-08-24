package proton.customers;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.base.BaseDictView;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "customers-view", layout = MainView.class)
@PageTitle("Заказчики")
public class CustomerView extends BaseDictView<Customer, CustomerService> {

    public CustomerView(CustomerService service) {
        this.service = service;
        editor = new CustomerViewEditor(service);
    }

    @PostConstruct
    public void init() {
        setupView();
    }

    @Override
    protected Customer getNewItem() {
        return new Customer();
    }

}
