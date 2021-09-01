package proton.customers;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import proton.base.BaseDictViewEditor;

@SpringComponent
@UIScope
@Slf4j
public class CustomerViewEditor extends BaseDictViewEditor<Customer, CustomerService> {

    public CustomerViewEditor(CustomerService service) {
        log.debug("CONSTRUCTOR");
        //repo = service.getRepository();
        this.service = service;
        binder = new Binder<>(Customer.class);
        setupView();
    }

}
