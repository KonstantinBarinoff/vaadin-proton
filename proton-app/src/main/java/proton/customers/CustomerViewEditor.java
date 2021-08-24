package proton.customers;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.base.BaseDictViewEditor;

@SpringComponent
@UIScope
public class CustomerViewEditor extends BaseDictViewEditor<Customer, CustomerService> {

    public CustomerViewEditor(CustomerService service) {
        //repo = service.getRepository();
        this.service = service;
        binder = new Binder<>(Customer.class);
        setupView();
    }

}
