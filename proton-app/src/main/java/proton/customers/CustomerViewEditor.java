package proton.customers;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import proton.base.BaseDictViewEditor;

@SpringComponent
@Scope("prototype")
@Slf4j
public class CustomerViewEditor extends BaseDictViewEditor<Customer, CustomerService> {

    public CustomerViewEditor(CustomerService service) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        binder = new Binder<>(Customer.class);
        setupView();
    }

}
