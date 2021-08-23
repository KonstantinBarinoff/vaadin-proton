package proton.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.entities.TestDict2;
import proton.repositories.TestDict2Service;

@Slf4j
@Route(value = "simple-form-dict-2", layout = MainView.class)
@PageTitle("SimpleDict2 Form Editor")
public class TestDict2FormView extends BaseDictFormView<TestDict2, TestDict2Service> {

    //@Autowired
    public TestDict2FormView(TestDict2Service service) {
        super(service);
        this.repo = service.getRepository();
        editor = new TestDict2FormEditor(service);
        setupView();
    }

    @Override
    protected TestDict2 getNewItem() {
        return new TestDict2();
    }

}
