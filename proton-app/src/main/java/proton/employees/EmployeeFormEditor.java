package proton.employees;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import proton.views.BaseDictFormEditor;

@SpringComponent
@UIScope
public class EmployeeFormEditor extends BaseDictFormEditor<EmployeeDict, EmployeeService> {

    public EmployeeFormEditor(EmployeeService service) {
        super(service);
        repo = service.getRepository();
        binder = new Binder<>(EmployeeDict.class);
        setupView();
    }

}
