package proton.employees;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.views.BaseDictFormView;
import proton.views.MainView;

import javax.annotation.PostConstruct;

@Slf4j
@Route(value = "simple-form-dict-2", layout = MainView.class)
@PageTitle("SimpleDict2 Form Editor")
public class EmployeeFormView extends BaseDictFormView<EmployeeDict, EmployeeService> {

    @Autowired
    EmployeeService service;

//    public TestDict2FormView(TestDict2Service service) {
//        super(service);
//    }

    @PostConstruct
    public void init() {
        this.repo = service.getRepository();
        editor = new EmployeeFormEditor(service);
        setupView();
    }

    @Override
    protected EmployeeDict getNewItem() {
        return new EmployeeDict();
    }

}
