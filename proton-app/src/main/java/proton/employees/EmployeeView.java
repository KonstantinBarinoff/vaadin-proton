package proton.employees;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.BaseDictView;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "employee-view", layout = MainView.class)
@PageTitle("Сотрудники")
public class EmployeeView extends BaseDictView<Employee, EmployeeService> {

    @Autowired
    public EmployeeView(EmployeeService service, EmployeeViewEditor editor) {
        log.debug("CONSTRUCTOR");
        this.service = service;
        this.editor = editor;
        editor.setOwnerDictView(this);
    }

    @PostConstruct
    public void init() {
        log.debug("POSTCONSTRUCT");
        setupView();
    }

    @Override
    protected Employee getNewItem() {
        return new Employee();
    }

}
