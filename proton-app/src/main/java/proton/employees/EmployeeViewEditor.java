package proton.employees;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.base.BaseDictViewEditor;

@SpringComponent
@UIScope
public class EmployeeViewEditor extends BaseDictViewEditor<Employee, EmployeeService> {

    public EmployeeViewEditor(EmployeeService service) {
        //repo = service.getRepository();
        this.service = service;
        binder = new Binder<>(Employee.class);
        setupView();
    }

}
