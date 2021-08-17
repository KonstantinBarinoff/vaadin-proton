package proton.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import proton.entities.TestDict2;
import proton.repositories.TestDict2Service;

@Slf4j
@Route(value = "simple-form-dict-2", layout = MainView.class)
@PageTitle("SimpleDict2 Form Editor")
public class TestFormDict2View extends BaseFormDictView<TestDict2, TestDict2Service> {

    //@Autowired
    public TestFormDict2View(TestDict2Service service) {
        super(service);
        this.repo = service.getRepository();
        editor = new TestFormDict2Editor(service);
        setupView();
    }

    @Override
    protected TestDict2 getNewItem() {
        return new TestDict2();
    }

//    @Override
//    protected TestFormDict2Editor getNewEditor(SimpleDict2Service service) {
//        return new TestFormDict2Editor(service);
//    }



}
